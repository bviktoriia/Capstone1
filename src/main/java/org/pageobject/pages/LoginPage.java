package org.pageobject.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageobject.BasePage;

import java.time.Duration;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[@id=\"login-username\"]")
    private WebElement userName;
    @FindBy(xpath = "//input[@id=\"login-password\"]")
    private WebElement password;
    @FindBy(xpath = "//button[@id='login-button']")
    private WebElement loginInButton;

    public LoginPage(WebDriver webDriver){
        super(webDriver);
    }

    public LoginPage putCredentials(String valueName, String valuePassword) {
        WebElement option1 = waitForVisibilityOf(userName);
        option1.sendKeys(valueName);

        WebElement option2 = waitForVisibilityOf(password);
        option2.sendKeys(valuePassword);

        return this;
    }
    public LoginPage clearInputs() {
        while (!userName.getAttribute("value").isEmpty()) {
            userName.sendKeys(Keys.BACK_SPACE);
        }

        while (!password.getAttribute("value").isEmpty()) {
            password.sendKeys(Keys.BACK_SPACE);
        }

        return this;
    }
    public HomePage loginIn() {

        WebElement option = waitForVisibilityOf(loginInButton);
        option.click();
        option.click();
        return new HomePage(webDriver);
    }
}