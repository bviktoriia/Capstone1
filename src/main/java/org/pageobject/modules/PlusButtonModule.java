package org.pageobject.modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pageobject.BasePage;

public class PlusButtonModule extends BasePage {

    @FindBy(xpath = "//button[contains(@class, 'wC9sIed7pfp47wZbmU6m') and .//span[contains(@class, 'Type__TypeElement-sc-goli3j-0 ieTwfQ') and contains(text(), 'Create a new playlist')]]")
    private WebElement createANewPlaylistMenu;

    public PlusButtonModule(WebDriver webDriver) {
        super(webDriver);
    }

    public PlaylistModule selectCreateANewPlaylistMenu() {
        WebElement option = waitForVisibilityOf(createANewPlaylistMenu);
        option.click();

        try {
            Thread.sleep(1000); // Пауза в 1 секунду
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new PlaylistModule(webDriver);
    }
}
