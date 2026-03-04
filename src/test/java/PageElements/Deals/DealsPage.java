package PageElements.Deals;

import CommonUtilities.CommonActions;
import PageElements.CommonElements;
import Reporters.PDFReportGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.*;
public class DealsPage extends CommonElements {

    public DealsPage(WebDriver driver, PDFReportGenerator pdf) {
        super(driver, pdf);
    }
    private final By dealCodes= By.xpath("//span[@class='dealCode']");
    public boolean verifyDealCodes() throws IOException {
        try{
        waitUtils.waitForElementToBeLocated(dealCodes);
        if(!driver.findElements(dealCodes).isEmpty())
        {
            CommonActions.scrollToElement(dealCodes,driver,waitUtils);
        stepLogger.captureStep("Verify Discount codes are displayed", "Discount codes shall be displayed", "Discount codes are displayed", "Pass");
    return true;}
        else {
            stepLogger.captureStep("Verify Discount codes are displayed", "Discount codes shall be displayed", "Discount codes are not displayed", "Fail");
            return false;
        }
        } catch (RuntimeException e) {
            stepLogger.captureStep("Verify Discount codes are displayed", "Discount codes shall be displayed", "Discount codes are not displayed", "Fail");
            return false;
        }
    }
    public void getDealCodes() throws IOException {
        try {
            List<WebElement> dealElements = driver.findElements(dealCodes);
            String dealCodes = "";
            for (WebElement element : dealElements) {
                dealCodes = dealCodes + " , " + element.getText();
            }
            stepLogger.captureStep("List of codes displayed should be noted down", "Discount codes should be noted down", "Discount codes are noted down as :"+dealCodes, "Pass");
        } catch (RuntimeException | IOException e) {
            stepLogger.captureStep("List of codes displayed should be noted down", "Discount codes should be noted down", "Discount codes are not noted down due to: "+e.getMessage(), "Fail");
        }

    }

}
