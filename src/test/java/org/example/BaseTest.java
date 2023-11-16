package org.example;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.utils.AccessToken;
import org.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;

import static org.example.utils.AccessToken.isTokenExpired;

public class BaseTest {
    protected WebDriver webDriver;
    public static String accessToken;

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

    public void setCommonParams(RequestSpecification requestSpecification) {
        requestSpecification.headers("Authorization", "Bearer " + accessToken);
        requestSpecification.contentType(ContentType.JSON);
    }
}