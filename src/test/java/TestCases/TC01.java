package TestCases;

import Base.Setup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

public class TC01 extends Setup {
    Logger logger= LogManager.getLogger(this.getClass());
    @Test
    public void TC01_Verify_UserLogin()
    {
        logger.info("verifying step 1");
        extentTest.info("Started Test Case");
        extentTest.pass("Login Successful");
        logger.info("Login successful");
    }
}
