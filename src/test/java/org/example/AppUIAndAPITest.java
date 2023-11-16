package org.example;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.example.dto.PlaylistRequest;
import org.example.dto.PlaylistResponse;
import org.example.utils.UserCredentials;
import org.pageobject.modules.YourLibraryModule;
import org.pageobject.pages.IndexPage;
import org.properties.holder.PropertyHolder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AppUIAndAPITest extends BaseTest {
    private static final String userId = UserCredentials.getUserId();
    private RequestSpecification requestSpecification;
    private String playlistName = "Playlist for Test";
    private String playlistUpdateName = "New playlist name for Test";
    private String playlistDescription = "Playlist for Test";
    private String singer = "Whitney Houston";
    private String song = "Run to You";
    private PropertyHolder propertyHolder = new PropertyHolder();
    private String username = propertyHolder.readProperty("username");
    private String password = propertyHolder.readProperty("password");

    @BeforeClass
    public static void setUpApi() {
        RestAssured.baseURI="https://api.spotify.com/v1";
    }

    @BeforeMethod
    public void setupUIAndAPITests(){
        setUpApiToken();
        setUpWebDriver();
        requestSpecification = RestAssured.given();
        setCommonParams(requestSpecification);
    }

    @AfterMethod
    public void tearDownUIAndAPITest(){
        tearDownDriver();
    }

    @Test
    public void addSongToPlaylistTest() {
        PlaylistRequest playlistRequest = new PlaylistRequest();
        playlistRequest.setName(playlistName);
        playlistRequest.setDescription(playlistDescription);
        playlistRequest.setIsPublic(false);

        requestSpecification
                .body(playlistRequest)
                .post("/users/" + userId + "/playlists")
                .then()
                .statusCode(201);

        IndexPage indexPage = new IndexPage(webDriver);
        indexPage
                .open()
                .login()
                .putCredentials(username, password)
                .loginIn()
                .invokeSearchMenu()
                .selectSerchInput(singer)
                .selectSongsList()
                .selectSongFromList(song)
                .selectAddToPlaylistMenu()
                .attachToJustCreatedPlaylist();

        YourLibraryModule playlistList = new YourLibraryModule(webDriver);
        boolean isSongFound = playlistList
                .selectJustCreatedPlaylist()
                .findASongInPlaylist(song);

        Assert.assertEquals(isSongFound, true);
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
                .statusCode(201)
                .extract()
                .as(PlaylistResponse.class);

        String playlistId = response.getId();

        PlaylistRequest playlistUpdate = new PlaylistRequest();
        playlistUpdate.setName(playlistUpdateName);
        playlistUpdate.setDescription(playlistDescription);
        playlistUpdate.setIsPublic(false);

        requestSpecification
                .body(playlistUpdate)
                .put("/playlists/" + playlistId)
                .then()
                .statusCode(200);

// This pause is needed due to playlist name is not updated immediately. It can be increased.
        try {
            Thread.sleep(120000); // The pause of two minutes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        IndexPage indexPage = new IndexPage(webDriver);
        indexPage
                .open()
                .login()
                .putCredentials(username, password)
                .loginIn();

        YourLibraryModule yourLibraryModule = new YourLibraryModule(webDriver);
        String nameFromPane = yourLibraryModule
                .selectJustCreatedPlaylist()
                .checkPlaylistNameFromPane();

        String nameFromList = yourLibraryModule
                .checkPlaylistNameFromList();

        Assert.assertEquals(nameFromPane, playlistUpdateName);
        Assert.assertEquals(nameFromList, playlistUpdateName);
    }
}
