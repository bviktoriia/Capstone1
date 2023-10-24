package org.pageobject.modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pageobject.BasePage;

public class EditDetailsModule extends BasePage {

    @FindBy(xpath = "//input[@data-testid='playlist-edit-details-name-input']")
    private WebElement playlistNameField;

    @FindBy(xpath = "//button[.//span[contains(text(), 'Save')]]")
    private WebElement saveButton;

    public EditDetailsModule(WebDriver webDriver) {
        super(webDriver);
    }

    public PlaylistModule renamePlaylist(String value) {
        WebElement option = waitForVisibilityOf(playlistNameField);
        option.clear();
        option.sendKeys(value);

        WebElement save = waitForVisibilityOf(saveButton);
        save.click();

        return new PlaylistModule(webDriver);
    }
}
