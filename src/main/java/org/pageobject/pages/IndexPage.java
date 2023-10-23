package org.pageobject.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageobject.BasePage;

import java.time.Duration;

public class IndexPage extends BasePage {
    @FindBy(xpath = "//button[@data-testid='login-button']")
    private WebElement loginButton;
    public IndexPage (WebDriver webDriver){
        super(webDriver);
    }

    public IndexPage open() {
        webDriver.get("https://open.spotify.com/");
        return this;
    }
    public LoginPage login() {
        WebElement option = waitForVisibilityOf(loginButton);
        option.click();
        return new LoginPage(webDriver);
    }

}
