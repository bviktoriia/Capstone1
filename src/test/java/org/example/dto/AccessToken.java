package org.example.dto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static java.lang.System.currentTimeMillis;

public class AccessToken {

    private static final String clientId = UserCredentials.getClientId();
    private static final String clientSecret = UserCredentials.getClientSecret();
    private static final String redirectUri = UserCredentials.getRedirectUri();

    private static final String BASE_URL_ACC = "https://accounts.spotify.com";

    public static String accessToken;
    public static String refreshToken;
    public static long expiresAt;
    private static final String FILE_PATH = "src/test/resources/token.properties";

    private static final String FILE_PATH_TOKEN = "src/test/resources/token.json";

    private static final String FILE_PATH_TOKEN_REFRESH = "src/test/resources/refresh_token.json";

    private static final Logger logger = Logger.getLogger(SpotifyAuthorizationCode.class.getName());

    public static void receiveToken() {
        RestAssured.baseURI = "https://accounts.spotify.com";

        String code = "AQDIVqNFkEhSxWRPAqE91vs7O_D51Z6_EmKp4IKVbE0uQGH574IycKLz0tEFd2Z_WlOgGFHL352XBeeyz-s8zzs39n4oJhgsj5YedVuTe_90EC5YHgJFQ2yHJcNeNW3IjXf01jqNd7HtpasAskGsgxyFzAFBzW60ajwJS4j6frSOu2QcXagaisWAPkAnTTHPUbtO6AvO8t9jkXJ4_Fo9rt2kly2CAUDnsRORLXzIxf7DBAtxU_az6btLdcZHmvDttQ";

        Response response = RestAssured.given()
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()))
                .formParam("code", code)
                .formParam("redirect_uri", redirectUri)
                .formParam("grant_type", "authorization_code")
                .post(BASE_URL_ACC + "/api/token")
                .then()
                .log()
                .all() // Показать логи запросов и ответов
                .extract()
                .response();

        if (response.getStatusCode() == 200) {
            accessToken = response.jsonPath().getString("access_token");
            refreshToken = response.jsonPath().getString("refresh_token");
            expiresAt = TimeUnit.MILLISECONDS.toSeconds(currentTimeMillis()) + response.jsonPath().getLong("expires_in");

            // Запись в файл
            try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
                fileWriter.write("Access Token: " + accessToken + "\n");
                fileWriter.write("Refresh Token: " + refreshToken + "\n");
                fileWriter.write("Expires At: " + expiresAt + "\n");
                System.out.println("Tokens written to file.");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (FileWriter fileWriter2 = new FileWriter(FILE_PATH_TOKEN)) {
                fileWriter2.write(response.jsonPath().prettyPrint());
                System.out.println("Response body written to response.json");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Access Token1: " + accessToken);
            System.out.println("Refresh Token1: " + refreshToken);
            System.out.println("Expires At1: " + expiresAt);
        } else {
            System.err.println("Error in token request1.");
        }
    }

    public static void refreshToken() {
        RestAssured.baseURI = "https://accounts.spotify.com";

        System.out.println("Access Token: " + accessToken);
        System.out.println("Refresh Token: " + refreshToken);
        System.out.println("Expires At: " + expiresAt);

        Response response = RestAssured.given()
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()))
                .header("Content-Type", ContentType.URLENC.toString())
                .formParam("refresh_token", refreshToken)
                .formParam("grant_type", "refresh_token")
                .post(BASE_URL_ACC + "/api/token")
                .then()
                .log()
                .all() // Показать логи запросов и ответов
                .extract()
                .response();

        if (response.getStatusCode() == 200) {
            accessToken = response.jsonPath().getString("access_token");
            expiresAt = TimeUnit.MILLISECONDS.toSeconds(currentTimeMillis()) + response.jsonPath().getLong("expires_in");

            // Запись в файл
            try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
                fileWriter.write("Access Token: " + accessToken + "\n");
                fileWriter.write("Refresh Token: " + refreshToken + "\n");
                fileWriter.write("Expires At: " + expiresAt + "\n");
                System.out.println("Tokens written to file.");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (FileWriter fileWriter2 = new FileWriter(FILE_PATH_TOKEN_REFRESH)) {
                fileWriter2.write(response.jsonPath().prettyPrint());
                System.out.println("Response body written to response.json");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Access Token2: " + accessToken);
            System.out.println("Refresh Token2: " + refreshToken);
            System.out.println("Expires At2: " + expiresAt);
        } else {
            System.err.println("Error in token request2.");
        }
        System.out.println("AccessToken2: " + accessToken);
        System.out.println("Refresh Token2: " + refreshToken);
        System.out.println("Expires At2: " + expiresAt);
    }

    public static boolean isTokenExpired() {
        return currentTimeMillis() >= TimeUnit.SECONDS.toMillis(AccessToken.expiresAt);
    }

    public static void loadTokenFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            accessToken = extractValue(reader.readLine());
            refreshToken = extractValue(reader.readLine());
            expiresAt = Long.parseLong(extractValue(reader.readLine()));

            System.out.println("Access Token: " + accessToken);
            System.out.println("Refresh Token: " + refreshToken);
            System.out.println("Expires At: " + expiresAt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String extractValue(String line) {
        String[] parts = line.split(":");
        if (parts.length > 1) {
            return parts[1].trim();
        } else {
            // Обработка случая, если символ ":" не найден в строке
            System.err.println("Error extracting value from line: " + line);
            return "";
        }
    }
}









