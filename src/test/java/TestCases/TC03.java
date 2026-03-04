package TestCases;

import Base.Setup;
import PageElements.Homepage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class TC03 extends Setup {
    @Test(groups={"sanity testing","flight search"})
    public void TC03_Verify_FlightSearch(String from, String to) throws IOException {
        Homepage homepage=new Homepage(driver,pdfReportGenerator);
        pdfReportGenerator.addCoverPage("TC03_Verify_FlightSearch","Verify User is able to search flights");
        SoftAssert softAssert=new SoftAssert();
        softAssert.assertTrue(homepage.closeLoginPopUp());
        softAssert.assertTrue(homepage.searchForFlights(from, to));
        boolean status=homepage.verifySearchResult(from, to);
        softAssert.assertTrue(status);
        softAssert.assertAll();
    }
}
