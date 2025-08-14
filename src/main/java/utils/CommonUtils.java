package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;

import constants.FileConstants;

public class CommonUtils {
	
	public static String getTimeStamp() {
		return new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	}

	public static String captureScreenshot(WebDriver driver) {
		String filePath = FileConstants.SCREENSHOTS_FOLDER_PATH;
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dst = new File(filePath);
		src.renameTo(dst);
		return filePath;
	}

//	@DataProvider(name = "ValidAccounts")
//	public Object loginTestDataValid() {
////		To read those user accounts logic
//		return new Object[][] { {"mithun@tek.com", "12345" }, { "deek@tek.com", "12345" },
//				{ "dean@tek.com", "12345" } };
//	}

	}
