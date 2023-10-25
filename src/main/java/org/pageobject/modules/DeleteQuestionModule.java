package org.pageobject.modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pageobject.BasePage;
import org.pageobject.UtilitiesPage;

public class DeleteQuestionModule extends BasePage {

    private UtilitiesPage utilityPage;

    @FindBy(xpath = "//button/span[contains(text(), 'Delete')]")
    private WebElement deleteButton;

    public DeleteQuestionModule(WebDriver webDriver) {
        super(webDriver);
        utilityPage = new UtilitiesPage(webDriver);
    }

    public YourLibraryModule invokeRCMForJustCreatedPlaylist() {
        utilityPage.waitForVisibilityOf(deleteButton).click();

        webDriver.navigate().refresh();

        return new YourLibraryModule(webDriver);
    }


}
