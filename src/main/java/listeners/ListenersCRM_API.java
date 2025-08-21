package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import api_tests.api_BaseTest;

public class ListenersCRM_API implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("[API Test] " + result.getName() + " Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (api_BaseTest.test.get() != null) {
            api_BaseTest.test.get().log(Status.PASS, result.getName() + " PASSED");
        }
        System.out.println("[API Test] " + result.getName() + " PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (api_BaseTest.test.get() != null) {
            api_BaseTest.test.get().log(Status.FAIL, result.getName() + " FAILED");
            api_BaseTest.test.get().log(Status.FAIL, result.getThrowable());
        }
        System.out.println("[API Test] " + result.getName() + " FAILED: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (api_BaseTest.test.get() != null) {
            api_BaseTest.test.get().log(Status.SKIP, result.getName() + " SKIPPED");
        }
        System.out.println("[API Test] " + result.getName() + " SKIPPED");
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("[API Suite] " + context.getName() + " Started");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("[API Suite] " + context.getName() + " Finished");
    }
}