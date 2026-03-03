package CommonUtilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private final WebDriverWait wait;
    public WaitUtils(WebDriver driver)
    {
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    public WebElement waitForElementToBeClickable(By locator)
    {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public WebElement waitForElementToBeLocated(By locator)
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public WebElement waitForElementToBeClickable(WebElement element)
    {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    public WebElement retryStaleElement(By locator )
    {
        return wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(locator)));
    }
    public WebElement retryStaleElement(WebElement element )
    {
        return wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
    }
}
