package Base;

import Reporters.ExtentManager;
import Reporters.PDFReportGenerator;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

public abstract class Setup {
    protected WebDriver driver;
    protected ExtentReports extentReports;
    protected ExtentTest extentTest;
    Logger logger= LogManager.getLogger(this.getClass());
    Properties properties;
    protected ThreadLocal<PDFReportGenerator> pdfReporter=new ThreadLocal<>();
    protected PDFReportGenerator pdfReportGenerator;
    @BeforeSuite
    public void getProperties() throws IOException {
        logger.info("----------------------------------------Started Testing-------------------------------------");
        FileInputStream fis=new FileInputStream("config.properties");
        properties=new Properties();
        properties.load(fis);
    }
    @BeforeTest
    public void startExtent(XmlTest result)
    {
        extentReports= ExtentManager.getInstance(result.getSuite().getName());
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
        context.setAttribute("extentTest",extentTest);
        pdfReporter.set(new PDFReportGenerator());
        pdfReportGenerator=pdfReporter.get();
    }
    @AfterMethod
    public void closeBrowser(Method method) throws IOException {
        logger.info("-----------------------------------------------Completed Test Case {} Execution-----------------------------------------------", method.getName());
        DriverFactory.closeDriver();
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
    @AfterTest
    public void flushExtent()
    {
        extentReports.flush();
    }
}
