package PageElements.FlightBooking;

import PageElements.CommonElements;
import Reporters.PDFReportGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CompleteBooking extends CommonElements {
    public CompleteBooking(WebDriver driver, PDFReportGenerator pdf) {
        super(driver, pdf);
    }

    private final By addAdultButton = By.xpath("//button[@class='addTravellerBtn']");
    private final By mobileNumberTextBox = By.xpath("//input[@placeholder='Mobile No']");
    private final By emailTextBox = By.xpath("//input[@placeholder='Email']");
    private final By firstName = By.xpath("//input[@placeholder='First & Middle Name']");
    private final By lastName = By.xpath("//input[@placeholder='Last Name']");
    private By gender(String gender)
    {
        gender=gender.toUpperCase();
        return By.xpath("//span[text()='"+ gender +"']");
    }
    private final By confirmButton=By.xpath("//p[text()='Confirm and save billing details to your profile']");
    private final By continue_button=By.xpath("//button[text()='Continue']");
    private final By review_confirm_button=By.xpath("//button[text()='CONFIRM']");
    private final By seat_button=By.xpath("//button[text()='Yes, Please']");
    private final By proceedToPay=By.xpath("//button[text()='Proceed to pay']");

}