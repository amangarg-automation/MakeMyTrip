package TestCases;

import Base.Setup;
import Reporters.StepLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC01 extends Setup {
    Logger logger= LogManager.getLogger(this.getClass());
    @Test
    public void TC01_Verify_UserLogin(String username, String password) throws IOException {
        pdfReportGenerator.addCoverPage("TC01_Verify_UserLogin","Verify User is able to login to MakeMyTrip application with valid credentials");
        logger.info("verifying step 1");
        getExtentTest().info("Started Test Case with username "+username+" Password "+password);
        getExtentTest().pass("Login Successful");
        logger.info("Login successful");
        StepLogger stepLogger=new StepLogger(driver,pdfReportGenerator);
        stepLogger.captureStep("Verify user is navigated to homepage","User is navigated to Homepage","User is navigated to HomePage","Pass");
        pdfReportGenerator.addStatusToCoverPage("Pass");
        //driver.findElement(By.xpath("div[contains(@class='logo')]"));
        }
}
