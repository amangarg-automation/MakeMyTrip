package CommonUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Set;

public class CommonActions {
    public static void clickElementFromDynamicDropdown(WebDriver driver, By locator, String value, WaitUtils waitUtils) {
        Logger logger = LogManager.getLogger();

        boolean clicked = false;
        for (int i = 0; i < 3; i++) { // retry 3 times
            try {
                waitUtils.waitForElementToBeLocated(locator);
                List<WebElement> elementList = driver.findElements(locator);
                for (WebElement element : elementList) {
                    if (element.getText().contains(value)) {
                        logger.info("Clicking dropdown value: " + value);
                        element.click(); // fresh element, no stale reference
                        clicked = true;
                        break;
                    }
                }
                if (clicked) break;
            } catch (StaleElementReferenceException e) {
                logger.warn("Stale element detected, retrying... attempt " + (i + 1));
            }
        }
        if (!clicked) {
            throw new RuntimeException("Failed to click dropdown value: " + value);
        }
    }
    public static void switchToDefaultWindow(WebDriver driver, String defaultWindowHandle)
    {
        Set<String> windowHandles=driver.getWindowHandles();
        for(String windowHandle:windowHandles)
        {
            if(!windowHandle.equalsIgnoreCase(defaultWindowHandle))
            {
                driver.switchTo().window(windowHandle).close();
            }
        }
        driver.switchTo().window(defaultWindowHandle);
    }
    public static void switchToNewWindow(WebDriver driver, String defaultWindowHandle)
    {
        Set<String> windowHandles=driver.getWindowHandles();
        for(String windowHandle:windowHandles)
        {
            if(!windowHandle.equalsIgnoreCase(defaultWindowHandle))
            {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }
    public static void scrollToElement(By locator,WebDriver driver,WaitUtils waitUtils)
    {
        WebElement element=waitUtils.waitForElementToBeLocated(locator);
        JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true)",element);
    }
}
