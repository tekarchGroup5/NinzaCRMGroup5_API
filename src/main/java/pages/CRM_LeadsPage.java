package pages;

import java.time.Duration;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.WaitUtils;

public class CRM_LeadsPage extends BasePage {

	public CRM_LeadsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//a[normalize-space()='Leads']")
	private WebElement leads;

	@FindBy(xpath = "//span[normalize-space()='Create Lead']")
	private WebElement createLeadButton;

	@FindBy(xpath = "//input[@name='name']")
	private WebElement inputleadName;

	@FindBy(xpath = "//input[@name=\"leadId\"]")
	private WebElement LeadIdfeild;

	@FindBy(xpath = "//input[@name='company']")
	private WebElement inputCompany;

	@FindBy(xpath = "//input[@name='leadSource']")
	private WebElement inputLeadSource;

	@FindBy(xpath = "//input[@name='industry']")
	private WebElement inputIndustry;

	@FindBy(xpath = "//input[@name='phone']")
	public WebElement inputPhone;

	@FindBy(xpath = "//input[@name='leadStatus']")
	private WebElement inputLeadStatus;

	@FindBy(xpath = "//*[@id=\"content\"]/div[2]/form/div/div/div[2]/div[9]/div/button")
	private WebElement lookupCampaignButton;

	@FindBy(xpath = "//*[@id=\"content\"]/div[2]/form/div/div/div[2]/div[9]/div/input")
	private WebElement inputCampaign;

	@FindBy(xpath = "//button[@type=\"submit\" ]")
	private WebElement saveLeadButton;

	@FindBy(xpath = "//input[@id=\"search-input\"]")
	WebElement inputSearchCampaign;

	@FindBy(xpath = "//input[@name=\"annualRevenue\"]")
	private WebElement inputAnnualRevenue;

	@FindBy(xpath = "//input[@name=\"noOfEmployees\"]")
	private WebElement inputNoOfEmployees;

	@FindBy(xpath = "//input[@name=\"email\"]")
	private WebElement inputEmail;

	@FindBy(xpath = "//input[@name=\"secondaryEmail\"]")
	private WebElement inputSecondaryEmail;

	@FindBy(xpath = "//input[@name=\"rating\"]")
	private WebElement inputRating;

	@FindBy(xpath = "//input[@name=\"assignedTo\"]")
	private WebElement inputAssignedTo;

	@FindBy(xpath = "//textarea[@name=\"address\"]")
	private WebElement inputAddress;

	@FindBy(xpath = "//input[@name=\"city\"]")
	private WebElement inputCity;

	@FindBy(xpath = "//input[@name=\"country\"]")
	private WebElement inputCountry;

	@FindBy(xpath = "//input[@name=\"postalCode\"]")
	private WebElement inputPostalCode;

	@FindBy(xpath = "//input[@name=\"website\"]")
	private WebElement website;

	@FindBy(xpath = "//textarea[@name=\"description\"]")
	private WebElement inputDescription;

	@FindBy(xpath = "//div[@class=\"Toastify__toast-body\" and contains(text(),\"please select a campaign before submitting\")")
	private WebElement campaignValidationtMessage;

	@FindBy(css = "div.error-message")
	public WebElement emailErrorMessage;

	@FindBy(xpath = "//input[@placeholder=\"Search by Lead Id\"]")
	private WebElement searchLeadInput;

	@FindBy(xpath = "//input[@placeholder=\"Search by Lead Name\"]")
	private WebElement searchLeadNameInput;

	@FindBy(xpath = "//*[@id=\"content\"]/div[2]/div[1]/div/div[1]/div/div[3]/select/option[2]")
	private WebElement SearchbyLeadNameDropdown;
	
	@FindBy(xpath = "//*[@id=\"content\"]/div[2]/div[1]/div/div[1]/div/div[3]/select")
	private WebElement selectBoxID;

// Example method to return error message text
	public String getEmailErrorMessage() {
		return emailErrorMessage.getText().trim();
	}

