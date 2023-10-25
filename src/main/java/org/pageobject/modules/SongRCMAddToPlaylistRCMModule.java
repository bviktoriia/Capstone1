package org.pageobject.modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageobject.BasePage;
import org.pageobject.UtilitiesPage;

import java.time.Duration;

public class SongRCMAddToPlaylistRCMModule extends BasePage {

    private UtilitiesPage utilityPage;

    public SongRCMAddToPlaylistRCMModule(WebDriver webDriver) {
        super(webDriver);
        utilityPage = new UtilitiesPage(webDriver);
    }
    public YourLibraryModule attachToJustCreatedPlaylist() {
        YourLibraryModule playlist = new YourLibraryModule(webDriver);
        String playlistName = playlist.checkPlaylistNameFromList(); // Имя плейлиста

        WebElement playlistXPath = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='wC9sIed7pfp47wZbmU6m']/span[contains(., '" + playlistName + "')][1]")));
        playlistXPath.click();

        try {
            Thread.sleep(1000); // Пауза в 1 секунду
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        webDriver.navigate().refresh();

        return new YourLibraryModule(webDriver);
    }
}
