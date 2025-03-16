package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportGenerate {
    String path;
    ExtentReports extentReport = new ExtentReports();
    ExtentSparkReporter sparkReporter;
    ExtentTest extentTest;

    private static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void generateReport(String apiName) {
        extentTest = extentReport.createTest("Prueba de api " + apiName);
        path = "./sparkreporter/" + apiName + "-" + getDateTime() + ".html";
        sparkReporter = new ExtentSparkReporter(path);
        extentReport.attachReporter(sparkReporter);
    }

    public void closeConnectionExtent() {
        extentReport.flush();
    }

    public void getExtentTestLog(Status status, String message) {
        extentTest.log(status, message);
    }
}