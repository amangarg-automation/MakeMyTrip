package PageElements.FlightBooking;

import CommonUtilities.CommonActions;
import PageElements.CommonElements;
import Reporters.PDFReportGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;

public class Flights_Search_Results extends CommonElements {
    public Flights_Search_Results(WebDriver driver,PDFReportGenerator pdf)
    {
        super(driver,pdf);
    }
    Logger logger= LogManager.getLogger(this.getClass());
    private By airlineCheckbox(String airlineName)
    {
        return By.xpath("//p[normalize-space()='"+ airlineName+ "']/parent::div/preceding-sibling::span/child::input");
    }
    private final By first_airline=By.xpath("(//span[text()='VIEW PRICES'])[1]");
    private final By book_now_button=By.xpath("//button[@type='button' and text()='BOOK NOW']");

    public boolean clickOnAirlineCheckbox(String airlineName) throws IOException
    {
        try {
            logger.info("Scrolling to element");
            CommonActions.scrollToElement(airlineCheckbox(airlineName),driver,waitUtils);
            logger.info("scrolled to element");
           WebElement checkbox= waitUtils.waitForElementToBeClickable(airlineCheckbox(airlineName));
           logger.info("waiting for checkbox to be clickable");
           checkbox.click();
           logger.info("clicked on the checkbox");
            boolean isSelected= checkbox.isSelected();
            if(isSelected)
            {
                stepLogger.captureStep("Click on Airline checkbox","Airline checkbox should get selected","Airline checkbox is selected","Pass");
            return isSelected;
            }
            else {
                stepLogger.captureStep("Click on Airline checkbox","Airline checkbox should get selected","Airline checkbox is not selected","Fail");
                return isSelected;
            }
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            stepLogger.captureStep("Click on Airline checkbox","Airline checkbox should get selected","Airline checkbox is not selected due to :"+e.getMessage(),"Fail");
            return false;
        }
    }
}
