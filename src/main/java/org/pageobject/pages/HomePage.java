package org.pageobject.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pageobject.BasePage;
import org.pageobject.modules.PlaylistModule;
import org.pageobject.modules.PlusButtonModule;
import org.pageobject.modules.SearchMenuModule;
import org.pageobject.modules.UserInfoModule;

public class HomePage extends BasePage {

    @FindBy(xpath = "//button[@class='Button-sc-1dqy6lx-0 grWQsc encore-over-media-set SFgYidQmrqrFEVh65Zrg']")
    private WebElement userButton;
    @FindBy(xpath = "//span[text()='Create playlist']")
    private WebElement createPlaylistButton;

    @FindBy(xpath = "//button[@class='Button-sc-1dqy6lx-0 emaScS OMCDc2F7g_AufJAtaKfL TxO7Ee8iwqBpkgznKHsd ufICQKJq0XJE5iiIsZfj caTDfb6Oj7a5_8jBLUSo vOp2HlcPkxOHebo3If32 eZnAGhYcXE4Bt0a7958z']")
    private WebElement plusButton;

    @FindBy(xpath = "//a[@aria-label='Search']")
    private WebElement searchButton;

    protected  HomePage (WebDriver webDriver) {

        super(webDriver);
 }
    public UserInfoModule openUserInfoModule() {
        WebElement option = waitForVisibilityOf(userButton);
        option.click();
        return new UserInfoModule(webDriver);
    }

    public PlaylistModule createPlaylistFromButton() {
        WebElement option = waitForVisibilityOf(createPlaylistButton);
        option.click();
        return new PlaylistModule(webDriver);
    }

    public PlusButtonModule createPlaylistFromPlusButton() {
        WebElement option = waitForVisibilityOf(plusButton);
        option.click();
        return new PlusButtonModule(webDriver);
    }
    public SearchMenuModule invokeSearchMenu() {
        webDriver.navigate().refresh();
        WebElement option = waitForVisibilityOf(searchButton);
        option.click();
        return new SearchMenuModule(webDriver);
    }

}
