package CommonUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class CommonActions {
    public static void clickElementFromDynamicDropdown(WebDriver driver, By locator, String value, WaitUtils waitUtils)
    {
        Logger logger= LogManager.getLogger();
        waitUtils.waitForElementToBeLocated(locator);
        List<WebElement> elementList=driver.findElements(locator);
        for(WebElement element:elementList)
        {
            if(element.getText().contains(value))
            {
                logger.info("Clicking dropdown value");
                waitUtils.retryStaleElement(element).click();
                break;
            }
        }
    }
}
