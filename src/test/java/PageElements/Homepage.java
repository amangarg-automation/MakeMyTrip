package PageElements;

import CommonUtilities.CommonActions;
import CommonUtilities.WaitUtils;
import Reporters.PDFReportGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Set;

public class Homepage extends CommonElements{

    String appName="makemytrip";

    public Homepage(WebDriver driver,PDFReportGenerator pdf)
    {
        super(driver,pdf);
    }
    Logger logger= LogManager.getLogger(this.getClass());

        private By close_login_pop_up_box()
        {
            if(appName.equalsIgnoreCase("makemytrip")){
           return By.xpath("//span[@class='commonModal__close']");}
            else {
                return By.xpath("//span[@class='sc-koXPp bDtzaf']");
            }
        }
    protected String currentWindowHandle= driver.getWindowHandle();
    private final By from_city=By.id("fromCity");
    private final By to_city=By.id("toCity");
    private final By from_locator=By.xpath("//input[@placeholder='From']");
    private final By from_dropdown=By.xpath("//span[@class='revampedCityName']");
    private final By to_dropdown=By.xpath("//span[@class='revampedCityName']");
    private final By to_locator=By.xpath("//input[@placeholder='To']");
    private final By searchButton=By.xpath("//a[text()='Search']");
    private final By searchText=By.xpath("//span[contains(text(),'Flights from ')]");
    private final By from_date=By.xpath("//p[text()='4']");
    private final By return_box=By.xpath("//p[@data-cy='returnDefaultText']");
    private final By Holi_Cabs_Offer=By.xpath("//p[text()='For Your Holi Trip Back Home:']/ancestor::div[@class='makeFlex cardInnerInfo']/following-sibling::div/descendant::a");
    private By searchResult_from(String fromName) {
        return By.xpath("//span[contains(text(),'Flights from ')]/child::b[text()='" + fromName + "']");
    }
    private By searchResult_to(String toName) {
        return By.xpath("//span[contains(text(),'Flights from ')]/child::b[text()='" + toName + "']");
    }
    public boolean closeLoginPopUp() throws IOException {

        try {
            waitUtils.waitForElementToBeClickable(close_login_pop_up_box());
            driver.findElement(close_login_pop_up_box()).click();
            driver.findElement(By.tagName("body")).click();
            stepLogger.captureStep("Close the login popup", "login pop up should be closed","loging popup is closed","pass");
        return true;
        } catch (RuntimeException e) {
            stepLogger.captureStep("Close the login popup", "login pop up should be closed","loging popup is not closed due to: "+e.getMessage(),"fail");
        logger.error(e.getMessage());
            return false;
        }
           }
    public boolean searchForFlights(String from, String to) throws IOException {
        try {
            waitUtils.waitForElementToBeLocated(from_city);
            driver.findElement(from_city).click();
            waitUtils.waitForElementToBeLocated(from_locator);
            driver.findElement(from_locator).sendKeys(from);
            waitUtils.retryStaleElement(from_dropdown);
            logger.info("waiting for from dropdown");
            CommonActions.clickElementFromDynamicDropdown(driver, from_dropdown, from, waitUtils);
            logger.info("selected from dropdown value");
            logger.info("started to dropdown clicking");
            waitUtils.retryStaleElement(to_city);
            logger.info("waiting for to city dropdown");
            driver.findElement(to_city).click();
            logger.info("clicked on to city dropdown");
            driver.findElement(to_locator).sendKeys(to);
            waitUtils.retryStaleElement(to_dropdown);
            CommonActions.clickElementFromDynamicDropdown(driver, to_dropdown, to, waitUtils);
            stepLogger.captureStep("Enter from and to locations", "User should be able to enter from and to locations","User is able to add from and to locations","pass");
            waitUtils.waitForElementToBeClickable(from_date);
            driver.findElement(from_date).click();
            waitUtils.waitForElementToBeLocated(return_box);
            driver.findElement(return_box).click();
            waitUtils.waitForElementToBeClickable(searchButton);
            driver.findElement(searchButton).click();
            return true;
        }catch (RuntimeException e) {
            stepLogger.captureStep("Enter from and to locations", "User should be able to enter from and to locations","User is not able to add from and to locations due to: "+e.getMessage(),"fail");
            logger.error(e.getMessage());
            return false;
        }
    }
    public boolean verifySearchResult(String from, String to) throws IOException
    {
        try {
            waitUtils.waitForElementToBeLocated(searchResult_from(from));
            waitUtils.waitForElementToBeLocated(searchResult_to(to));
            logger.info(driver.findElement(searchText).getText());
            logger.info(driver.findElement(searchResult_from(from)).getText());
            logger.info(driver.findElement(searchResult_to(to)).getText());
            if (driver.findElement(searchResult_from(from)).isDisplayed() && driver.findElement(searchResult_to(to)).isDisplayed() && driver.findElement(searchText).isDisplayed()) {
                stepLogger.captureStep("Verify Search Results are correct", "Search Result should be correct","Search Results are correct","pass");
                return true;
            } else {
                stepLogger.captureStep("Verify Search Results are correct", "Search Result should be correct","Search Results are not correct","fail");

                return false;
            }
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            stepLogger.captureStep("Verify Search Results are correct", "Search Result should be correct","Search Results are not correct due to: "+e.getMessage(),"fail");
        return false;
        }
    }
    public void clickHoliCabBookNow() throws IOException {
      try {
          waitUtils.waitForElementToBeClickable(Holi_Cabs_Offer);
          driver.findElement(Holi_Cabs_Offer).click();
          CommonActions.switchToNewWindow(driver, currentWindowHandle);
          waitUtils.waitForElementToBeLocated(By.xpath("//h2[text()='Enjoy Deals as Colourful as Gulaal']"));
          stepLogger.captureStep("Navigate to holi cab offer page", "User should be navigated to Holi cab Offer Page", "User is navigated to offer page", "Pass");
      } catch (RuntimeException e) {
          stepLogger.captureStep("Navigate to holi cab offer page", "User should be navigated to Holi cab Offer Page", "User is navigated to offer page due to: "+e.getMessage(), "Pass");
      }
    }
}
