package org.pageobject.modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageobject.BasePage;

import java.time.Duration;

public class SongRCMAddToPlaylistRCMModule extends BasePage {

    public SongRCMAddToPlaylistRCMModule(WebDriver webDriver) {
        super(webDriver);
    }
    public PlaylistModule attachToJustCreatedPlaylist() {
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

        webDriver.navigate().refresh();

        return new PlaylistModule(webDriver);
    }
}
