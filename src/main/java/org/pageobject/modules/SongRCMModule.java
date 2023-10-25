package org.pageobject.modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pageobject.BasePage;
import org.pageobject.UtilitiesPage;

public class SongRCMModule extends BasePage {

    private UtilitiesPage utilityPage;

    @FindBy(xpath = "//span[contains(text(), 'Add to playlist')]")
    private WebElement addToPlaylistMenu;

    @FindBy(xpath = "//span[contains(text(), 'Remove from this playlist')]")
    private WebElement removeFromPlaylistMenu;

    public SongRCMModule(WebDriver webDriver) {
        super(webDriver);
        utilityPage = new UtilitiesPage(webDriver);
    }

    public SongRCMAddToPlaylistRCMModule selectAddToPlaylistMenu() {
        utilityPage.waitForVisibilityOf(addToPlaylistMenu).click();
         return new SongRCMAddToPlaylistRCMModule(webDriver);
    }

    public YourLibraryModule selectRemoveFromPlaylistMenu() {
        utilityPage.waitForVisibilityOf(removeFromPlaylistMenu).click();

        try {
            Thread.sleep(1000); // Пауза в 1 секунду
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new YourLibraryModule(webDriver);
    }
}
