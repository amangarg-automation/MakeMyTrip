package PageElements;

import CommonUtilities.WaitUtils;
import Reporters.PDFReportGenerator;
import Reporters.StepLogger;
import org.openqa.selenium.WebDriver;

public class CommonElements {
    protected WebDriver driver;
    protected PDFReportGenerator pdf;
    protected WaitUtils waitUtils;
    protected StepLogger stepLogger;
    public CommonElements(WebDriver driver,PDFReportGenerator pdf) {
        this.driver = driver;
        this.pdf = pdf;
        stepLogger = new StepLogger(driver, pdf);
        waitUtils=new WaitUtils(driver);
    }
}
