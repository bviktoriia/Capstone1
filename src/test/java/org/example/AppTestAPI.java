package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.dto.PlaylistResponse;
import org.example.utils.UserCredentials;
import org.factory.WebDriverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class AppTestAPI extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(AppTestAPI.class);
    private static final String BASE_URL_USERS = "";
    private static final String clientId = UserCredentials.getClientId();
    private static final String userId = UserCredentials.getUserId();

    @BeforeClass
    public void setUpApi(){
//          RestAssured.baseURI("https://api.spotify.com/v1");
        // TODO inmplement one time setup baseUri
    }

    @BeforeMethod
    public void setUpTestMethod(){
//        var token= setToken()
        setUpApi();
        // TODO it is just example how to setup tests
    }

    @Test
    public void createPlaylistTest() {

        var requestEntity = new PlaylistResponse();
        requestEntity.setId(10);
        requestEntity.setName("Some name");

        String requestBodyPlaylist = "{"
                + "\"name\": \"Playlist for Test1\","
                + "\"description\": \"Playlist for Test1\","
                + "\"public\": false"
                + "}";

        var response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBodyPlaylist)
                .post(BASE_URL_USERS + "/users/" + userId + "/playlists")
                .then()
                .statusCode(201)
                .extract().as(PlaylistResponse.class);

        // TODO example how to extract entity
        var test = response.getId();

//        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test
    public void editDetailsOfThePlaylistTest() {

        String requestBodyPlaylist = "{"
                + "\"name\": \"Playlist for Test2\","
                + "\"description\": \"Playlist for Test2\","
                + "\"public\": false"
                + "}";

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBodyPlaylist)
                .post(BASE_URL_USERS + "/users/" + userId + "/playlists")
                .then()
                .statusCode(201)
                .extract()
                .response();

//        Assert.assertEquals(response.getStatusCode(), 201);
        String playlistId = response.jsonPath().getString("id");


        String requestBodyEditPlaylist = "{"
                + "\"name\": \"Updated My Playlist for Test2\","
                + "\"description\": \"Updated My Playlist for Test2\","
                + "\"public\": false"
                + "}";

        Response response2 = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBodyEditPlaylist)
                .put(BASE_URL_USERS + "/playlists/" + playlistId)
                .then()
                .extract()
                .response();

        Assert.assertEquals(response2.getStatusCode(), 200);
    }

    @Test
    public void addItemsToPlaylistTest() {

        String requestBodyPlaylist = "{"
                + "\"name\": \"Playlist for Test3\","
                + "\"description\": \"Playlist for Test3\","
                + "\"public\": false"
                + "}";

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBodyPlaylist)
                .post(BASE_URL_USERS + "/users/" + userId + "/playlists")
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 201);
        String playlistId = response.jsonPath().getString("id");

        var trackId = "5KD6AEm19QnMbfWpfoOHMl";
        String requestBodyItem = "{"
                + "\"uris\": ["
                + "  \"" + trackId + "\""
                + "],"
                + "\"position\": 0"
                + "}";

        Response response2 = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .queryParam("uris", "spotify:track:" + trackId)
                .body(requestBodyItem)
                .post(BASE_URL_USERS + "/playlists/" + playlistId + "/tracks")
                .then()
                .extract()
                .response();

        Assert.assertEquals(response2.getStatusCode(), 201);
    }

    @Test(priority = 4)
    public void removeSongFromPlaylistTest() {

        String requestBodyPlaylist = "{"
                + "\"name\": \"Playlist for Test4\","
                + "\"description\": \"Playlist for Test4\","
                + "\"public\": false"
                + "}";

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBodyPlaylist)
                .post(BASE_URL_USERS + "/users/" + userId + "/playlists")
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 201);
        String playlistId = response.jsonPath().getString("id");

        String requestBodyItem = "{"
                + "\"uris\": ["
                + "  \"4yIfjMoivhXnY9lZkoVntq\""
                + "],"
                + "\"position\": 0"
                + "}";

        Response response2 = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .queryParam("uris", "spotify:track:4yIfjMoivhXnY9lZkoVntq")
                .body(requestBodyItem)
                .post(BASE_URL_USERS + "/playlists/" + playlistId + "/tracks")
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 201);
        String snapshotId = response2.jsonPath().getString("snapshot_id");

        String requestDeleteItem = "{"
                + "\"tracks\": ["
                + "  {"
                + "    \"uri\": \"spotify:track:4yIfjMoivhXnY9lZkoVntq\""
                + "  }"
                + "],"
                + "\"snapshot_id\": \"" + snapshotId + "\""
                + "}";

        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestDeleteItem)
                .delete(BASE_URL_USERS + "/playlists/" + playlistId + "/tracks")
                .then()
                .statusCode(200);
    }
}
