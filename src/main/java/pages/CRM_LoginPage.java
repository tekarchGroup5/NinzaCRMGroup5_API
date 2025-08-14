package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import utils.WaitUtils;


public class CRM_LoginPage extends BasePage {

    public CRM_LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "Login")
    public WebElement loginButton;

    @FindBy(xpath = "//input[@id='username']")
    public WebElement userName;

    @FindBy(xpath = "//input[@id='inputPassword']")
    public WebElement password;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement signInButton;

    @FindBy(xpath = "//a[contains(text(),'Forgot password?')]")
    public WebElement forgotPassword;
    @FindBy(id = "navbarNav")
    public WebElement navigationBar;

    @FindBy(how = How.ID, using = "error")
    public WebElement errorMessage;

    public void enterUsername(String username) {
        // Use explicit wait with locator instead of WebElement
        WebElement usernameField = WaitUtils.explicitlyWaitForVisibility(driver, By.xpath("//input[@id='username']"));
        usernameField.clear();
        usernameField.sendKeys(username);
        logger.debug("Username is entered");
    }

    public void enterPassword(String passWord) {
        WebElement passwordField = WaitUtils.explicitlyWaitForVisibility(driver, By.xpath("//input[@id='inputPassword']"));
        passwordField.clear();
        passwordField.sendKeys(passWord);
        logger.debug("Password is entered");
    }

    public void clickSignInButton() {
        WebElement signInBtn = WaitUtils.explicitlyWaitForClickableElement(driver, By.xpath("//button[@type='submit']"));
        signInBtn.click();
        logger.debug("Sign In button clicked");
    }

    public String getErrorMessage() {
        WebElement errorMsg = WaitUtils.explicitlyWaitForVisibility(driver, By.id("error"));
        String msg = errorMsg.getText();
        logger.debug("Error message fetched: " + msg);
        return msg;
    }

    public CRM_HomePage loginToApp(WebDriver driver, String username, String password) {
        logger.debug("Navigated to the login page: " + driver.getCurrentUrl());

        enterUsername(username);
        enterPassword(password);
        clickSignInButton();

        // Wait for home page unique element to confirm successful login
        WaitUtils.explicitlyWaitForVisibility(driver, By.id( "navbarNav")); // Replace with real locator

        logger.debug("Login successful, landed on Home Page.");
        return new CRM_HomePage(driver);
    }
}