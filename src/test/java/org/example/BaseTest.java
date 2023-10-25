package org.example;

import org.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver webDriver;

    @BeforeMethod
    protected void setUpWebDriver() {
     webDriver = new WebDriverFactory().getWebDriver();
     webDriver.manage().window().maximize();
    }

    @AfterMethod
    protected void quit() {
        webDriver.quit();
    }

}