package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CRM_CreateUsersPage extends BasePage {

	@FindBy(xpath = "//input[@name='empName']")
	WebElement userFullName;

	@FindBy(xpath = "//input[@name='mobileNo']")
	WebElement mobileNum;

	@FindBy(xpath = "//input[@name=\"email\"]")
	WebElement email;

	@FindBy(xpath = "//input[@name='username']")
	WebElement userName;

	@FindBy(xpath = "//input[@name='password']")
	WebElement password;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement CUserButton;

	@FindBy(xpath = "//input[@name='dob']")
	WebElement dob;

	@FindBy(xpath = "//div[@role='alert']")
	WebElement successMsg;

	// Constructor
	public CRM_CreateUsersPage(WebDriver driver) {
		super(driver);
	}
	public void enterUserFullName(String userFullName1){
		userFullName.sendKeys(userFullName1);
	}
	public void enterMobileNum(String mobNum){
		mobileNum.sendKeys(mobNum);
	}
	public void enterEmail(String emailId){
		email.sendKeys(emailId);
	}
	public void enterUserName(String UName){
		userName.sendKeys(UName);
	}
	public void enterPassword(String Password){
		password.sendKeys(Password);
	}
	public void clickCreateUserButton(){
		CUserButton.click();
	}
	public boolean verifyUserCreated(String userName) {

		return driver.getPageSource().contains(userName);
	}

}
