package Reporters;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class StepLogger {
    WebDriver driver;
    PDFReportGenerator pdfReportGenerator;
    public StepLogger(WebDriver driver,PDFReportGenerator pdfReportGenerator)
    {
        this.driver=driver;
        this.pdfReportGenerator=pdfReportGenerator;
    }
    public void captureStep(String description, String expected, String actual, String status) throws IOException {
        String screenshotPath=System.getProperty("user.dir")+"/"+"CaptureScreenshots/"+System.currentTimeMillis()+".png";
        byte[] screenshotBytes=((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
        FileUtils.writeByteArrayToFile(new File(screenshotPath),screenshotBytes);
        pdfReportGenerator.addStepToPDF(description,expected,actual,status,screenshotPath);
    }
}
