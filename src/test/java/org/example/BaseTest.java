package org.example;

import org.example.dto.AccessToken;
import org.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static org.example.dto.AccessToken.*;

public class BaseTest {

    protected WebDriver webDriver;

    public String accessToken;

    @BeforeMethod
    protected void setUpWebDriver() {
        webDriver = new WebDriverFactory().getWebDriver();
        webDriver.manage().window().maximize();
    }

    @AfterMethod
    protected void quit() {
        webDriver.quit();
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    @BeforeMethod
    public void setUp() {
        if (AccessToken.accessToken == null) {
            AccessToken.loadTokenFromFile();

            if (AccessToken.accessToken == null) {
                AccessToken.receiveToken();
            } else if (isTokenExpired()) {
                AccessToken.refreshToken();
            }
            accessToken = AccessToken.accessToken;
        }
    }
}


