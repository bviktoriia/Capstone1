package org.example;

import org.pageobject.modules.YourLibraryModule;
import org.pageobject.pages.HomePage;
import org.pageobject.pages.IndexPage;
import org.pageobject.pages.LoginPage;
import org.properties.holder.PropertyHolder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class AppUITest extends BaseTest{
    private String singer = "Whitney Houston";
    private String song = "Run to You";
    private String playlistUpdateName = "New playlist name for Test";
    private String errorUserName = "Please enter your Spotify username or email address.";
    private String errorPassword = "Please enter your password.";
    private String errorMessage = "Incorrect username or password.";
    private String profileUserInfo = "Viktoriia";

    private PropertyHolder propertyHolder = new PropertyHolder();
    private String username = propertyHolder.readProperty("username");
    private String password = propertyHolder.readProperty("password");
    private String username2 = propertyHolder.readProperty("username2");
    private String password2 = propertyHolder.readProperty("password2");


    @BeforeMethod
    public void setupUITests(){
        setUpWebDriver();
    }

    @AfterMethod
    public void tearDownUITests(){
        tearDownDriver();
    }

    @Test
    public void loginWithEmptyCredentialsTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        String textOfErrorUserName = indexPage
                .open()
                .login()
                .putCredentials(password, username)
                .clearInputs()
                .checkTextOfErrorUserName();

        Assert.assertEquals(textOfErrorUserName, errorUserName);

        LoginPage loginPage = new LoginPage(webDriver);
        String textOfErrorPassword = loginPage
                .checkTextOfErrorPassword();

        Assert.assertEquals(textOfErrorPassword, errorPassword);
    }


    @DataProvider(name = "wrongCredentials")
    public Object[][] wrongCredentials() {
        return new Object[][]{
                {"wrongusername", "wrongpassword"},
        };
    }

    @Test(dataProvider = "wrongCredentials")
    public void loginWithIncorrectCredentialsTest(String username, String password) {
        IndexPage indexPage = new IndexPage(webDriver);
        indexPage
                .open()
                .login()
                .putCredentials(username, password)
                .loginIn();

        LoginPage banerPage = new LoginPage(webDriver);
        String textOfErrorMessage = banerPage
                .checkTheBanner();

        Assert.assertEquals(textOfErrorMessage, errorMessage);
    }


    @Test
    public void loginWithCorrectCredentialsTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        String profileInfoText = indexPage
                .open()
                .login()
                .putCredentials(username, password)
                .loginIn()
                .openUserInfoModule()
                .openProfileInfoModule()
                .getProfileInfoText();

        Assert.assertEquals(profileInfoText, profileUserInfo);
    }

//@Ignore
    @Test
    public void createPlaylistTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        indexPage
                .open()
                .login()
                .putCredentials(username2, password2)
                .loginIn()
                .createPlaylistFromButton();

        YourLibraryModule playlistName = new YourLibraryModule(webDriver);

        String nameFromList = playlistName
                .checkPlaylistNameFromList();

        String nameFromPane = playlistName
                .checkPlaylistNameFromPane();

        Assert.assertEquals(nameFromPane, nameFromList);


//Delete a playlist for possibility to run without manual deleting
        YourLibraryModule deletePlaylist = new YourLibraryModule(webDriver);
        deletePlaylist
            .invokeRCMForJustCreatedPlaylist()
            .selectDeleteMenu()
            .invokeRCMForJustCreatedPlaylist();

    }


    @Test
    public void editDetailsOfPlaylistTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        String nameFromList = indexPage
                .open()
                .login()
                .putCredentials(username, password)
                .loginIn()
                .createPlaylistFromPlusButton()
                .selectCreateANewPlaylistMenu()
                .invokeRCMForJustCreatedPlaylist()
                .selectEditDetailsMenu()
                .renamePlaylist(playlistUpdateName)
                .selectJustCreatedPlaylist()
                .checkPlaylistNameFromList();

        YourLibraryModule yourLibraryPage = new YourLibraryModule(webDriver);
        String nameFromPane = yourLibraryPage
                .checkPlaylistNameFromList();

        Assert.assertEquals(nameFromList, playlistUpdateName);
        Assert.assertEquals(nameFromPane, playlistUpdateName);

    }


    @Test
    public void searchAndAddToPlaylistTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        indexPage
                .open()
                .login()
                .putCredentials(username, password)
                .loginIn()
                .createPlaylistFromPlusButton()
                .selectCreateANewPlaylistMenu();

        HomePage homePage = new HomePage(webDriver);
        homePage
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
    public void removeSongFromPlaylistTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        indexPage
                .open()
                .login()
                .putCredentials(username, password)
                .loginIn()
                .createPlaylistFromPlusButton()
                .selectCreateANewPlaylistMenu();

        HomePage homePage = new HomePage(webDriver);
        boolean isSongNotFound = homePage
                .invokeSearchMenu()
                .selectSerchInput(singer)
                .selectSongsList()
                .selectSongFromList(song)
                .selectAddToPlaylistMenu()
                .attachToJustCreatedPlaylist()
                .selectJustCreatedPlaylist()
                .selectASongInPlaylistForRCM(song)
                .selectRemoveFromPlaylistMenu()
                .findASongInPlaylist(song);

        Assert.assertEquals(isSongNotFound, false);
    }


    @Test
    public void deletePlaylistTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        String playlistName = indexPage
                .open()
                .login()
                .putCredentials(username, password)
                .loginIn()
                .createPlaylistFromPlusButton()
                .selectCreateANewPlaylistMenu()
                .selectJustCreatedPlaylist()
                .checkPlaylistNameFromList();


        YourLibraryModule playlistList = new YourLibraryModule(webDriver);
        boolean isPlaylistNotFound = playlistList
                .invokeRCMForJustCreatedPlaylist()
                .selectDeleteMenu()
                .invokeRCMForJustCreatedPlaylist()
                .findAPlaylist(playlistName);

        Assert.assertEquals(isPlaylistNotFound, false);
    }


}

