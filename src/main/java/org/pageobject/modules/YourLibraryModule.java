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
import org.pageobject.UtilitiesPage;

import java.time.Duration;

public class YourLibraryModule extends BasePage {

    private UtilitiesPage utilityPage;

    @FindBy(xpath = "//h1[@class='Type__TypeElement-sc-goli3j-0 dYGhLW']")
    private WebElement playlistNameFromPane;

    @FindBy(xpath = "//li[@aria-posinset=1]")
    private WebElement createdPlaylistFromList;

    @FindBy(xpath = "//li[@aria-posinset=1]//span")
    private WebElement playlistNameFromList;

    @FindBy(xpath = "//button[contains(@class, 'wC9sIed7pfp47wZbmU6m') and .//span[contains(@class, 'Type__TypeElement-sc-goli3j-0 ieTwfQ') and contains(text(), 'Create a new playlist')]]")
    private WebElement createANewPlaylistMenu;

    public YourLibraryModule(WebDriver webDriver) {
        super(webDriver);
        utilityPage = new UtilitiesPage(webDriver);
    }

    public YourLibraryModule selectJustCreatedPlaylist() {
        utilityPage.waitForVisibilityOf(createdPlaylistFromList).click();
        return this;
    }

    public String checkPlaylistNameFromList() {
        String nameFromList = utilityPage.waitForVisibilityOf(playlistNameFromList).getText();
        return nameFromList;
    }

    public String checkPlaylistNameFromPane() {
        String nameFromPane = utilityPage.waitForVisibilityOf(playlistNameFromPane).getText();
        return nameFromPane;
    }

    public YourLibraryModule selectCreateANewPlaylistMenu() {
        utilityPage.waitForVisibilityOf(createANewPlaylistMenu).click();

        try {
            Thread.sleep(1000); // Пауза в 1 секунду
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new YourLibraryModule(webDriver);
    }

    public boolean findASongInPlaylist(String value) {

            By songXPath = By.xpath("//div[@data-testid='tracklist-row' and .//div[contains(@class, 'Type__TypeElement') and text()='" + value + "']]");

            try {
                new WebDriverWait(webDriver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.visibilityOfElementLocated(songXPath));
                return true;
            } catch (TimeoutException e) {
                return false;
            }
    }

    public SongRCMModule selectASongInPlaylistForRCM(String value) {
        WebElement songXPath = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-testid='tracklist-row']//div[@role='gridcell' and .//div[contains(@class, 'Type__TypeElement') and text()='" + value + "']]")));

        Actions actions = new Actions(webDriver);
        actions.contextClick(songXPath).perform();

        return new SongRCMModule(webDriver);
    }
    public PlaylistRCMModule invokeRCMForJustCreatedPlaylist() {
        utilityPage.waitForVisibilityOf(createdPlaylistFromList);

        Actions actions = new Actions(webDriver);
        actions.contextClick(createdPlaylistFromList).perform();

        return new PlaylistRCMModule(webDriver);
    }
    public boolean findAPlaylist(String value) {

        By playlistXPath = By.xpath("//li[@role='listitem' and @aria-posinset='1'][.//span[contains(@class, 'ListRowTitle__LineClamp') and text()='" + value +"']]");

        try {
            new WebDriverWait(webDriver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(playlistXPath));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

}
