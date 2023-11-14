package org.example;

import org.example.utils.AccessToken;
import org.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;

import static org.example.utils.AccessToken.*;

public class BaseTest {

    protected WebDriver webDriver;
    public String accessToken;

    protected void setUpWebDriver() {
        webDriver = new WebDriverFactory().getWebDriver();
        webDriver.manage().window().maximize();
    }

    protected void tearDownDriver() {
        webDriver.quit();
    }

    protected WebDriver getWebDriver() {
        return webDriver;
    }


    public void setUpApiToken() {
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


