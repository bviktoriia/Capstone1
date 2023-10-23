package org.pageobject.modules;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pageobject.BasePage;

public class SearchMenuModule extends BasePage {

    @FindBy(xpath = "//form/input[@class='Type__TypeElement-sc-goli3j-0 ieTwfQ QO9loc33XC50mMRUCIvf']")
    private WebElement searchField;

    public SearchMenuModule(WebDriver webDriver) {
        super(webDriver);
    }
    public SearchResultModule selectSerchInput(String value) {
        WebElement option = waitForVisibilityOf(searchField);
        option.click();
        option.sendKeys(value);
        option.sendKeys(Keys.ENTER); // Нажать клавишу Enter
        return new SearchResultModule(webDriver);
    }
}


