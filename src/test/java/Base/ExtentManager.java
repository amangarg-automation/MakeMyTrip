package Base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;

public class ExtentManager {

    public static ExtentReports getInstance(ITestContext context)
    {
        String reportPath=System.getProperty("user.dir")+"/ExtentReports/"+context.getAttribute("suiteName");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        ExtentReports reports = new ExtentReports();
        reports.attachReporter(sparkReporter);
        reports.setSystemInfo("Tester",System.getProperty("user.name"));
        return reports;
    }
}
