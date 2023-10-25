package org.pageobject.modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageobject.BasePage;
import org.pageobject.UtilitiesPage;

import java.time.Duration;

public class EditDetailsModule extends BasePage {

    private UtilitiesPage utilityPage;

    @FindBy(xpath = "//input[@data-testid='playlist-edit-details-name-input']")
    private WebElement playlistNameField;

    @FindBy(xpath = "//button[.//span[contains(text(), 'Save')]]")
    private WebElement saveButton;

    public EditDetailsModule(WebDriver webDriver) {
        super(webDriver);
        utilityPage = new UtilitiesPage(webDriver);
    }

    public YourLibraryModule renamePlaylist(String value) {
        utilityPage.waitForVisibilityOf(playlistNameField).clear();
        playlistNameField.sendKeys(value);

        utilityPage.waitForVisibilityOf(saveButton).click();

        By playlist = By.xpath("//li[@aria-posinset=1]//span[contains(., '"+ value + "')]");
        WebElement editPlaylist = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(playlist));

        return new YourLibraryModule(webDriver);
    }
}
