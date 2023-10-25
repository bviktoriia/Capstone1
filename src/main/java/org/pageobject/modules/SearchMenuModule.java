package org.pageobject.modules;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pageobject.BasePage;
import org.pageobject.UtilitiesPage;

public class SearchMenuModule extends BasePage {

    private UtilitiesPage utilityPage;

    @FindBy(xpath = "//form/input[@class='Type__TypeElement-sc-goli3j-0 ieTwfQ QO9loc33XC50mMRUCIvf']")
    private WebElement searchField;

    public SearchMenuModule(WebDriver webDriver) {
        super(webDriver);
        utilityPage = new UtilitiesPage(webDriver);
    }
    public SearchResultModule selectSerchInput(String value) {
        utilityPage.waitForVisibilityOf(searchField).click();
        searchField.sendKeys(value);
        searchField.sendKeys(Keys.ENTER); // Нажать клавишу Enter
        return new SearchResultModule(webDriver);
    }
}


