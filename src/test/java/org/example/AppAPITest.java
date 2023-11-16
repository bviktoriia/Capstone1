package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.dto.ItemRequest;
import org.example.dto.PlaylistRequest;
import org.example.dto.PlaylistResponse;
import org.example.dto.RemoveItem;
import org.example.utils.UserCredentials;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;


public class AppAPITest extends BaseTest {
    private static final String userId = UserCredentials.getUserId();
    private RequestSpecification requestSpecification;
    private String playlistName = "Playlist for Test";
    private String playlistUpdateName = "New playlist name for Test";
    private String playlistDescription = "Playlist for Test";
    private String trackUri = "5KD6AEm19QnMbfWpfoOHMl";

    @BeforeClass
    public static void setUpApi() {
        RestAssured.baseURI="https://api.spotify.com/v1";
    }

    @BeforeMethod
    public void setupAPITests(){
        setUpApiToken();
        requestSpecification = RestAssured.given();
        setCommonParams(requestSpecification);
    }

    @Test
    public void createPlaylistTest() {
        PlaylistRequest playlistRequest = new PlaylistRequest();
        playlistRequest.setName(playlistName);
        playlistRequest.setDescription(playlistDescription);
        playlistRequest.setIsPublic(false);

        requestSpecification
                .body(playlistRequest)
                .post("/users/" + userId + "/playlists")
                .then()
                .statusCode(201);
    }

    @Test
    public void editDetailsOfThePlaylistTest() {
        PlaylistRequest playlistRequest = new PlaylistRequest();
        playlistRequest.setName(playlistName);
        playlistRequest.setDescription(playlistDescription);
        playlistRequest.setIsPublic(false);

        PlaylistResponse response = requestSpecification
                .body(playlistRequest)
                .post("/users/" + userId + "/playlists")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(PlaylistResponse.class);

        String playlistId = response.getId();

        PlaylistRequest playlistUpdate = new PlaylistRequest();
        playlistUpdate.setName(playlistUpdateName);
        playlistUpdate.setDescription(playlistUpdateName);
        playlistUpdate.setIsPublic(false);

        requestSpecification
                .body(playlistUpdate)
                .put("/playlists/" + playlistId)
                .then()
                .statusCode(200);
    }

    @Test
    public void addItemsToPlaylistTest() {
        PlaylistRequest playlistRequest = new PlaylistRequest();
        playlistRequest.setName(playlistName);
        playlistRequest.setDescription(playlistDescription);
        playlistRequest.setIsPublic(false);

        PlaylistResponse response = requestSpecification
                .body(playlistRequest)
                .post("/users/" + userId + "/playlists")
                .then()
                .statusCode(201)
                .extract()
                .as(PlaylistResponse.class);

        String playlistId = response.getId();

        ItemRequest addSong = new ItemRequest();
        addSong.setUris(new String[]{trackUri});
        addSong.setPosition(0);

        requestSpecification
                .queryParam("uris", "spotify:track:" + trackUri)
                .body(addSong)
                .post("/playlists/" + playlistId + "/tracks")
                .then()
                .statusCode(201);
    }

    @Test
    public void removeSongFromPlaylistTest() {
        PlaylistRequest playlistRequest = new PlaylistRequest();
        playlistRequest.setName(playlistName);
        playlistRequest.setDescription(playlistDescription);
        playlistRequest.setIsPublic(false);

        PlaylistResponse response = requestSpecification
                .body(playlistRequest)
                .post("/users/" + userId + "/playlists")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(PlaylistResponse.class);

        String playlistId = response.getId();;

        ItemRequest addSong = new ItemRequest();
        addSong.setUris(new String[]{trackUri});
        addSong.setPosition(0);

        PlaylistResponse response2 = requestSpecification
                .queryParam("uris", "spotify:track:" + trackUri)
                .body(addSong)
                .post("/playlists/" + playlistId + "/tracks")
                .then()
                .statusCode(201)
                .extract()
                .as(PlaylistResponse.class);

        String snapshotId = response2.getSnapshot_id();

        RemoveItem requestDeleteItem = new RemoveItem();
        requestDeleteItem.setUris(Collections.singletonList("spotify:track:" + trackUri));
        requestDeleteItem.setSnapshot_id(snapshotId);

        requestSpecification
                .body(requestDeleteItem)
                .delete("/playlists/" + playlistId + "/tracks")
                .then()
                .statusCode(200);
    }
}












