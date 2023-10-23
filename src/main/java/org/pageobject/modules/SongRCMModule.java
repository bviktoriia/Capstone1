package org.pageobject.modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pageobject.BasePage;

public class SongRCMModule extends BasePage {

    @FindBy(xpath = "//span[contains(text(), 'Add to playlist')]")
    private WebElement addToPlaylistMenu;

    @FindBy(xpath = "//span[contains(text(), 'Remove from this playlist')]")
    private WebElement removeFromPlaylistMenu;

    public SongRCMModule(WebDriver webDriver) {
        super(webDriver);
    }

    public SongRCM_SelectPlaylistModule selectAddToPlaylistMenu() {
        WebElement option = waitForVisibilityOf(addToPlaylistMenu);
        option.click();
         return new SongRCM_SelectPlaylistModule(webDriver);
    }

    public PlaylistModule selectremoveFromPlaylistMenu() {
        WebElement option = waitForVisibilityOf(removeFromPlaylistMenu);
        option.click();

        try {
            Thread.sleep(1000); // Пауза в 1 секунду
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new PlaylistModule(webDriver);
    }
}
