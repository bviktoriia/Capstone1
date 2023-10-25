package org.pageobject.modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageobject.BasePage;
import org.pageobject.UtilitiesPage;

import java.time.Duration;

public class ProfileInfoModule extends BasePage {

    private UtilitiesPage utilityPage;

    private WebElement profileInfoText;
    public ProfileInfoModule(WebDriver webDriver) {
        super(webDriver);
        utilityPage = new UtilitiesPage(webDriver);
    }
    public String getProfileInfoText() {
        profileInfoText = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//h1[@data-encore-id='type' and @class='Type__TypeElement-sc-goli3j-0 dYGhLW']")));
        return profileInfoText.getText();
    }

}
