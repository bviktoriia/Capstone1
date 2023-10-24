package org.example;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageobject.modules.PlaylistModule;
import org.pageobject.pages.HomePage;
import org.pageobject.pages.IndexPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;


public class AppTest extends BaseTest {


    @Test(priority = 1)
    public void loginWithEmptyCredentialsTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        indexPage
                .open()
                .login()
                .putCredentials("user", "password")
                .clearInputs();

        WebElement errorUserName = waitForVisibilityOf(webDriver.findElement(By.xpath("//p[contains(text(), 'Please enter your Spotify username or email address.')]")));
        String textOfErrorUserName = errorUserName.getText();
        Assert.assertEquals(textOfErrorUserName, "Please enter your Spotify username or email address.");

        WebElement errorPassword = waitForVisibilityOf(webDriver.findElement(By.xpath("//span[contains(text(), 'Please enter your password.')]")));
        String textOfErrorPassword = errorPassword.getText();
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

        By bannerXPath = By.xpath("//span[contains(text(), 'Incorrect username or password.')]");
        WebElement banner = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(bannerXPath));

        String textOfErrorMessage = banner.getText();
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

        PlaylistModule playlistNameFromList = new PlaylistModule(webDriver);
        String nameFromList = playlistNameFromList
                .checkPlaylistNameFromList();

        PlaylistModule playlistNameFromPane = new PlaylistModule(webDriver);
        String nameFromPane = playlistNameFromPane
                .checkPlaylistNameFromPane();

        Assert.assertEquals(nameFromPane, nameFromList);


//Delete a playlist for possibility to run without manual deleting

    PlaylistModule playlistListDelete = new PlaylistModule(webDriver);
    playlistListDelete
            .invokeRCMForJustCreatedPlaylist()
            .selectDeleteMenu()
            .invokeRCMForJustCreatedPlaylist();

    }


    @Test(priority = 5)
    public void editDetailsOfPlaylistTest() {
        IndexPage indexPage = new IndexPage(webDriver);
        indexPage
                .open()
                .login()
                .putCredentials("bukasik82@gmail.com", "Capstone1")
                .loginIn()
                .createPlaylistFromPlusButton()
                .selectCreateANewPlaylistMenu()
                .invokeRCMForJustCreatedPlaylist()
                .selectEditDetailsMenu()
                .renamePlaylist("My favorite playlist");

        PlaylistModule playlistList = new PlaylistModule(webDriver);
        String nameFromList = playlistList
                .selectJustCreatedPlaylist()
                .checkPlaylistNameFromList();

        PlaylistModule playlistPane = new PlaylistModule(webDriver);
        String nameFromPane = playlistPane
                .checkPlaylistNameFromPane();

        Assert.assertEquals(nameFromPane, nameFromList);
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

        PlaylistModule playlistList = new PlaylistModule(webDriver);
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

        PlaylistModule playlistList = new PlaylistModule(webDriver);
        String playlistName = playlistList
                .selectJustCreatedPlaylist()
                .checkPlaylistNameFromList();

        PlaylistModule playlistListDelete = new PlaylistModule(webDriver);
        String actualMessage = playlistListDelete
                .invokeRCMForJustCreatedPlaylist()
                .selectDeleteMenu()
                .invokeRCMForJustCreatedPlaylist()
                .findAPlaylist(playlistName);

        Assert.assertEquals(actualMessage, "The playlist was not found in the playlist.");
    }


}

