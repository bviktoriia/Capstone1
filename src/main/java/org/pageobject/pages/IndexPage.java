package org.pageobject.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pageobject.BasePage;
import org.pageobject.UtilitiesPage;

public class IndexPage extends BasePage {
    private UtilitiesPage utilityPage;
    @FindBy(xpath = "//button[@data-testid='login-button']")
    private WebElement loginButton;
    public IndexPage (WebDriver webDriver){
        super(webDriver);
        utilityPage = new UtilitiesPage(webDriver);
    }

    public IndexPage open() {
        webDriver.get("https://open.spotify.com/");
        return this;
    }
    public LoginPage login() {
        utilityPage.waitForVisibilityOf(loginButton).click();
        return new LoginPage(webDriver);
    }

}
