package Learnings.Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportsNG {

	public static ExtentReports getReportObject() {
		String reportsPath = System.getProperty("user.dir")+"//reports//index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(reportsPath);
		reporter.config().setReportName("Extent Reports");
		reporter.config().setDocumentTitle("Selenium Test reports");
		
		ExtentReports report = new ExtentReports();
		report.attachReporter(reporter);
		report.setSystemInfo("Tester", "Nikhil Chaudhari");
		
		return report;
	}
}
