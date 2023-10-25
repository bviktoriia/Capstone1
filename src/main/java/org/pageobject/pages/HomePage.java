package org.pageobject.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pageobject.BasePage;
import org.pageobject.UtilitiesPage;
import org.pageobject.modules.YourLibraryModule;
import org.pageobject.modules.SearchMenuModule;
import org.pageobject.modules.UserInfoModule;

public class HomePage extends BasePage {

    private UtilitiesPage utilityPage;

    @FindBy(xpath = "//button[@class='Button-sc-1dqy6lx-0 grWQsc encore-over-media-set SFgYidQmrqrFEVh65Zrg']")
    private WebElement userButton;
    @FindBy(xpath = "//span[text()='Create playlist']")
    private WebElement createPlaylistButton;

    @FindBy(xpath = "//button[@class='Button-sc-1dqy6lx-0 emaScS OMCDc2F7g_AufJAtaKfL TxO7Ee8iwqBpkgznKHsd ufICQKJq0XJE5iiIsZfj caTDfb6Oj7a5_8jBLUSo vOp2HlcPkxOHebo3If32 eZnAGhYcXE4Bt0a7958z']")
    private WebElement plusButton;

    @FindBy(xpath = "//a[@aria-label='Search']")
    private WebElement searchButton;

    public HomePage (WebDriver webDriver) {
        super(webDriver);
        utilityPage = new UtilitiesPage(webDriver);
 }
    public UserInfoModule openUserInfoModule() {
        utilityPage.waitForVisibilityOf(userButton).click();
        return new UserInfoModule(webDriver);
    }

    public YourLibraryModule createPlaylistFromButton() {
        utilityPage.waitForVisibilityOf(createPlaylistButton).click();
        return new YourLibraryModule(webDriver);
    }

    public YourLibraryModule createPlaylistFromPlusButton() {
        utilityPage.waitForVisibilityOf(plusButton).click();
        return new YourLibraryModule(webDriver);
    }
    public SearchMenuModule invokeSearchMenu() {
        webDriver.navigate().refresh();
        utilityPage.waitForVisibilityOf(searchButton).click();
        return new SearchMenuModule(webDriver);
    }

}
