package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.utils.UserCredentials;
import org.pageobject.modules.YourLibraryModule;
import org.pageobject.pages.IndexPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AppTestUIAndAPI extends BaseTest {

    private static final String BASE_URL_USERS = "https://api.spotify.com/v1";
    private static final String userId = UserCredentials.getUserId();

    @BeforeMethod
    public void startTest(){
        setUpApiToken();
        setUpWebDriver();
        // TODO it is just example how to setup tests
    }

    @AfterMethod
    public void tearDownTest(){
        tearDownDriver();
        // TODO it is just example how to setup tests
    }

    @Test(priority = 1)
    public void addSongToPlaylistTest() {

        String requestBodyPlaylist = "{"
                + "\"name\": \"Playlist for UIandAPI Test1\","
                + "\"description\": \"Playlist for UIandAPI Test1\","
                + "\"public\": false"
                + "}";

        RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBodyPlaylist)
                .post(BASE_URL_USERS + "/users/" + userId + "/playlists")
                .then()
                .statusCode(201);

        IndexPage indexPage = new IndexPage(webDriver);
        indexPage
                .open()
                .login()
                .putCredentials("bukasik82@gmail.com", "Capstone1")
                .loginIn()
                .invokeSearchMenu()
                .selectSerchInput("Whitney Houston")
                .selectSongsList()
                .selectSongFromList("Run to You")
                .selectAddToPlaylistMenu()
                .attachToJustCreatedPlaylist();

        YourLibraryModule playlistList = new YourLibraryModule(webDriver);
        String actualMessage = playlistList
                .selectJustCreatedPlaylist()
                .findASongInPlaylist("Run to You");

        Assert.assertEquals(actualMessage, "The song was successfully added to the playlist.");
    }

    @Test(priority = 2)
    public void editDetailsOfThePlaylistTest() {

        String requestBodyPlaylist = "{"
                + "\"name\": \"Playlist for UIandAPI Test2\","
                + "\"description\": \"Playlist for UIandAPI Test2\","
                + "\"public\": false"
                + "}";

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBodyPlaylist)
                .post(BASE_URL_USERS + "/users/" + userId + "/playlists")
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 201);
        String playlistId = response.jsonPath().getString("id");


        String requestBodyEditPlaylist = "{"
                + "\"name\": \"Updated My Playlist for UIandAPI Test2\","
                + "\"description\": \"Updated My Playlist for UIandAPI Test2\","
                + "\"public\": false"
                + "}";

        Response response2 = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBodyEditPlaylist)
                .put(BASE_URL_USERS + "/playlists/" + playlistId)
                .then()
                .extract()
                .response();

        Assert.assertEquals(response2.getStatusCode(), 200);


// This pause is needed due to playlist name is not updated immediately. It can be increased.
        try {
            Thread.sleep(120000); // Пауза в 1 секунду
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        IndexPage indexPage = new IndexPage(webDriver);
        indexPage
                .open()
                .login()
                .putCredentials("bukasik82@gmail.com", "Capstone1")
                .loginIn();

        YourLibraryModule yourLibraryModule = new YourLibraryModule(webDriver);
        String nameFromPane = yourLibraryModule
                .selectJustCreatedPlaylist()
                .checkPlaylistNameFromPane();

        String nameFromList = yourLibraryModule
                .checkPlaylistNameFromList();

        Assert.assertEquals(nameFromPane, "Updated My Playlist for UIandAPI Test2");
        Assert.assertEquals(nameFromList, "Updated My Playlist for UIandAPI Test2");
    }
}
