package org.pageobject.modules;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageobject.BasePage;

import java.time.Duration;

public class PlaylistModule extends BasePage {

    @FindBy(xpath = "//h1[@class='Type__TypeElement-sc-goli3j-0 dYGhLW']")
    private WebElement playlistNameFromPane;

    @FindBy(xpath = "//li[@aria-posinset=1]")
    private WebElement createdPlaylistFromList;

    @FindBy(xpath = "//li[@aria-posinset=1]//span")
    private WebElement playlistNameFromList;

    public PlaylistModule(WebDriver webDriver) {
        super(webDriver);
    }

    public PlaylistModule selectJustCreatedPlaylist() {
        WebElement option = waitForVisibilityOf(createdPlaylistFromList);
        option.click();
        return this;
    }

    public String checkPlaylistNameFromList() {
        WebElement option = waitForVisibilityOf(playlistNameFromList);
        String nameFromList = option.getText();
        return nameFromList;
    }

    public String checkPlaylistNameFromPane() {
        WebElement option = waitForVisibilityOf(playlistNameFromPane);
        String nameFromPane = option.getText();
        return nameFromPane;
    }

//    public PlaylistRCMModule editPlaylistFromList() {
//        WebElement option = waitForVisibilityOf(createdPlaylistFromList);
//        option.click();
//
//        Actions actions = new Actions(webDriver);
//        actions.contextClick(option).perform();
//
//        return new PlaylistRCMModule(webDriver);
//    }

    public String findASongInPlaylist(String value) {

            By songXPath = By.xpath("//div[@data-testid='tracklist-row' and .//div[contains(@class, 'Type__TypeElement') and text()='" + value + "']]");

            try {
                new WebDriverWait(webDriver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.visibilityOfElementLocated(songXPath));
                return "The song was successfully added to the playlist.";
            } catch (TimeoutException e) {
                return "The song was not found in the playlist.";
            }
    }

    public SongRCMModule selectASongInPlaylistForRCM(String value) {
        By songXPath = By.xpath("//div[@data-testid='tracklist-row']//div[@role='gridcell' and .//div[contains(@class, 'Type__TypeElement') and text()='" + value + "']]");
        WebElement option = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(songXPath));

        Actions actions = new Actions(webDriver);
        actions.contextClick(option).perform();

        return new SongRCMModule(webDriver);
    }
    public PlaylistRCMModule invokeRCMForJustCreatedPlaylist() {
        WebElement option = waitForVisibilityOf(createdPlaylistFromList);

        Actions actions = new Actions(webDriver);
        actions.contextClick(option).perform();

        return new PlaylistRCMModule(webDriver);
    }
    public String findAPlaylist(String value) {

        By playlistXPath = By.xpath("//li[@role='listitem' and @aria-posinset='1'][.//span[contains(@class, 'ListRowTitle__LineClamp') and text()='" + value +"']]");

        try {
            new WebDriverWait(webDriver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(playlistXPath));
            return "The playlist was successfully added to the playlist.";
        } catch (TimeoutException e) {
            return "The playlist was not found in the playlist.";
        }
    }


}
