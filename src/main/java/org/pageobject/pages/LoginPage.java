package org.pageobject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageobject.BasePage;
import org.pageobject.UtilitiesPage;

import java.time.Duration;

public class LoginPage extends BasePage {

    private UtilitiesPage utilityPage;

    @FindBy(xpath = "//input[@id=\"login-username\"]")
    private WebElement userName;
    @FindBy(xpath = "//input[@id=\"login-password\"]")
    private WebElement password;
    @FindBy(xpath = "//button[@id='login-button']")
    private WebElement loginInButton;

    public LoginPage(WebDriver webDriver){
        super(webDriver);
        utilityPage = new UtilitiesPage(webDriver);
    }

    public LoginPage putCredentials(String valueName, String valuePassword) {
        utilityPage.waitForVisibilityOf(userName).sendKeys(valueName);

        utilityPage.waitForVisibilityOf(password).sendKeys(valuePassword);

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
        utilityPage.waitForVisibilityOf(loginInButton).click();
                loginInButton.click();

        return new HomePage(webDriver);
    }
    public String checkTextOfErrorUserName() {
        WebElement errorUserName = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//p[contains(text(), 'Please enter your Spotify username or email address.')]")));
        String textOfErrorUserName = errorUserName.getText();

        return textOfErrorUserName;
    }
    public String checkTextOfErrorPassword() {
        WebElement errorPassword = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//span[contains(text(), 'Please enter your password.')]")));
        String textOfErrorPassword = errorPassword.getText();

        return textOfErrorPassword;
    }
    public String checkTheBanner() {
        WebElement banner = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//span[contains(text(), 'Incorrect username or password.')]")));
        String textOfErrorMessage = banner.getText();

        return textOfErrorMessage;
    }
}