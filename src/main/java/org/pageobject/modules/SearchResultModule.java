package org.pageobject.modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageobject.BasePage;

import java.time.Duration;


public class SearchResultModule extends BasePage {

    @FindBy(xpath = "//button/span[contains(text(), 'Songs')]")
    private WebElement songsFilter;

    public SearchResultModule(WebDriver webDriver) {
        super(webDriver);
    }

    public SearchResultModule selectSongsList() {
        WebElement option = waitForVisibilityOf(songsFilter);
        option.click();
        return this;
    }

    public SongRCMModule selectSongFromList(String value) {

        By songXPath = By.xpath("//div[@data-testid=\"tracklist-row\" and .//div[contains(@class, 'Type__TypeElement') and text()='" + value + "']]");
        WebElement song = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(songXPath));

        Actions actions = new Actions(webDriver);
        actions.contextClick(song).perform();

        return new SongRCMModule(webDriver);
    }

}
