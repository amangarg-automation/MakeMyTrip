package PageElements;

import Reporters.PDFReportGenerator;
import Reporters.StepLogger;
import org.openqa.selenium.WebDriver;

public class CommonElements {
    WebDriver driver;
    PDFReportGenerator pdf;
    StepLogger stepLogger;
    public CommonElements(WebDriver driver,PDFReportGenerator pdf) {
        this.driver = driver;
        this.pdf = pdf;
        stepLogger = new StepLogger(driver, pdf);
    }
}
