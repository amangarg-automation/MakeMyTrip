package Base;

import Reporters.ExtentManager;
import Reporters.PDFReportGenerator;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;

public abstract class Setup {
    protected WebDriver driver;
    protected static ExtentReports extentReports;
    public static String sheetName;
    protected ExtentTest getExtentTest()
    {
        return extentTestThreadLocal.get();
    }
    Logger logger= LogManager.getLogger(this.getClass());
    static Properties properties;
    protected ThreadLocal<PDFReportGenerator> pdfReporter=new ThreadLocal<>();
    protected ThreadLocal<ExtentTest> extentTestThreadLocal=new ThreadLocal<>();
    protected PDFReportGenerator pdfReportGenerator;
    @BeforeSuite(alwaysRun = true)
    public void getProperties() throws IOException {
        logger.info("----------------------------------------Started Testing-------------------------------------");
        FileInputStream fis=new FileInputStream("config.properties");
        properties=new Properties();
        properties.load(fis);
    }
    @BeforeTest(alwaysRun = true)
    public void startExtent(XmlTest xmlTest) {
        extentReports = ExtentManager.getInstance(
                xmlTest.getSuite().getName()
        );
        sheetName=properties.getProperty("sheet");
    }
    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void startBrowser(String browserName, ITestContext context, Method method) throws MalformedURLException {
        System.out.println("Thread ID: " + Thread.currentThread().getId() +
                " - " + method.getName());
        logger.info("---------------------------------Executing {}------------------------------", method.getName());
        driver=DriverFactory.getDriver(browserName);
        String appName=System.getProperty("Application_Name");
        if(appName==null)
        {
            appName="makemytrip";
        }
        context.setAttribute("driver",driver);
        context.setAttribute("testcase",method.getName());
        System.out.println(appName);
        if(appName.equalsIgnoreCase("makemytrip"))
        {
        String url= properties.getProperty("makemytrip_url");
            driver.get(url);
        }
        else {
            String url= properties.getProperty("goibibo_url");
            driver.get(url);
        }

        extentTestThreadLocal.set(extentReports.createTest(method.getName()));
        context.setAttribute("extentTest",getExtentTest());
        pdfReporter.set(new PDFReportGenerator());
        pdfReportGenerator=pdfReporter.get();
    }
    @AfterMethod(alwaysRun = true)
    public void closeBrowser(Method method, ITestResult result) throws IOException {
        logger.info("-----------------------------------------------Completed Test Case {} Execution-----------------------------------------------", method.getName());
        DriverFactory.closeDriver();
        if(result.getStatus()==ITestResult.FAILURE)
        {
            pdfReportGenerator.addStatusToCoverPage("Fail");
            getExtentTest().fail("Test Case Failed");
        }
        else if(result.getStatus()==ITestResult.SUCCESS)
        {
            pdfReportGenerator.addStatusToCoverPage("Pass");
            getExtentTest().pass("Test Case Passed");
        }
        else {
            pdfReportGenerator.addStatusToCoverPage("Skipped");
            getExtentTest().info("Test Case Skipped");
        }
        String timestamp=new java.text.SimpleDateFormat("ddMMyyyy_HH_mm_ss").format(new java.util.Date());
        String reportDir=System.getProperty("user.dir")+"/PDFReports/"+method.getName()+"_"+timestamp;
        File dir=new File(reportDir);
        if(!dir.exists())
        {
            dir.mkdirs();
        }
        String reportPath=reportDir+"/"+method.getName()+".pdf";
      pdfReportGenerator.save(reportPath);
    }
    @AfterTest(alwaysRun = true)
    public void flushExtent()
    {
        extentReports.flush();
    }
}
