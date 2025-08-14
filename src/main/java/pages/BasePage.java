package pages;

import java.time.Duration;

import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	public WebDriver driver;
	Select select;
	JavascriptExecutor js;

	public static Logger logger = LogManager.getLogger("Basepage");

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public void handleAlertIfPresent() {

		try {
			int retries = 2; // Retry up to 10 times
			for (int i = 0; i < retries; i++) {
				try {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
					wait.until(ExpectedConditions.alertIsPresent());

					Alert alert = driver.switchTo().alert();
					logger.info("Alert found after login with text: " + alert.getText());

					alert.accept();
					logger.info("Alert accepted successfully after login.");
					return; // exit method after handling
				} catch (TimeoutException | NoAlertPresentException e) {
					// No alert yet, wait briefly then retry
					Thread.sleep(1000);
				}
			}
			logger.info("No alert appeared after login within retry window.");
		} catch (Exception e) {
			logger.error("Unexpected error while handling alert: " + e.getMessage());
		}

	}

	public void switchToChildWindow() {
		String parentWindow = driver.getWindowHandle();
		Set<String> Handles = driver.getWindowHandles();
		for (String handle : Handles) {
			if (!handle.equals(parentWindow)) {
				driver.switchTo().window(handle);
			}
		}
	}

	public void switchToParentWindow(String parentWindow) {
		Set<String> handles = driver.getWindowHandles();
		for (String handle : handles) {
			if (!handle.equals(parentWindow)) {
				driver.switchTo().window(handle);
			}
		}
		driver.switchTo().window(parentWindow);
	}

	public String getParentWindow() {
		String parentWindow = driver.getWindowHandle();
		return parentWindow;
	}

	public static String getTextFromElement(WebElement ele) {// priyanka
		String data = ele.getText();
		return data;
	}

	public void selectText(WebElement ele, String text) {
		select = new Select(ele);
		select.selectByVisibleText(text);
	}

	public void selectValue(WebElement ele, String value) {
		select = new Select(ele);
		select.selectByValue(value);
	}

	public void selectIndex(WebElement ele, int index) {
		select = new Select(ele);
		select.selectByIndex(index);
	}

	public void refreshpage() {
		driver.navigate().refresh();
	}

	public void clearElement(WebElement ele) {
		if (ele.isDisplayed()) {
			ele.clear();
		} else {
			System.out.println(" element is not displayed.");
		}
	}

	public void enterText(WebElement ele, String data) {
		if (ele.isDisplayed()) {
			clearElement(ele);
			ele.sendKeys(data);
		} else {
			System.out.println(" element is not displayed.");
		}
	}

	public void switchFrame(WebElement frame) {
		driver.switchTo().frame(frame);
	}

	public void switchToDefault() {
		driver.switchTo().defaultContent();
	}

	public void scrolldown() {
		js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 500)");
	}


	public static String errorValidationMsg(WebElement ele)// priyanka
	{

		String validationMessage = ele.getAttribute("validationMessage");
		return validationMessage;

	}

	public static String getValueFrmField(WebElement ele)// priyanka
	{

		String value = ele.getAttribute("value");
		return value;

	}

	public static int checkDropDownOptions(WebElement ele) {// priyanka
		Select dropDwn = new Select(ele);
		// Get all options
		List<WebElement> options = dropDwn.getOptions();
		return options.size();
	}


	public String getErrorMessage(WebElement element) {
		js = (JavascriptExecutor) driver;
		String validationMsg = (String) js.executeScript("return arguments[0].validationMessage;", element);
		return validationMsg;
	}

}
