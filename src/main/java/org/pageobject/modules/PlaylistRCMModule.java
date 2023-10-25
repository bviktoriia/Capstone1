package org.pageobject.modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pageobject.BasePage;
import org.pageobject.UtilitiesPage;

public class PlaylistRCMModule extends BasePage {

    private UtilitiesPage utilityPage;

    @FindBy(xpath = "//span[contains(text(), 'Edit details')]")
    private WebElement editDetailsMenu;

    @FindBy(xpath = "//span[contains(text(), 'Delete')]")
    private WebElement deleteMenu;

    public PlaylistRCMModule(WebDriver webDriver) {
        super(webDriver);
        utilityPage = new UtilitiesPage(webDriver);
    }

    public EditDetailsModule selectEditDetailsMenu() {
        utilityPage.waitForVisibilityOf(editDetailsMenu).click();

        return new EditDetailsModule(webDriver);
    }

    public DeleteQuestionModule selectDeleteMenu() {
        utilityPage.waitForVisibilityOf(deleteMenu).click();

        return new DeleteQuestionModule(webDriver);
    }
}