	public void clickCreateLeadButtonAndGetTitle() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOf(createLeadButton));
		wait.until(ExpectedConditions.elementToBeClickable(createLeadButton));
		createLeadButton.click();

	}

	public void enterLeadName(String string) {
		inputleadName.sendKeys(string);

	}

	public @Nullable String getAttributeLeadName(String string) {
		inputleadName.sendKeys(string);
		return inputleadName.getAttribute("required"); // returns current text in input field
	}

	public String getLeadNameValue() {

		return inputleadName.getAttribute("value"); // returns current text in input field
	}

	public void enterCompany(String string) {
		inputCompany.sendKeys(string);
	}

	public void enterLeadSource(String string) {
		inputLeadSource.sendKeys(string);

	}

	public void enterIndustry(String string) {
		inputIndustry.sendKeys(string);

	}

	public String enterPhone(String string) {
		inputPhone.clear();
		inputPhone.sendKeys(string);
		return inputPhone.getAttribute("value");

	}

	public void enterLeadStatus(String string) {
		inputLeadStatus.sendKeys(string);
	}

	public void inputSearchCampaignmethod(String string) {
		inputSearchCampaign.sendKeys(string);

	}

	public String enterAnnualRevenue(String string) {
		inputAnnualRevenue.clear();
		inputAnnualRevenue.sendKeys(string);
		String annualRevenue = inputAnnualRevenue.getAttribute("value");
		return annualRevenue;
	}

	public String getAnnualRevenue() {
		String annualRevenue = inputAnnualRevenue.getAttribute("value");
		return annualRevenue;
	}

	public void lookupCampaign(String string) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
				lookupCampaignButton);
		wait.until(ExpectedConditions.elementToBeClickable(lookupCampaignButton)).click();
		// maximize the window to ensure the element is visible
		driver.manage().window().maximize();
		String parentWindow = driver.getWindowHandle();
		for (String windowHandle : driver.getWindowHandles()) {
			if (!windowHandle.equals(parentWindow)) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}
		WaitUtils.explicitlyWaitForVisibility(driver, By.xpath("//input[@id='search-input']"));

		WebElement campaignOption = WaitUtils.explicitlyWaitForClickableElement(driver,
				By.xpath("//*[@id=\"campaign-table\"]/tbody/tr/td/button[contains(@onclick,'" + string + "')]"));
		if (campaignOption != null) {
			campaignOption.click();
		} else {
			throw new RuntimeException("Campaign with ID '" + string + "' not found in the table.");
		}
		// Switch back to the parent window
		driver.switchTo().window(parentWindow);

	}

	public void SaveLeadButton() {
		// TODO Auto-generated method stub
		saveLeadButton.click();

	}

	
	public boolean checkIfLeadCreated(String leadName) {

		return driver.getPageSource().contains(leadName);
	}

	public @Nullable String enterNoOfEmployees(String noOfEmployees) {
		inputNoOfEmployees.clear();
		inputNoOfEmployees.sendKeys(noOfEmployees);
		return inputNoOfEmployees.getAttribute("value"); // returns current text in input field
	}

	public String getNumberOfEmployeesValue() {
		return inputNoOfEmployees.getAttribute("value").trim();
	}

	public String enterEmail(String email) {

		inputEmail.clear();
		inputEmail.sendKeys(email);
		return inputEmail.getAttribute("value"); 

	}

	public String enterSecondayEmail(String email) {

		inputEmail.clear();
		inputSecondaryEmail.sendKeys(email);
		return inputEmail.getAttribute("value"); 

	}

	public void enterSecondaryEmail(String secondaryEmail) {
		inputSecondaryEmail.clear();
		inputSecondaryEmail.sendKeys(secondaryEmail);

	}

	public void enterAssignedTo(String assignedTo) {
		inputAssignedTo.clear();
		inputAssignedTo.sendKeys(assignedTo);
	}

	public void enterAddress(String address) {
		inputAddress.clear();
		inputAddress.sendKeys(address);

	}

	public void enterCity(String city) {
		inputCity.clear();
		inputCity.sendKeys(city);

	}

	public void enterCountry(String country) {
		inputCountry.clear();
		inputCountry.sendKeys(country);

	}

	public void enterPostalCode(String postalCode) {

		inputPostalCode.clear();
		inputPostalCode.sendKeys(postalCode);

	}

	public void enterWebsite(String website2) {
		website.clear();
		website.sendKeys(website2);

	}

	public void enterDescription(String description) {

		inputDescription.clear();
		inputDescription.sendKeys(description);
	}

	public String getAttributeCompany(String string) {

		inputCompany.sendKeys(string);
		return inputleadName.getAttribute("required"); }

	public String getAttributeLeadSource(String string) {
		inputLeadSource.sendKeys(string);
		return inputleadName.getAttribute("required"); 
	}

	public String getAttributeIndustry(String string) {
		inputIndustry.sendKeys(string);
		return inputleadName.getAttribute("required"); 
	}

	public String getAttributePhone(String string) {
		inputPhone.sendKeys(string);
		return inputleadName.getAttribute("required"); 
	}

	public String getAttributeLeadStatus(String string) {
		inputLeadStatus.sendKeys(string);
		return inputleadName.getAttribute("required"); 
	}

	public String getAttributeCampaign(String string) {
		if (lookupCampaignButton.isDisplayed()) {
			lookupCampaignButton.click();
			inputSearchCampaign.sendKeys(string);
			WebElement campaignOption = WaitUtils.explicitlyWaitForClickableElement(driver,
					By.xpath("//*[@id=\"campaign-table\"]/tbody/tr/td/button[contains(@onclick,'" + string + "')]"));
			if (campaignOption != null) {
				campaignOption.click();
				return inputSearchCampaign.getAttribute("required");
			} else {
				throw new RuntimeException("Campaign with ID '" + string + "' not found in the table.");
			}

		}
		return string;
	}

	public String getCampaignMandatoryToastMessage() {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		if (campaignValidationtMessage.isDisplayed()) {
			return campaignValidationtMessage.getText();
		} else {
			return "No alert message displayed";
		}

	}

	public String getRatingDefaultValue() {
		return inputRating.getAttribute("value").trim();
	}

	public String RatingFieldInput(String rating) {
		inputRating.clear();
		inputRating.sendKeys(rating);
		return inputRating.getAttribute("value"); // returns current text in input field

	}

	public String searchLead(String leadName) {
		// Use the Select class
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOf(selectBoxID));

		Select dropdown = new Select(dropdownElement);

		// Select by visible text
		String optionToSelect = "Search by Lead Name"; // Replace with your actual option
		dropdown.selectByVisibleText(optionToSelect);
		searchLeadNameInput.clear();

		searchLeadNameInput.sendKeys(leadName);
		// Return the lead ID from the search input
		return searchLeadNameInput.getAttribute("value").trim();

	}

	public String getGeneratedLeadID(String leadName) {
		// XPath: Find row by leadName, then get sibling td containing LeadID
		WebElement leadIdElement = driver
				.findElement(By.xpath("//td[text()='" + leadName + "']/preceding-sibling::td[1]"));
		return leadIdElement.getText();
	}

public String getLeadIdAttribute(String leadName) {

	return LeadIdfeild.getAttribute("value");


}

}
