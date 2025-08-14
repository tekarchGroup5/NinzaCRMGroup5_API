package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utils.WaitUtils;

public class CRM_OpportunitiesPage extends BasePage {

	@FindBy(xpath = "//button[@class ='btn btn-info']")
	WebElement createOpportunityButton;

	@FindBy(xpath = "//input[@name='opportunityId']")
	WebElement OpportunityIdField;

	@FindBy(xpath = "//input[@name='opportunityName']")
	WebElement opportunityName;

	@FindBy(xpath = "//input[@name='amount']")
	WebElement Amount;

	@FindBy(xpath = "//input[@name='businessType']")
	WebElement business_Type;

	@FindBy(xpath = "//input[@name='nextStep']")
	WebElement next_Step;

	@FindBy(xpath = "//input[@name='salesStage']")
	WebElement sales_Stage;

	@FindBy(xpath = "//input[@name='assignedTo']")
	WebElement assigned_To;

	@FindBy(xpath = "//input[@name='expectedCloseDate']")
	WebElement expected_CloseDate;

	@FindBy(xpath = "//input[@name='probability']")
	WebElement probability_Field;

	@FindBy(xpath = "(//button[@type='button'])[2]")
	WebElement lead_lookupButton;

	@FindBy(xpath = "(//input[@type='text'])[7]")
	WebElement leadField;

	@FindBy(xpath = "//input[@id='search-input']")
	WebElement searchTextBox;

	@FindBy(xpath = "//select[@id='search-criteria']")
	WebElement searchDropdown;

	@FindBy(xpath = "(//button[@class='select-btn'])[1]")
	WebElement lead_selectButton1;

	@FindBy(xpath = "(//button[@class='select-btn'])[2]")
	WebElement lead_selectButton2;

	@FindBy(xpath = "(//button[@class='select-btn'])[3]")
	WebElement lead_selectButton3;

	@FindBy(xpath = "(//button[@class='select-btn'])[4]")
	WebElement lead_selectButton4;

	@FindBy(xpath = "(//button[@class='select-btn'])[5]")
	WebElement lead_selectButton5;

	@FindBy(xpath = "//textarea[@name='description']")
	WebElement description_field;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement createopportunityButtonInForm;

	@FindBy(xpath = "//table[@class='table table-striped table-hover']/tbody/tr/td")
	WebElement opportunityIdFieldInList;

	@FindBy(xpath = "//table[@class='table table-striped table-hover']/tbody/tr/td[2]")
	WebElement opportunityNameInList;

	@FindBy(xpath = "//a[@class='edit']")
	WebElement editButtonInList;

	@FindBy(xpath = "//div[@class='Toastify__toast-container Toastify__toast-container--top-right']")
	WebElement successMessageLocator;

	@FindBy(xpath = "//table[@class='table table-striped table-hover']")
	WebElement OpportunitiesListLocator;

	@FindBy(xpath = "//table[@class='table table-striped table-hover']/tbody/tr/td[7]")
	WebElement probabilityInListLocator;

	@FindBy(xpath = "//div[@class='Toastify__toast-body']")
	WebElement BlankLeadErrorMessage;

	public CRM_OpportunitiesPage(WebDriver driver) {
		super(driver);
	}

	public void clickCreateOpportunity() {
		WaitUtils.explicitlyWaitForClickableElement(driver, createOpportunityButton);

		createOpportunityButton.click();
	}

	public void enterOpportunityName(String oppName) {
		enterText(opportunityName, oppName);

	}

	public void enterAmount(String amount) {
		enterText(Amount, amount);

	}

	public void enterBusinessType(String businessType) {
		enterText(business_Type, businessType);

	}

	public void enterExpectedCloseDate(String expectedCloseDate) {
		enterText(expected_CloseDate, expectedCloseDate);

	}

	public void enterAssignedTo(String assignedTo) {
		enterText(assigned_To, assignedTo);
	}

	public void enterNextStep(String nextStep) {
		enterText(next_Step, nextStep);
	}

	public void enterSalesStage(String salesStage) {
		enterText(sales_Stage, salesStage);

	}

