package tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import listeners.ListenersCRM;
import pages.CRM_HomePage;
import pages.CRM_LoginPage;
import utils.FileUtils;
import utils.ReportManager;

@Listeners(ListenersCRM.class)
public class BaseTest {

	public static ExtentReports extent;
//	public static ExtentTest test;
	CRM_HomePage hp = null;
	public static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	public static Logger logger = LogManager.getLogger("BaseTest");

	public void setDriver(String browserName, boolean headless) {
		WebDriver driver = getDriver(browserName, headless);// init
		threadLocalDriver.set(driver);// stores it
		driver.manage().deleteAllCookies(); // Clear cookies to start fresh
	}

	public static WebDriver getBrowser() {
		return threadLocalDriver.get();
	}

	public WebDriver getDriver(String browserName, boolean headless) {
		WebDriver driver = null;
		switch (browserName.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
		//	if (headless) {
			//	options.addArguments("--headless=new");
			//}
			options.addArguments("--incognito");
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-blink-features=AutomationControlled");
			options.addArguments("--disable-password-manager-reauthentication");
			options.addArguments("--disable-save-password-bubble");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--allow-insecure-localhost");
			options.addArguments("--guest");
			options.addArguments("--profile-directory=/Users/user/Library/Application Support/Google/Chrome/Default");

			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);

			driver = new ChromeDriver(options);
			driver.manage().window().maximize(); // priyanka
			break;
		case "safari":
			driver = new SafariDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();

			break;
		default:
			logger.error("Invalid browser specified: " + browserName);
			throw new IllegalArgumentException("Unsupported browser: " + browserName);
		}
		return driver;
	}

	@BeforeSuite
	public void setupSuite() {
		extent = ReportManager.getInstance();
	}

	@AfterSuite
	public void tearDownFinal() {
		extent.flush();
	}

	@Parameters("bName")
	@BeforeMethod(alwaysRun = true)
	public void setupTest(@Optional("chrome") String browserName, Method method)
			throws FileNotFoundException, IOException, InterruptedException {
		ExtentTest ext = extent.createTest(method.getName());
		test.set(ext);
		System.out.println("ExtentTest in BaseTest:" + ext);
		// Initialize and configure driver
		setDriver(browserName, false);
		WebDriver driver = getBrowser();
		driver.manage().window().maximize();// added this code later to maximize the browser window, this will be a git
											// conflict
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		// Navigate to CRM URL
		String crmUrl = FileUtils.readLoginPropertiesFile("prod.url");
		driver.get(crmUrl);
		logger.info("Navigated to CRM URL: " + crmUrl);

		// Perform login
		CRM_LoginPage loginPage = new CRM_LoginPage(driver);
		String validUsername = FileUtils.readLoginPropertiesFile("valid.admin.username");
		String validPassword = FileUtils.readLoginPropertiesFile("valid.admin.password");
		CRM_HomePage homePage = loginPage.loginToApp(driver, validUsername, validPassword);
		// homePage.handleAlertIfPresent();

		// Validate login success
		Assert.assertTrue(homePage.isHomePage(), "User should be on CRM Home Page after login.");
		logger.info("Login to Ninza CRM validated successfully.");

		// Store homePage reference for tests
		this.hp = homePage;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDownTest() {
		WebDriver driver = getBrowser();
		if (driver != null) {
			driver.quit();
			threadLocalDriver.remove();
		}
	}

}