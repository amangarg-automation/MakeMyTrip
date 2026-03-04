package TestCases;

import Base.Setup;
import PageElements.Deals.DealsPage;
import PageElements.Homepage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class TC04 extends Setup {
    @Test(groups = {"smoke testing","sanity testing"})
    public void TC04_Verify_Discount_Codes() throws IOException {
        pdfReportGenerator.addCoverPage("TC04_Verify_Discount_Codes","Verify Discount codes");
        Homepage hm=new Homepage(driver,pdfReportGenerator);
        DealsPage dl=new DealsPage(driver,pdfReportGenerator);
        SoftAssert softAssert=new SoftAssert();
        softAssert.assertTrue(hm.closeLoginPopUp());
        hm.clickHoliCabBookNow();
        softAssert.assertTrue(dl.verifyDealCodes());
        dl.getDealCodes();
        softAssert.assertAll();
    }
}
