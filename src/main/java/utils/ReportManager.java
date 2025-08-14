package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import constants.FileConstants;

public class ReportManager {
	private ReportManager() {

	}

	private static ExtentReports extent;

	public static ExtentReports getInstance() {
		if (extent == null) {
			createInstance(FileConstants.REPORTS_FILE_PATH);
		}
		return extent;
	}

	private static ExtentReports createInstance(String fileName) {
		ExtentSparkReporter sparkReport = new ExtentSparkReporter(fileName);
		sparkReport.config().setTheme(Theme.DARK);
		sparkReport.config().setDocumentTitle("NinzaCRMGroup5 Automation Report");
		sparkReport.config().setEncoding("utf-8");
		extent = new ExtentReports();
		extent.attachReporter(sparkReport);
		return extent;
	}

}
