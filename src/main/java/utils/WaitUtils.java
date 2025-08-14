package utils;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import constants.WaitConstant;

public class WaitUtils {

	// Wait for visibility of element located by 'by', then return it
	public static WebElement explicitlyWaitForVisibility(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Boolean explicitlyWaitForWindowToOpen(WebDriver driver, int expectedWindowCount) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedWindowCount));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Wait for invisibility of the given element, returns true if invisible
	public static void explicitlyWaitForInVisibility(WebDriver driver, WebElement elementToWait) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			wait.until(ExpectedConditions.invisibilityOf(elementToWait));
		} catch (TimeoutException e) {
			System.out.println("Toast did not disappear in time, continuing anyway.");
		}
	}
	
	// Wait for element located by 'by' to be clickable, then return it
	public static WebElement explicitlyWaitForClickableElement(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		try {
			return wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Fluent wait for the element to be visible and enabled
	public static WebElement fluentlyWait(WebDriver driver, By by) {
		Wait<WebDriver> fWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofMillis(500)).ignoring(Exception.class);

		return fWait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				WebElement element = driver.findElement(by);
				if (element.isDisplayed() && element.isEnabled()) {
					return element;
				}
				return null;
			}
		});
	}

	public static WebElement explicitlyWaitForVisibility(WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			return wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static WebElement explicitlyWaitForClickableElement(WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		try {
			return wait.until(ExpectedConditions.elementToBeClickable(element));

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static WebElement explicitlyWaitPresenceOfTheElement(WebDriver driver, List<WebElement> pages) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		try {
			return wait.until(ExpectedConditions.elementToBeClickable((By) pages));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void waitForPageToLoad() throws InterruptedException {

		Thread.sleep(8000);
	}

     /*   return fWait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                WebElement element = driver.findElement(by);
                if (element.isDisplayed() && element.isEnabled()) {
                    return element;
                }
                return null;
            }
        });
    }*/

    
	public static boolean waitForElement(WebDriver driver, WebElement element) {
		boolean isElementClickable = false;
		WebDriverWait wait =new WebDriverWait(driver, WaitConstant.WAIT_FOR_ELEMENT);
		try {
		wait.until((ExpectedConditions.elementToBeClickable(element)));
		isElementClickable=true;
		} catch (Exception e){
			e.printStackTrace();
			
		}
		return isElementClickable;
		}
	
	public static boolean waitForElementToDisappear(WebDriver driver, WebElement element) {
		boolean isElementInvisible = false;
		WebDriverWait wait =new WebDriverWait(driver, WaitConstant.WAIT_FOR_ELEMENT_TO_DISAPPEAR);
		try {
		wait.until((ExpectedConditions.invisibilityOf(element)));
		isElementInvisible=true;
		} catch (Exception e){
			e.printStackTrace();
			
		}
		return isElementInvisible;
		}

   
}