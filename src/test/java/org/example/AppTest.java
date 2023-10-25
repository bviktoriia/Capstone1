package org.example;


import org.pageobject.modules.YourLibraryModule;
import org.pageobject.pages.HomePage;
import org.pageobject.pages.IndexPage;
import org.pageobject.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class AppTest extends BaseTest {


    @Test(priority = 1)
    public void loginWithEmptyCredentialsTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        String textOfErrorUserName = indexPage
                .open()
                .login()
                .putCredentials("user", "password")
                .clearInputs()
                .checkTextOfErrorUserName();

        Assert.assertEquals(textOfErrorUserName, "Please enter your Spotify username or email address.");

        LoginPage loginPage = new LoginPage(webDriver);
        String textOfErrorPassword = loginPage
                .checkTextOfErrorPassword();

        Assert.assertEquals(textOfErrorPassword, "Please enter your password.");
    }


    @DataProvider(name = "wrongCredentials")
    public Object[][] wrongCredentials() {
        return new Object[][]{
                {"wrongusername", "wrongpassword"},
        };
    }

    @Test(dataProvider = "wrongCredentials", priority = 2)
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

        Assert.assertEquals(textOfErrorMessage, "Incorrect username or password.");
    }


    @Test(priority = 3)
    public void loginWithCorrectCredentialsTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        String profileInfoText = indexPage
                .open()
                .login()
                .putCredentials("bukasik82@gmail.com", "Capstone1")
                .loginIn()
                .openUserInfoModule()
                .openProfileInfoModule()
                .getProfileInfoText();

        Assert.assertEquals(profileInfoText, "Viktoriia");
    }

//@Ignore
    @Test(priority = 4)
    public void createPlaylistTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        indexPage
                .open()
                .login()
                .putCredentials("bvik.ua@gmail.com", "Capstone1")
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


    @Test(priority = 5)
    public void editDetailsOfPlaylistTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        String nameFromList = indexPage
                .open()
                .login()
                .putCredentials("bukasik82@gmail.com", "Capstone1")
                .loginIn()
                .createPlaylistFromPlusButton()
                .selectCreateANewPlaylistMenu()
                .invokeRCMForJustCreatedPlaylist()
                .selectEditDetailsMenu()
                .renamePlaylist("My favorite playlist")
                .selectJustCreatedPlaylist()
                .checkPlaylistNameFromList();

        YourLibraryModule yourLibraryPage = new YourLibraryModule(webDriver);
        String nameFromPane = yourLibraryPage
                .checkPlaylistNameFromList();

        Assert.assertEquals(nameFromList, "My favorite playlist");
        Assert.assertEquals(nameFromPane, "My favorite playlist");

    }


    @Test(priority = 6)
    public void searchAndAddToPlaylistTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        indexPage
                .open()
                .login()
                .putCredentials("bukasik82@gmail.com", "Capstone1")
                .loginIn()
                .createPlaylistFromPlusButton()
                .selectCreateANewPlaylistMenu();

        HomePage homePage = new HomePage(webDriver);
        homePage
                .invokeSearchMenu()
                .selectSerchInput("Whitney Elizabeth Houston")
                .selectSongsList()
                .selectSongFromList("I Have Nothing")
                .selectAddToPlaylistMenu()
                .attachToJustCreatedPlaylist();

        YourLibraryModule playlistList = new YourLibraryModule(webDriver);
        String actualMessage = playlistList
                .selectJustCreatedPlaylist()
                .findASongInPlaylist("I Have Nothing");

        Assert.assertEquals(actualMessage, "The song was successfully added to the playlist.");
    }


    @Test(priority = 7)
    public void removeSongFromPlaylistTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        indexPage
                .open()
                .login()
                .putCredentials("bukasik82@gmail.com", "Capstone1")
                .loginIn()
                .createPlaylistFromPlusButton()
                .selectCreateANewPlaylistMenu();

        HomePage homePage = new HomePage(webDriver);
        String actualMessage = homePage
                .invokeSearchMenu()
                .selectSerchInput("Whitney Elizabeth Houston")
                .selectSongsList()
                .selectSongFromList("I Have Nothing")
                .selectAddToPlaylistMenu()
                .attachToJustCreatedPlaylist()
                .selectJustCreatedPlaylist()
                .selectASongInPlaylistForRCM("I Have Nothing")
                .selectRemoveFromPlaylistMenu()
                .findASongInPlaylist("I Have Nothing");

        Assert.assertEquals(actualMessage, "The song was not found in the playlist.");
    }


    @Test(priority = 8)
    public void deletePlaylistTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        indexPage
                .open()
                .login()
                .putCredentials("bukasik82@gmail.com", "Capstone1")
                .loginIn()
                .createPlaylistFromPlusButton()
                .selectCreateANewPlaylistMenu();

        YourLibraryModule playlistList = new YourLibraryModule(webDriver);
        String playlistName = playlistList
                .selectJustCreatedPlaylist()
                .checkPlaylistNameFromList();

        String actualMessage = playlistList
                .invokeRCMForJustCreatedPlaylist()
                .selectDeleteMenu()
                .invokeRCMForJustCreatedPlaylist()
                .findAPlaylist(playlistName);

        Assert.assertEquals(actualMessage, "The playlist was not found in the playlist.");
    }


}