	public void selectLead(String value, String lead) throws InterruptedException {
		// Step1. store the parent window
		String parentWindow = getParentWindow();

		// Step2 click on the lookup button
		lead_lookupButton.click();

		// step3 wait and switch to the new window
		if (WaitUtils.explicitlyWaitForWindowToOpen(driver, 2)) {
			switchToChildWindow();
		} else {
			throw new RuntimeException("New Window did not open");
		}

		// step4 search dropdown is clicked
		searchDropdown.click();
		// Step5 search dropdown value is selected
		selectValue(searchDropdown, value);
		// step6 Lead name is entered in search text box
		enterText(searchTextBox, lead);
		WaitUtils.waitForPageToLoad();
		// step 7 first lead is selected
		lead_selectButton1.click();

		// step5 switch back to parent window
		switchToParentWindow(parentWindow);

	}

	public void enterProbability(String probability) {
		enterText(probability_Field, probability);
	}

	public void enterDescription(String description) {
		enterText(description_field, description);
	}

	public void clickCreateOpportunityInForm() {
		createopportunityButtonInForm.click();
	}

	public boolean verifyOpportunityCreatedWithMandatoryFields(String oppName) {

		return driver.getPageSource().contains(oppName);
	}

	public boolean verifyopportunityCreatedWithAllFields(String oppName) {

		return driver.getPageSource().contains(oppName);
	}

	public String getOpportunityId() {
		return opportunityIdFieldInList.getText();
	}

	public boolean isOpportunityIdIsEditable() {
		editButtonInList.click();
		WaitUtils.explicitlyWaitForVisibility(driver, OpportunityIdField);
		return OpportunityIdField.isEnabled() && OpportunityIdField.getAttribute("readonly") == null;

	}

	public void waitForOpportunityListOrSuccessMessage() {
		WaitUtils.explicitlyWaitForVisibility(driver, successMessageLocator);
		WaitUtils.explicitlyWaitForVisibility(driver, OpportunitiesListLocator);

	}

	public void waitForSuccessMessageToDisappear() {
		WaitUtils.explicitlyWaitForInVisibility(driver, successMessageLocator);
	}

	public String getProbabilityValue() {
		return probabilityInListLocator.getText();
	}

	public String getAmountErrorMessage() {
		return getErrorMessage(Amount);
	}

	public String getProbabilityErrorMessage() {
		return getErrorMessage(probability_Field);
	}

	public String getOpportunityNameErrorMessage() {
		return getErrorMessage(opportunityName);
	}

	public String getBusinessTypeErrorMessage() {
		return getErrorMessage(business_Type);
	}

	public String getNextStepErrorMessage() {
		return getErrorMessage(next_Step);
	}

	public String getSalesStageErrorMessage() {
		return getErrorMessage(sales_Stage);

	}

	public String getLeadErrorMessage() {
		WaitUtils.explicitlyWaitForVisibility(driver, BlankLeadErrorMessage);
		return BlankLeadErrorMessage.getText();
	}

	public void waitForSuccessMessage() {
		WaitUtils.explicitlyWaitForVisibility(driver, successMessageLocator);

	}

	public String getDuplicateErrorMessage() {
		return successMessageLocator.getText();
	}

	public String getFirstRowOpportunityId() {
		return opportunityIdFieldInList.getText();
	}

	public boolean isOpportunityCreatedWithName(String oppName) {

		return opportunityNameInList.getText().trim().equalsIgnoreCase(oppName);
	}

	public String getExpectedCloseDateErrorMessage() {
		return getErrorMessage(expected_CloseDate);
	}

	public void clickExpectedCloseDateField() {
		expected_CloseDate.click();

	}

	// Use JS to set a valid date
	public void setExpectedCloseDate(String date) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value = arguments[1];", expected_CloseDate, date);
	}

	// Optional: Trigger calendar by focus (for visibility check or visual testing)
	public void focusOnExpectedCloseDate() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].focus();", expected_CloseDate);
	}

	// Fetch selected value
	public String getSelectedCloseDate() {
		return expected_CloseDate.getAttribute("value");
	}

	public String getOpportunityNameFieldValue() {
		return opportunityName.getAttribute("value");
	}

	public String getBusinessTypeFieldValue() {
		return business_Type.getAttribute("value");
	}

	public String getNextStepFieldValue() {
		return next_Step.getAttribute("value");
	}

	public String getSalesStageFieldValue() {
		return sales_Stage.getAttribute("value");
	}

	public String getSelectedLead() {
		return leadField.getAttribute("value");
	}

	public String getAmountValue() {
		return Amount.getAttribute("value");
	}

	

}
