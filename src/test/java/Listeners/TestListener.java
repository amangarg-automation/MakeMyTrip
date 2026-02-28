package Listeners;

import com.aventstack.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class TestListener implements ITestListener {
    public void onTestFailure(ITestResult result) {
        ITestContext context=result.getTestContext();
        WebDriver driver=(WebDriver) context.getAttribute("driver");
        ExtentTest test=(ExtentTest) context.getAttribute("extentTest");
        String dirPath=System.getProperty("user.dir")+"/FailureScreenshots"+"/"+result.getMethod().getMethodName();
        File dir=new File(dirPath);
        if(!dir.exists())
        {
            dir.mkdirs();
        }
        String screenshotPath=dirPath+"/"+System.currentTimeMillis()+".png";
        File screenshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot,new File(screenshotPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        test.fail("Step failed").addScreenCaptureFromPath(screenshotPath);
    }
}
