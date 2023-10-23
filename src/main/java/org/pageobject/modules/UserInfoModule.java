package org.pageobject.modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageobject.BasePage;

import java.time.Duration;

public class UserInfoModule extends BasePage {

    @FindBy(xpath = "//span[@data-encore-id='type' and contains(text(), 'Profile')]")
    private WebElement profileButton;

    public UserInfoModule(WebDriver webDriver) {
        super(webDriver);
    }
    public ProfileInfoModule openProfileInfoModule() {
        WebElement option = waitForVisibilityOf(profileButton);
        option.click();
        return new ProfileInfoModule(webDriver);
    }
}
