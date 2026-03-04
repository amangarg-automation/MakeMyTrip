package TestCases;

import Base.Setup;
import PageElements.FlightBooking.Flights_Search_Results;
import PageElements.Homepage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC05 extends Setup {
    @Test(groups = "flight booking")
    public void TC05_Verify_Flight_Booking(String from, String to) throws IOException {
        Homepage hm=new Homepage(driver,pdfReportGenerator);
        hm.closeLoginPopUp();
        hm.searchForFlights(from,to);
        hm.verifySearchResult(from,to);
        Flights_Search_Results fs=new Flights_Search_Results(driver,pdfReportGenerator);
        Assert.assertTrue(fs.clickOnAirlineCheckbox("Air India"));
    }
}
