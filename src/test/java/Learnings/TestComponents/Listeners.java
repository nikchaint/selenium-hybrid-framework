package Learnings.TestComponents;

import java.io.IOException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Learnings.Resources.ExtentReportsNG;

// This Listeners class implements TestNG's ITestListener to listen to test execution events
public class Listeners extends BaseTest implements ITestListener {
	
	// Create ExtentReports object using a method from ExtentReportsNG class
	ExtentReports report = ExtentReportsNG.getReportObject();
	
	// Create ExtentTest object for individual test logs
	ExtentTest test;
	
	// Thread-safe ExtentTest to handle parallel execution without overriding reports
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		// Create a new ExtentTest entry for the currently starting test method
		test = report.createTest(result.getMethod().getMethodName());
		extentTest.set(test); // Set it to thread-local for thread safety
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		extentTest.get().log(Status.PASS, "Test is Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// Log the exception/error in Extent Report
		extentTest.get().fail(result.getThrowable());
		
		// Get the driver instance from the failing test class
		Object testClass = result.getInstance();
		driver = ((BaseTest) testClass).driver;
//		try {
//			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		String filePath = null;
		
		try {
			// Capture the screenshot and store the file path
		  filePath = getScreenShot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Attach the screenshot to the report
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		report.flush();
	}
	

}
