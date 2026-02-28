package Base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

public abstract class Setup {
    protected WebDriver driver;
    protected ExtentReports extentReports;
    protected ExtentTest extentTest;
    Logger logger= LogManager.getLogger(this.getClass());
    Properties properties;
    @BeforeSuite
    public void getProperties(ITestContext context, ISuite suite) throws IOException {
        logger.info("----------------------------------------Started Testing-------------------------------------");
        FileInputStream fis=new FileInputStream("config.properties");
        properties=new Properties();
        properties.load(fis);
        context.setAttribute("suiteName",suite.getName());
        extentReports=ExtentManager.getInstance(context);
    }
    @Parameters("browser")
    @BeforeMethod
    public void startBrowser(String browserName, ITestContext context, Method method)
    {
        logger.info("---------------------------------Executing {}------------------------------", method.getName());
        driver=DriverFactory.getDriver(browserName);
        context.setAttribute("driver",driver);
        context.setAttribute("testcase",method.getName());
        String url= properties.getProperty("url");
        driver.get(url);
        extentTest=extentReports.createTest(method.getName());
    }
    @AfterMethod
    public void closeBrowser(Method method)
    {
        logger.info("-----------------------------------------------Completed Test Case {} Execution-----------------------------------------------", method.getName());
        DriverFactory.closeDriver();
    }
    @AfterSuite
    public void flushExtent()
    {
        extentReports.flush();
    }
}
