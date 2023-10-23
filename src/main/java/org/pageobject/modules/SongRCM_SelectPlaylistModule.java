package org.pageobject.modules;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageobject.BasePage;

import java.time.Duration;

public class SongRCM_SelectPlaylistModule extends BasePage {

    public SongRCM_SelectPlaylistModule(WebDriver webDriver) {
        super(webDriver);
    }
    public SearchResultModule attachToJustCreatedPlaylist() {
        PlaylistModule playlist = new PlaylistModule(webDriver);
        String playlistName = playlist.checkPlaylistNameFromList(); // Получите имя плейлиста

        By playlistXPath = By.xpath("//button[@class='wC9sIed7pfp47wZbmU6m']/span[contains(., '" + playlistName + "')][1]");
        WebElement option = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(playlistXPath));

        option.click();

        try {
            Thread.sleep(1000); // Пауза в 1 секунду
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new SearchResultModule(webDriver);
    }
}
