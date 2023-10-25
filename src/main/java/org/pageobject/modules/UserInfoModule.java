package org.pageobject.modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pageobject.BasePage;
import org.pageobject.UtilitiesPage;

public class UserInfoModule extends BasePage {

    private UtilitiesPage utilityPage;

    @FindBy(xpath = "//span[@data-encore-id='type' and contains(text(), 'Profile')]")
    private WebElement profileButton;

    public UserInfoModule(WebDriver webDriver) {
        super(webDriver);
        utilityPage = new UtilitiesPage(webDriver);
    }
    public ProfileInfoModule openProfileInfoModule() {
        utilityPage.waitForVisibilityOf(profileButton).click();
        return new ProfileInfoModule(webDriver);
    }
}
