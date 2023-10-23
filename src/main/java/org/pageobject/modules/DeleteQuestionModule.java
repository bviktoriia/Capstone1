package org.pageobject.modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pageobject.BasePage;

public class DeleteQuestionModule extends BasePage {

    @FindBy(xpath = "//button/span[contains(text(), 'Delete')]")
    private WebElement deleteButton;

    public DeleteQuestionModule(WebDriver webDriver) {
        super(webDriver);
    }

    public PlaylistModule invokeRCMForJustCreatedPlaylist() {
        WebElement option = waitForVisibilityOf(deleteButton);

       option.click();

        try {
            Thread.sleep(1000); // Пауза в 1 секунду
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        webDriver.navigate().refresh();

        return new PlaylistModule(webDriver);
    }


}
