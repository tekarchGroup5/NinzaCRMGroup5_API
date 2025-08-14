package tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import constants.FileConstants;
import pages.CRM_LeadsPage;
import pages.CRM_OpportunitiesPage;
import utils.CommonUtils;
import utils.ExcelUtils;
import utils.FileUtils;
import utils.Opportunity_DataProviderUtil;
import utils.WaitUtils;

public class Crm_CreateOpportunityTest extends BaseTest {
	public static Logger logger = LogManager.getLogger("Crm_CreateOpportunityTest");

//=========================================================================
	// 1. Create a Opportunity with mandatory fields only
//=========================================================================
	@Test(priority = 1, description = "Create a Opportunity with mandatory fields only", dataProvider = "opportunityData", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void TC_1_createOpportunityWithMandatoryFields(Map<String, String> data)
			throws InterruptedException, FileNotFoundException, IOException {

		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();

		oppPage.clickCreateOpportunity();
		logger.info("Create Opportunity button is clicked");

		String oppName = data.get("Opportunity Name");
		String amount = data.get("Amount");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");

		// fill the form using Page Object methods
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is entered");

		oppPage.enterAmount(amount);
		logger.info("Amount is entered");

		oppPage.enterBusinessType(businessType);
		logger.info("Business type is entered");

		oppPage.enterNextStep(nextStep);
		logger.info("Next step is entered");

		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is entered");

		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

		oppPage.selectLead(value, lead);
		logger.info("lead is selected");

		oppPage.clickCreateOpportunityInForm();
		logger.info("opportunity is created with mandatory fields: " + oppName + "," + lead);

		// validate Opportunity created
		Assert.assertTrue(oppPage.verifyOpportunityCreatedWithMandatoryFields(oppName), "Opportunity Creation failed.");
		logger.info("opportunity found succesfully: " + oppName);

	}

//=========================================================================
// 2. Create a Opportunity with all the fields
//=========================================================================

	@Test(priority = 2, description = "Create a opportunity with all the fields", dataProvider = "tc2Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void TC_2_createOpportunityWithAllFields(Map<String, String> data)
			throws FileNotFoundException, IOException, InterruptedException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();
		oppPage.clickCreateOpportunity();
		logger.info("Create Opportunity button is clicked");
		String oppName = data.get("Opportunity Name");
		String amount = data.get("Amount");
		String businessType = data.get("Business Type");
		String expectedClosedate = data.get("Expected Close Date");
		String assignedTo = data.get("Assigned To");
		String probability = data.get("Probability");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");
		String description = data.get("Description");

		// fill the form using Page Object methods
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is entered");

		oppPage.enterAmount(amount);
		logger.info("Amount is entered");

		oppPage.enterBusinessType(businessType);
		logger.info("Business type is entered");

		oppPage.enterExpectedCloseDate(expectedClosedate);
		logger.info("expected close date is entered");

		oppPage.enterAssignedTo(assignedTo);
		logger.info("Assigned to is entered");

		oppPage.enterProbability(probability);
		logger.info("Probability is entered");

		oppPage.enterNextStep(nextStep);
		logger.info("Next step is entered");

		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is entered");

		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");
		oppPage.selectLead(value, lead);
		logger.info("lead is selected");

		oppPage.enterDescription(description);
		logger.info("description is entered");

		oppPage.clickCreateOpportunityInForm();
		logger.info("opportunity is created");

		// validate Opportunity created
		Assert.assertTrue(oppPage.verifyopportunityCreatedWithAllFields(oppName), "Opportunity Creation failed.");
		logger.info("Opportunity found succesfully: " + oppName);
	}

//===========================================
//3.Opportunity ID should be auto-generated and unique
// ====================================================
	@Test(priority = 3, dataProvider = "allOpportunitiesData", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void TC_3_verifyOpportunityIdIsAutoGeneratedAndUnique(List<Map<String, String>> dataList)
			throws FileNotFoundException, IOException, InterruptedException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();
		String oppId1 = "", oppId2 = "";
		logger.info("Create Opportunity button is clicked");
		for (int i = 0; i < 2; i++) { // Only first 2 rows

			oppPage.clickCreateOpportunity();

			Map<String, String> data = dataList.get(i);

			String oppName = data.get("Opportunity Name");
			String amount = data.get("Amount");
			String businessType = data.get("Business Type");
			String nextStep = data.get("Next Step");
			String salesStage = data.get("Sales Stage");
			String lead = data.get("Lead");

// fill the form using Page Object methods
			oppPage.enterOpportunityName(oppName);
			logger.info("Opportunity name is entered");

			oppPage.enterAmount(amount);
			logger.info("Amount is entered");

			oppPage.enterBusinessType(businessType);
			logger.info("Business type is entered");

			oppPage.enterNextStep(nextStep);
			logger.info("Next step is entered");

			oppPage.enterSalesStage(salesStage);
			logger.info("Sales stage is entered");

			String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");
			oppPage.selectLead(value, lead);
			logger.info("lead is selected");

			oppPage.clickCreateOpportunityInForm();
			logger.info("opportunity is created");

			oppPage.waitForOpportunityListOrSuccessMessage();
			logger.info("Success message is appeared");

			String currentId = oppPage.getOpportunityId();
			logger.info("Opportunity ID " + (i + 1) + ": " + currentId);

			Assert.assertNotNull(currentId, "Opportunity ID is null");
			Assert.assertFalse(currentId.isEmpty(), "OpportunityId is empty");
			Assert.assertFalse(oppPage.isOpportunityIdIsEditable(), "Opportunity id field should be non-editable");
			hp.clickOpportunities();

			oppPage.waitForSuccessMessageToDisappear();

			if (i == 0) {
				oppId1 = currentId;
			} else
				oppId2 = currentId;
		}

		Assert.assertNotEquals(oppId1, oppId2, "Opportunity IDs should be unique");

	}
	// ===========================================
	// 4.Probability defaults to 0 if left blank
	// ====================================================

	@Test(priority = 4, dataProvider = "tc3Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void TC_4_VerifyProbabilityDefaultsToZeroWhenBlank(Map<String, String> data)
			throws FileNotFoundException, IOException, InterruptedException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();
		oppPage.clickCreateOpportunity();
		logger.info("Create Opportunity button is clicked");
		String oppName = data.get("Opportunity Name");
		String amount = data.get("Amount");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");
		// fill the form using Page Object methods
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is entered");

		oppPage.enterAmount(amount);
		logger.info("Amount is entered");

		oppPage.enterBusinessType(businessType);
		logger.info("Business type is entered");

		oppPage.enterNextStep(nextStep);
		logger.info("Next step is entered");

		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is entered");

		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

		oppPage.selectLead(value, lead);
		logger.info("lead is selected");

		oppPage.clickCreateOpportunityInForm();
		logger.info("opportunity is created with blank Probability");

		// Wait for success and navigate to details or list page
		oppPage.waitForOpportunityListOrSuccessMessage();

		// Get the displayed value of "Probability" from the opportunity details
		String actualProbability = oppPage.getProbabilityValue(); // implement this method if needed
		logger.info("Probability field value: " + actualProbability);

		// Assert it is 0
		Assert.assertEquals(actualProbability, "0", "Probability should default to 0 when left blank");
	}
	// ===========================================
	// 5 Verify Amount field is mandatory and numeric
	// ====================================================

	@Test(priority = 5, dataProvider = "tc3Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void TC_5_VerifyAmountFieldValidation(Map<String, String> data)
			throws FileNotFoundException, IOException, InterruptedException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();
		oppPage.clickCreateOpportunity();
		logger.info("Create Opportunity button is clicked");
		String oppName = data.get("Opportunity Name");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");

		// fill the form using Page Object methods without amount field
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is entered");

		oppPage.enterBusinessType(businessType);
		logger.info("Business type is entered");

		oppPage.enterNextStep(nextStep);
		logger.info("Next step is entered");

		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is entered");

		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

		oppPage.selectLead(value, lead);
		logger.info("lead is selected");

		// Step1: left amount field blank
		oppPage.enterAmount("");// simulate blank
		logger.info("Amount field is left blank");

		oppPage.clickCreateOpportunityInForm();
		logger.info("Create Opportunity button is clicked");

		String blankAmountError = oppPage.getAmountErrorMessage();
		Assert.assertEquals(blankAmountError, "Please fill out this field.", "Blank Amount error not shown properly");
		logger.info("Error is: " + blankAmountError);

		// === Step 2: Enter non-numeric amount ===
		oppPage.enterAmount("e");
		logger.info("Alphabet 'e' is entered in Amount field");

		oppPage.clickCreateOpportunityInForm();
		logger.info("Create Opportunity button is clicked");

		String invalidAmountError = oppPage.getAmountErrorMessage(); // reuse same method
		Assert.assertEquals(invalidAmountError.trim(), "Please enter a number.",
				"Non-numeric Amount error not shown properly");
		logger.info("Error is: " + invalidAmountError);

		logger.info("Amount field validation tested successfully");

	}
	// ===========================================
	// 6. Verify Probability field accepts numeric values
	// ====================================================

	@Test(dataProvider = "tc3Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void VerifyProbablityAcceptsNumericValues(Map<String, String> data)
			throws FileNotFoundException, IOException, InterruptedException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();
		oppPage.clickCreateOpportunity();
		logger.info("Create Opportunity button is clicked");
		String oppName = data.get("Opportunity Name");
		String amount = data.get("Amount");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");

		// fill the form using Page Object methods without amount field
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is entered");

		oppPage.enterBusinessType(businessType);
		logger.info("Business type is entered");

		oppPage.enterAmount(amount);
		logger.info("Amount is entered");

		oppPage.enterNextStep(nextStep);
		logger.info("Next step is entered");

		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is entered");

		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

		oppPage.selectLead(value, lead);
		logger.info("lead is selected");

		// Enter non-numeric value in probability field ===
		oppPage.enterProbability("e");
		logger.info("Alphabet 'e' is entered in Probability field");

		oppPage.clickCreateOpportunityInForm();
		logger.info("Create Opportunity button is clicked");

		String invalidProbabilityError = oppPage.getProbabilityErrorMessage();// reuse same method
		Assert.assertEquals(invalidProbabilityError.trim(), "Please enter a number.",
				"Non-numeric Probability error not shown properly");
		logger.info("Error is: " + invalidProbabilityError);

		logger.info("Probablity field validation tested successfully");

	}

	// ===========================================
	// 7. Verify Blank Opportunity Name
	// ====================================================

	@Test(dataProvider = "tc4Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void VerifyBlankOpportunityNameField(Map<String, String> data)
			throws FileNotFoundException, IOException, InterruptedException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();
		oppPage.clickCreateOpportunity();
		logger.info("Create Opportunity button is clicked");
		String oppName = data.get("Opportunity Name");
		String amount = data.get("Amount");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");

		// fill the form using Page Object methods without amount field
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is left blank");

		oppPage.enterBusinessType(businessType);
		logger.info("Business type is entered");

		oppPage.enterAmount(amount);
		logger.info("Amount is entered");

		oppPage.enterNextStep(nextStep);
		logger.info("Next step is entered");

		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is entered");

		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

		oppPage.selectLead(value, lead);
		logger.info("lead is selected");

		oppPage.clickCreateOpportunityInForm();
		logger.info("Create Opportunity button is clicked");

		String blankOpportunityNameError = oppPage.getOpportunityNameErrorMessage();
		Assert.assertEquals(blankOpportunityNameError, "Please fill out this field.",
				"Blank Opportunity Name error not shown properly");
		logger.info("Error is: " + blankOpportunityNameError);
		logger.info("Opportunity Name field validation tested successfully");

	}
	// ===========================================
	// 8. Verify Blank Business Type field
	// ====================================================

	@Test(dataProvider = "tc5Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void VerifyBlankBusinessTypeField(Map<String, String> data)
			throws FileNotFoundException, IOException, InterruptedException {

		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();
		oppPage.clickCreateOpportunity();
		logger.info("Create Opportunity button is clicked");
		String oppName = data.get("Opportunity Name");
		String amount = data.get("Amount");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");

		// fill the form using Page Object methods without amount field
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is entered");

		oppPage.enterBusinessType(businessType);
		logger.info("Business type is left blank");

		oppPage.enterAmount(amount);
		logger.info("Amount is entered");

		oppPage.enterNextStep(nextStep);
		logger.info("Next step is entered");

		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is entered");

		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

		oppPage.selectLead(value, lead);
		logger.info("lead is selected");

		oppPage.clickCreateOpportunityInForm();
		logger.info("Create Opportunity button is clicked");

		String blankBusinessTypeError = oppPage.getBusinessTypeErrorMessage();
		Assert.assertEquals(blankBusinessTypeError, "Please fill out this field.",
				"Blank Opportunity Name error not shown properly");
		logger.info("Error is: " + blankBusinessTypeError);
		logger.info("Business Type field validation tested successfully");

	}

	// ===========================================
	// 9. Verify Blank Next Step field
	// ====================================================

	@Test(dataProvider = "tc6Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void VerifyBlankNextStepField(Map<String, String> data)
			throws FileNotFoundException, IOException, InterruptedException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();
		oppPage.clickCreateOpportunity();
		logger.info("Create Opportunity button is clicked");
		String oppName = data.get("Opportunity Name");
		String amount = data.get("Amount");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");

		// fill the form using Page Object methods without amount field
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is entered");

		oppPage.enterBusinessType(businessType);
		logger.info("Business type is entered");

		oppPage.enterAmount(amount);
		logger.info("Amount is entered");

		oppPage.enterNextStep(nextStep);
		logger.info("Next step is left blank");

		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is entered");

		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

		oppPage.selectLead(value, lead);
		logger.info("lead is selected");

		oppPage.clickCreateOpportunityInForm();
		logger.info("Create Opportunity button is clicked");

		String blankNextStepFieldError = oppPage.getNextStepErrorMessage();
		Assert.assertEquals(blankNextStepFieldError, "Please fill out this field.",
				"Blank Next step error not shown properly");
		logger.info("Error is: " + blankNextStepFieldError);
		logger.info("Next Step field validation tested successfully");

	}

	// ===========================================
	// 10. Verify Blank Sales Stage field
	// ====================================================

	@Test(dataProvider = "tc7Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void VerifyBlankSalesStageField(Map<String, String> data)
			throws FileNotFoundException, IOException, InterruptedException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();
		oppPage.clickCreateOpportunity();
		logger.info("Create Opportunity button is clicked");
		String oppName = data.get("Opportunity Name");
		String amount = data.get("Amount");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");

		// fill the form using Page Object methods without amount field
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is entered");

		oppPage.enterBusinessType(businessType);
		logger.info("Business type is entered");

		oppPage.enterAmount(amount);
		logger.info("Amount is entered");

		oppPage.enterNextStep(nextStep);
		logger.info("Next step is entered");

		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is left blank");

		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

		oppPage.selectLead(value, lead);
		logger.info("lead is selected");

		oppPage.clickCreateOpportunityInForm();
		logger.info("Create Opportunity button is clicked");

		String blankSalesStageFieldError = oppPage.getSalesStageErrorMessage();
		Assert.assertEquals(blankSalesStageFieldError, "Please fill out this field.",
				"Blank Sales Stage error not shown properly");
		logger.info("Error is: " + blankSalesStageFieldError);
		logger.info("Sales Stage field validation tested successfully");

	}

	// ===========================================
	// 11. Verify Blank Lead field
	// ====================================================

	@Test(dataProvider = "tc8Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void VerifyBlankLeadField(Map<String, String> data) throws FileNotFoundException, IOException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();
		oppPage.clickCreateOpportunity();
		logger.info("Create Opportunity button is clicked");
		String oppName = data.get("Opportunity Name");
		String amount = data.get("Amount");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");

		// fill the form using Page Object methods without amount field
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is entered");

		oppPage.enterBusinessType(businessType);
		logger.info("Business type is entered");

		oppPage.enterAmount(amount);
		logger.info("Amount is entered");

		oppPage.enterNextStep(nextStep);
		logger.info("Next step is entered");

		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is entered");

		oppPage.clickCreateOpportunityInForm();
		logger.info("Create Opportunity button is clicked");

		String blankLeadFieldError = oppPage.getLeadErrorMessage();
		Assert.assertEquals(blankLeadFieldError, "Please select a Lead before submitting.",
				"Blank Lead error not shown properly");
		logger.info("Error is: " + blankLeadFieldError);
		logger.info("Lead field validation tested successfully");
	}

	// ===========================================
	// 12. Verify Create duplicate Opportunity (same Name & Lead)
	// ====================================================
	@Test(dataProvider = "tc9Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void verifyCreateDuplicateOpportunity(Map<String, String> data)
			throws InterruptedException, FileNotFoundException, IOException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();

		oppPage.clickCreateOpportunity();
		logger.info("Create Opportunity button is clicked");

		String oppName = data.get("Opportunity Name");
		String amount = data.get("Amount");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");

		// fill the form using Page Object methods
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is entered");

		oppPage.enterAmount(amount);
		logger.info("Amount is entered");

		oppPage.enterBusinessType(businessType);
		logger.info("Business type is entered");

		oppPage.enterNextStep(nextStep);
		logger.info("Next step is entered");

		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is entered");

		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

		oppPage.selectLead(value, lead);
		logger.info("lead is selected");

		oppPage.clickCreateOpportunityInForm();
		logger.info("First opportunity created with name and lead.");

		oppPage.waitForSuccessMessageToDisappear();

		String firstOpportunityId = oppPage.getFirstRowOpportunityId();
		logger.info("First Opportunity ID: " + firstOpportunityId);

		// Step2 : Try to create duplicate opportunity
		oppPage.clickCreateOpportunity();
		logger.info("Create Opportunity button is clicked");

		// fill the form using Page Object methods
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is entered");

		oppPage.enterAmount(amount);
		logger.info("Amount is entered");

		oppPage.enterBusinessType(businessType);
		logger.info("Business type is entered");

		oppPage.enterNextStep(nextStep);
		logger.info("Next step is entered");

		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is entered");

		String value2 = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

		oppPage.selectLead(value2, lead);
		logger.info("lead is selected");

		oppPage.clickCreateOpportunityInForm();
		logger.info("Attempted to create duplicate opportunity.");

		oppPage.waitForSuccessMessageToDisappear();

		String secondOpportunityId = oppPage.getFirstRowOpportunityId();
		logger.info("Second Opportunity ID: " + secondOpportunityId);

		// Step3: Verify Error message

		Assert.assertEquals(firstOpportunityId, secondOpportunityId, "Duplicate opportunity is allowed by the system.");
	}

//	===========================================
//	13. Verify Verify Probability accepts 0-100
//	====================================================

	@Test(dataProvider = "tc9Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void verifyProbabilityAcceptsOnlyValuesBetween0And100(Map<String, String> data)
			throws FileNotFoundException, IOException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();

		oppPage.clickCreateOpportunity();
		logger.info("Create Opportunity button is clicked");

		String oppName = data.get("Opportunity Name");
		String amount = data.get("Amount");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");
		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

		// function to try creating opportunity and capture validation message
		Consumer<String> attemptWithProbability = (String prob) -> {

			oppPage.enterOpportunityName(oppName);
			logger.info("Opportunity name is entered");

			oppPage.enterBusinessType(businessType);
			logger.info("Business type is entered");

			oppPage.enterAmount(amount);
			logger.info("Amount is entered");

			oppPage.enterNextStep(nextStep);
			logger.info("Next step is entered");

			oppPage.enterSalesStage(salesStage);
			logger.info("Sales stage is entered");

			try {
				oppPage.selectLead(value, lead);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("lead is selected");

			oppPage.enterProbability(prob);
			logger.info("Probability" + prob + "is entered");

			oppPage.clickCreateOpportunityInForm();
			logger.info("Create Opportunity button is clicked");

		};

		// 1. Negative value: -10

		attemptWithProbability.accept("-10");
		String msg1 = oppPage.getProbabilityErrorMessage();
		logger.info("Validation for -10:" + msg1);
		Assert.assertFalse(msg1.isEmpty(), "Expected validation message for -10");

		// 2. Above range: 150
		attemptWithProbability.accept("150");
		String msg2 = oppPage.getProbabilityErrorMessage();
		logger.info("Validation for 150:" + msg2);
		Assert.assertFalse(msg2.isEmpty(), "Expected validation message for 150");

		// 3. Valid value = 50
		attemptWithProbability.accept("50");

		oppPage.waitForSuccessMessageToDisappear();

		Assert.assertTrue(oppPage.isOpportunityCreatedWithName(oppName),
				"Opportunity not created with valid probability 50.");
	}

//	=============================================================
//	14. Verify Expected Close Date format (dd-mm-yyyy)
//	==============================================================

	@Test(dataProvider = "tc3Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void verifyExpectedCloseDateFormat(Map<String, String> data)
			throws FileNotFoundException, IOException, InterruptedException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();

		oppPage.clickCreateOpportunity();
		logger.info("Create Opportunity button is clicked");

		String oppName = data.get("Opportunity Name");
		String amount = data.get("Amount");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");
		String invalidDate = "02/10/2330";
		String validDate = "06/10/2027";
		// fill the form using Page Object methods
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is entered");

		oppPage.enterAmount(amount);
		logger.info("Amount is entered");

		oppPage.enterBusinessType(businessType);
		logger.info("Business type is entered");

		oppPage.enterExpectedCloseDate(invalidDate);
		logger.info("invalid date is entered :" + invalidDate);

		oppPage.enterNextStep(nextStep);
		logger.info("Next step is entered");

		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is entered");

		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

		oppPage.selectLead(value, lead);
		logger.info("lead is selected");

		oppPage.clickCreateOpportunityInForm();
		logger.info("create opportunity button is clicked");

		// capture the validation error
		String validationMessage = oppPage.getExpectedCloseDateErrorMessage();

		Assert.assertTrue(validationMessage != null && !validationMessage.isEmpty(),
				"Expected validation error for invalid date");

		logger.info("Validation message for invalid date:" + validationMessage);

		// enter the valid date

		oppPage.enterExpectedCloseDate(validDate);
		logger.info("valid date is entered :" + validDate);

		oppPage.clickCreateOpportunityInForm();
		logger.info("create opportunity button is clicked");

		// Assert no error is shown for valid adate

		String validDateMsg = oppPage.getExpectedCloseDateErrorMessage();
		Assert.assertTrue(validDateMsg == null || validDateMsg.isEmpty(), "Valid date should not trigger error");

		logger.info("Valid date accepted successfully.");
	}
//	=============================================================
//	15. Verify Save functionality
//	==============================================================

	
	@Test(dataProvider = "tc1Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void verifySaveFunctionality(Map<String, String> data)
			throws FileNotFoundException, IOException, InterruptedException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();
		oppPage.clickCreateOpportunity();
		logger.info("Navigated to Create Opportunity");

		String oppName = data.get("Opportunity Name");
		String amount = data.get("Amount");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");

		// Fill form
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is entered");

		oppPage.enterAmount(amount);
		logger.info("Amount is entered");

		oppPage.enterBusinessType(businessType);
		logger.info("Business type is entered");

		oppPage.enterNextStep(nextStep);
		logger.info("Next step is entered");

		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is entered");

		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

		oppPage.selectLead(value, lead);
		logger.info("lead is selected");

		oppPage.clickCreateOpportunityInForm();
		logger.info("clicked create opportunity button");

		// Wait for confirmation or record in list
		oppPage.waitForOpportunityListOrSuccessMessage();

		// Validate Opportunity Created
		Assert.assertTrue(oppPage.isOpportunityCreatedWithName(oppName), "Opportunity was not created successfully");
	}

//	=============================================================
//	16. Verify Validation messages appear near relevant fields
//	==============================================================
	
	@Test(dataProvider = "tc1Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void verifyValidationMessagesForEmptyMandatoryFields(Map<String, String> data)
			throws IOException, InterruptedException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();
		String oppName = data.get("Opportunity Name");
		String amount = data.get("Amount");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");

		oppPage.clickCreateOpportunity();
		logger.info("Navigated to Create Opportunity form");

		// Leave all mandatory fields blank and click Save
		oppPage.clickCreateOpportunityInForm();
		logger.info("Create opportunity button is clicked");
		String oppNameMsg = oppPage.getOpportunityNameErrorMessage();
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is entered");

		oppPage.clickCreateOpportunityInForm();
		logger.info("Create opportunity button is clicked");
		String amountMsg = oppPage.getAmountErrorMessage();
		oppPage.enterAmount(amount);
		logger.info("Amount is entered");

		oppPage.clickCreateOpportunityInForm();
		logger.info("Create opportunity button is clicked");
		String businessTypeMsg = oppPage.getBusinessTypeErrorMessage();
		oppPage.enterBusinessType(businessType);
		logger.info("Business type is entered");

		oppPage.clickCreateOpportunityInForm();
		logger.info("Create opportunity button is clicked");
		String nextStepMsg = oppPage.getNextStepErrorMessage();
		oppPage.enterNextStep(nextStep);
		logger.info("Next step is entered");

		oppPage.clickCreateOpportunityInForm();
		logger.info("Create opportunity button is clicked");
		String salesStageMsg = oppPage.getSalesStageErrorMessage();
		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is entered");

		oppPage.clickCreateOpportunityInForm();
		logger.info("Create opportunity button is clicked");

		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

		String leadMsg = oppPage.getLeadErrorMessage();
		oppPage.selectLead(value, lead);

		logger.info("Validation Message - Opportunity Name: " + oppNameMsg);
		logger.info("Validation Message - Amount: " + amountMsg);
		logger.info("Validation Message - Business Type: " + businessTypeMsg);
		logger.info("Validation Message - Next Step: " + nextStepMsg);
		logger.info("Validation Message - Sales Stage: " + salesStageMsg);
		logger.info("Validation Message - Lead: " + leadMsg);

		// Assert appropriate error message
		Assert.assertFalse(oppNameMsg.isEmpty(), "Opportunity Name validation message missing");
		Assert.assertFalse(amountMsg.isEmpty(), "Amount validation message missing");
		Assert.assertFalse(businessTypeMsg.isEmpty(), "Business Type validation message missing");
		Assert.assertFalse(nextStepMsg.isEmpty(), "Next Step validation message missing");
		Assert.assertFalse(salesStageMsg.isEmpty(), "Sales Stage validation message missing");
		Assert.assertFalse(leadMsg.isEmpty(), "Lead validation message missing");
	}

//	=============================================================
//	16. Verify whether the Expected Date is a Date Picker
//	==============================================================

	@Test(dataProvider = "tc1Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void verifyExpectedCloseDatePickerFunctionality(Map<String, String> data)
			throws IOException, InterruptedException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();
		oppPage.clickCreateOpportunity();
		logger.info("Navigated to Create Opportunity page");

		oppPage.focusOnExpectedCloseDate();
		logger.info("Expected close date field is clicked");
		// Set a valid date
		String validDate = "05-10-2026";
		oppPage.enterExpectedCloseDate(validDate);
		logger.info("Date is set using sendkeys");

		String selectedDate = oppPage.getSelectedCloseDate();
		logger.info("Selected Date from UI:" + selectedDate);

		LocalDate date = LocalDate.parse(selectedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String formattedDate = date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));

		oppPage.focusOnExpectedCloseDate();
		logger.info("Expected close date field is clicked");
		Assert.assertEquals(formattedDate, validDate, "Expected Close Date not set correctly");

		String oppName = data.get("Opportunity Name");
		String amount = data.get("Amount");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");

		// Fill form
		oppPage.enterOpportunityName(oppName);
		logger.info("Opportunity name is entered");

		oppPage.enterAmount(amount);
		logger.info("Amount is entered");

		oppPage.enterBusinessType(businessType);
		logger.info("Business type is entered");

		oppPage.enterNextStep(nextStep);
		logger.info("Next step is entered");

		oppPage.enterSalesStage(salesStage);
		logger.info("Sales stage is entered");

		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

		oppPage.selectLead(value, lead);
		logger.info("lead is selected");

		oppPage.clickCreateOpportunityInForm();
		logger.info("clicked create opportunity button");

		Assert.assertTrue(oppPage.isOpportunityCreatedWithName(oppName), "Opportunity was not created successfully");
	}
//	=============================================================
//	16. Verify Fields retain values after validation error
//	==============================================================


	@Test(dataProvider = "tc1Data", dataProviderClass = Opportunity_DataProviderUtil.class)
	public void verifyFieldsRetainDataAfterValidationError(Map<String, String> data)
			throws IOException, InterruptedException {
		CRM_OpportunitiesPage oppPage = hp.clickOpportunities();
		oppPage.clickCreateOpportunity();

		String oppName = data.get("Opportunity Name");
		String businessType = data.get("Business Type");
		String nextStep = data.get("Next Step");
		String salesStage = data.get("Sales Stage");
		String lead = data.get("Lead");

		// Fill all fields except "Amount"
		oppPage.enterOpportunityName(oppName);
		oppPage.enterBusinessType(businessType);
		oppPage.enterNextStep(nextStep);
		oppPage.enterSalesStage(salesStage);
		String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");
		oppPage.selectLead(value, lead);

		// Leave "Amount" blank and click Save
		oppPage.clickCreateOpportunityInForm();

		// Wait for validation error
		String validationMsg = oppPage.getAmountErrorMessage();
		logger.info("message is :" + validationMsg);
		Assert.assertNotNull(validationMsg, "Validation message is null");

		// Now check that other fields still hold values
		Assert.assertEquals(oppPage.getOpportunityNameFieldValue(), oppName, "Opportunity Name not retained");
		Assert.assertEquals(oppPage.getBusinessTypeFieldValue(), businessType, "Business Type not retained");
		Assert.assertEquals(oppPage.getNextStepFieldValue(), nextStep, "Next Step not retained");
		Assert.assertEquals(oppPage.getSalesStageFieldValue(), salesStage, "Sales Stage not retained");
		Assert.assertEquals(oppPage.getSelectedLead(), lead, "Lead not retained");

		// Enter valid amount now and submit again
		oppPage.enterAmount(data.get("Amount"));
		oppPage.clickCreateOpportunityInForm();

		// Final validation
		Assert.assertTrue(oppPage.isOpportunityCreatedWithName(oppName), "Opportunity not created after fixing error");
	}
//	=============================================================
//	17. Verify Large numeric value in Amount field
//	==============================================================

@Test(dataProvider = "tc4Data", dataProviderClass = Opportunity_DataProviderUtil.class)
public void verifyLargeNumericValueInAmountField(Map<String, String> data) throws FileNotFoundException, IOException, InterruptedException {
	CRM_OpportunitiesPage oppPage = hp.clickOpportunities();
	oppPage.clickCreateOpportunity();
	String oppName = data.get("Opportunity Name");
	String businessType = data.get("Business Type");
	String nextStep = data.get("Next Step");
	String salesStage = data.get("Sales Stage");
	String lead = data.get("Lead");
	String amount= "9999999999999999999999";
	
	
	// Fill all fields except "Amount"
	oppPage.enterOpportunityName(oppName);
	logger.info("Opportunity name is entered");

	oppPage.enterAmount(amount);
	logger.info("Amount is entered");

	oppPage.enterBusinessType(businessType);
	logger.info("Business type is entered");

	oppPage.enterNextStep(nextStep);
	logger.info("Next step is entered");

	oppPage.enterSalesStage(salesStage);
	logger.info("Sales stage is entered");

	String value = FileUtils.readOpportunitiesPropertiesFile("search.dropdown.value2");

	oppPage.selectLead(value, lead);
	logger.info("lead is selected");

	oppPage.clickCreateOpportunityInForm();
	logger.info("clicked create opportunity button");
	
	//verify 
	// Option 1: Check if error is displayed
    if (oppPage.getAmountErrorMessage() != null) {
        System.out.println("Validation error displayed for large amount as expected.");
        Assert.assertTrue(true);
    } else {
        // Option 2: Field accepted the value
        String actualAmount = oppPage.getAmountValue();
        Assert.assertEquals(actualAmount, amount, "Amount field should retain the entered large value.");
        System.out.println("Large amount accepted without validation error.");
    }


}
}