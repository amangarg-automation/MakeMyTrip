package Reporters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    public static ExtentReports getInstance(String tc)
    {
        String date=new java.text.SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new java.util.Date());
        String reportPath=System.getProperty("user.dir")+"/ExtentReports/"+tc+"/"+tc+"_"+date+".html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        ExtentReports reports = new ExtentReports();
        reports.attachReporter(sparkReporter);
        reports.setSystemInfo("Tester",System.getProperty("user.name"));
        return reports;
    }
}
