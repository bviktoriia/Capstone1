package org.enumeraition;

import org.invoker.WebDriverInvoker;
import org.invoker.implementations.ChromeInvoker;
import org.invoker.implementations.FirefoxInvoker;
import org.openqa.selenium.WebDriver;
public enum SupportedBrowsers {
    FIREFOX(new FirefoxInvoker()),
    CHROME(new ChromeInvoker());
    private WebDriverInvoker webDriverInvoker;
    SupportedBrowsers (WebDriverInvoker webDriverInvoker) {

        this.webDriverInvoker = webDriverInvoker;
    }
    public WebDriver getWebDriver() {
        return webDriverInvoker.invokeWebDriver();
    }
}