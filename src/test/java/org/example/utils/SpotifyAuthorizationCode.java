package org.example.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class SpotifyAuthorizationCode {
    private static final Logger logger = Logger.getLogger(SpotifyAuthorizationCode.class.getName());

    public static void main(String[] args) {
        RestAssured.baseURI = "https://accounts.spotify.com";
        String clientId = "aedd356bdbde48408e592c730bb564b8";
        String responseType = "code";
        String redirectUri = "http://localhost:3000";
        String scope = "playlist-modify-public%20playlist-read-private%20playlist-modify-private";
        boolean showDialog = false;
        RequestSpecification requestSpec = RestAssured.given()
                .queryParam("client_id", clientId)
                .queryParam("response_type", responseType)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scope)
                .queryParam("show_dialog", showDialog);
        Response response = requestSpec.get("/authorize");

        if (response.getStatusCode() == 200) {
            logger.info("GET request is successful"); // TODO logger to output
            String authorizationURL = "https://accounts.spotify.com/authorize" +
                    "?client_id=" + clientId +
                    "&response_type=" + responseType +
                    "&redirect_uri=" + redirectUri +
                    "&scope=" + scope +
                    "&showDialog" + showDialog;
            try {
                Desktop.getDesktop().browse(new URI(authorizationURL));
                Response response2 = RestAssured.get(redirectUri);

            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }

        } else {
            System.err.println("Error GET request"); // TODO logger.error
        }
    }
}
