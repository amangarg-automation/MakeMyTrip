package PageElements.FlightBooking;

import PageElements.CommonElements;
import Reporters.PDFReportGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaymentPage extends CommonElements {
    public PaymentPage(WebDriver driver, PDFReportGenerator pdf)
    {super(driver,pdf);
    }
    private final By viewDetails=By.xpath("//span[text()='VIEW DETAILS']");
    private final By passenger_name=By.xpath("//span[contains(@class,'passengerHeading')]");
}
