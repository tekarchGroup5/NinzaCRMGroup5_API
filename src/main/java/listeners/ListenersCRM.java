package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import tests.BaseTest;
import utils.CommonUtils;

public class ListenersCRM implements ITestListener {

	public void onTestStart(ITestResult result) {

		System.out.println(result.getName() + " Started");
	}

	public void onTestFailure(ITestResult result) {
		BaseTest.test.get().addScreenCaptureFromPath(CommonUtils.captureScreenshot(BaseTest.getBrowser()));
		// api_BaseTest.test.get().addScreenCaptureFromPath(CommonUtils.captureScreenshot(api_BaseTest.getBrowser()));

		BaseTest.test.get().log(Status.FAIL, result.getName() + " FAILED");
		System.out.println("ExtentTest in listener:" + BaseTest.test.get());
	}

	public void onFinish(ITestContext context) {
		System.out.println(context.getName() + " Finished");
	}

	public void onTestSuccess(ITestResult result) {
		BaseTest.test.get().log(Status.PASS, result.getName() + " PASSED");

	}

}
