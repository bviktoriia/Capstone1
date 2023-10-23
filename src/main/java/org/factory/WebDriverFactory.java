package org.factory;

import org.openqa.selenium.WebDriver;
import org.properties.convertors.SupportedBrowserConverter;
import org.properties.holder.PropertyHolder;

public class WebDriverFactory {
    public WebDriver getWebDriver() {
        return SupportedBrowserConverter.valueOfWebBrowser(
                new PropertyHolder().readProperty("browser")).getWebDriver();

    }
}
