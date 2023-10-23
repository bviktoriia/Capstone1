package org.pageobject.modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pageobject.BasePage;

public class PlaylistRCMModule extends BasePage {

    @FindBy(xpath = "//span[contains(text(), 'Edit details')]")
    private WebElement editDetailsMenu;

    @FindBy(xpath = "//span[contains(text(), 'Delete')]")
    private WebElement deleteMenu;

    public PlaylistRCMModule(WebDriver webDriver) {
        super(webDriver);
    }

    public EditDetailsModule selectEditDetailsMenu() {
        WebElement option = waitForVisibilityOf(editDetailsMenu);
        option.click();

        try {
            Thread.sleep(1000); // Пауза в 1 секунду
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new EditDetailsModule(webDriver);
    }

    public DeleteQuestionModule selectDeleteMenu() {
        WebElement option = waitForVisibilityOf(deleteMenu);
        option.click();

        return new DeleteQuestionModule(webDriver);
    }
}
