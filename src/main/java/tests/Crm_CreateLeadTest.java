package tests;

import java.io.IOException;
import java.time.Duration;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import listeners.ListenersCRM;
import pages.CRM_LeadsPage;

import utils.Lead_DataProviderUtil;


@Listeners(ListenersCRM.class)
public class Crm_CreateLeadTest extends BaseTest {
	

	public static Logger logger = LogManager.getLogger("Crm_CreateLeadTest");

	private String createdLeadName;
	//*******************************************************
	// 1.Create a lead with mandatory fields only
	//*******************************************************
	

	@Test(priority = 1,
			description = "Create a new lead with mandatory fields only", 
			enabled = true,
			dataProvider = "LeadMandatoryFeild", 
			dataProviderClass = Lead_DataProviderUtil.class)
	public void TC_1_createLeadWithMandatoryFields(Map<String, String> data) throws InterruptedException {
		{
			// getBrowser() method comes from Crm_BaseTest,

            WebDriver driver = getBrowser(); 

			CRM_LeadsPage lp = hp.clickLeads();
			logger.info("Leads page is opened");

			lp.clickCreateLeadButtonAndGetTitle();
			logger.info("Create Lead button is clicked");

			// Extract data from the map
			String leadName = data.get("leadName");
			createdLeadName = data.get("leadName"); // Store leadName in shared variable
			String company = data.get("company");
			String leadSource = data.get("leadSource");
			String industry = data.get("industry");
			String phone = data.get("phone");
			String leadStatus = data.get("leadStatus");
			String campaignID = data.get("campaignID");

			// Log the extracted values
			logger.info("Lead Name: " + leadName);
			logger.info("Company: " + company);
			logger.info("Lead Source: " + leadSource);
			logger.info("Industry: " + industry);
			logger.info("Phone: " + phone);
			logger.info("Lead Status: " + leadStatus);
			logger.info("Campaign ID: " + campaignID);

			// Fill in mandatory fields
			lp.enterLeadName(leadName);
			lp.enterCompany(company);
			lp.enterLeadSource(leadSource);
			lp.enterIndustry(industry);
			lp.enterPhone(phone);
			lp.enterLeadStatus(leadStatus);
			lp.lookupCampaign(campaignID);
			lp.SaveLeadButton();
			logger.info("Lead save button clicked");

			System.out.println("Lead created with mandatory fields: " + leadName + ", " + company + ", " + leadSource);

			// Search for the created lead to verify creation
			logger.info("Searching for created lead: " + leadName);

			String LeadCreated = lp.searchLead(leadName);
			Assert.assertTrue(LeadCreated.contains(leadName), "Lead creation failed for: " + leadName);
			logger.info("Lead found successfully: " + leadName);

		}
	}
//*************************************************************
//2. Create a lead with all optional and mandatory fields filled
//*************************************************************

	@Test(priority = 2,
			description = "Ensure lead creation with all optional and mandatory fields filled",
			enabled = true, 
			dataProvider = "createLeadwithOptionalFeilds", 
			dataProviderClass = Lead_DataProviderUtil.class)
	public void TC_2_CreateLeadWithAoptionAlFeilds(Map<String, String> data) throws InterruptedException {
		{

			CRM_LeadsPage lp = hp.clickLeads();
			logger.info("Leads page is opened");

			lp.clickCreateLeadButtonAndGetTitle();
			logger.info("Create Lead button is clicked");

			// Extract data from the map
			String leadName = data.get("leadName");
        //Extract leadName and store in shared variable

			String createdLeadName = data.get("leadName");
			String company = data.get("company");
			String leadSource = data.get("leadSource");
			String industry = data.get("industry");
			String phone = data.get("phone");
			String leadStatus = data.get("leadStatus");
			String campaignID = data.get("campaignID");
			String annualRevenue = data.get("annualRevenue");
			String noOfEmployees = data.get("noOfEmployees");
			String Phone = data.get("Phone");
			String email = data.get("Email");
			String secondaryEmail = data.get("Secondary Email");
			String assignedTo = data.get("Assigned To");
			String Address = data.get("Address");
			String city = data.get("City");
			String country = data.get("Country");
			String postalCode = data.get("PostalCode");
			String website = data.get("Website");
			String description = data.get("Description");

			// Log the extracted values
			logger.info("Lead Name: " + leadName);
			logger.info("Company: " + company);
			logger.info("Lead Source: " + leadSource);
			logger.info("Industry: " + industry);

			logger.info("Lead Status: " + leadStatus);
			logger.info("Campaign ID: " + campaignID);
			logger.info("Annual Revenue: " + annualRevenue);
			logger.info("No Of Employees: " + noOfEmployees);
			logger.info("Phone: " + Phone);
			logger.info("Email: " + email);
			logger.info("Secondary Email: " + secondaryEmail);
			logger.info("Assigned To: " + assignedTo);
			logger.info("Address: " + Address);

			logger.info("City: " + city);
			logger.info("Country: " + country);
			logger.info("Postal Code: " + postalCode);
			logger.info("Website: " + website);
			logger.info("Description: " + description);

			// Fill in mandatory and Optional fields
			lp.enterLeadName(leadName);
			lp.enterCompany(company);
			lp.enterLeadSource(leadSource);
			lp.enterIndustry(industry);

			lp.enterLeadStatus(leadStatus);
			lp.lookupCampaign(campaignID);
			lp.enterAnnualRevenue(annualRevenue);
			lp.enterNoOfEmployees(noOfEmployees);
			lp.enterPhone(Phone);
			lp.enterEmail(email);

			lp.enterSecondaryEmail(secondaryEmail);
			lp.enterAssignedTo(assignedTo);
			lp.enterAddress(Address);

			lp.enterCity(city);
			lp.enterCountry(country);
			lp.enterPostalCode(postalCode);
			lp.enterWebsite(website);
			lp.enterDescription(description);

			lp.SaveLeadButton();
			logger.info("Lead save button clicked");

			System.out.println("Lead created with mandatory fields: " + leadName + ", " + company + ", " + leadSource);

			// Search for the created lead to verify creation
			logger.info("Searching for created lead: " + leadName);

			String LeadCreated = lp.searchLead(leadName);
			Assert.assertTrue(LeadCreated.contains(leadName), "Lead creation failed for: " + leadName);
			logger.info("Lead found successfully: " + leadName);

		}
	}

//**********************************************************
//3. Verify that Lead ID is auto-generated after lead creation
//**********************************************************

	@Test(priority = 3, 
			enabled = true, 
			description = "Verify that Lead ID is auto-generated after lead creation")

	public void TC_3_leadIDAutogenerated() throws InterruptedException {
		WebDriver driver = getBrowser();
		CRM_LeadsPage lp = hp.clickLeads();
		String createdLeadName ="TestLead";
		logger.info("Looking for Lead ID of lead: " + createdLeadName);

		String leadId = lp.getGeneratedLeadID(createdLeadName);
		logger.info(" Retrieved Lead ID: " + leadId);

		Assert.assertNotNull(leadId, "Lead ID should not be null");

		logger.info(" Lead ID is auto-generated and unique for lead: " + createdLeadName + ", ID: " + leadId);
	}
//**************************************************
//4. Ensure Phone field accepts numeric input only
//**************************************************

	@DataProvider(name = "phoneValidation")
	public Object[][] phoneValidationInline() {
		return new Object[][] { 
			    { "1234567890", true }, // Valid: numeric
				{ "abc123a", true }, // Invalid: alphanumeric
				{ "123#$$%", false }, // Invalid: special chars

		};
	}

	@Test(priority = 4, 
			description = "Verify Phone field accepts numeric input only", 
			enabled = true, 
			dataProvider = "phoneValidation")

	public void TC_4_verifyPhoneField(String phoneInput, boolean shouldAccept) throws InterruptedException, IOException {
		
		WebDriver driver = getBrowser();
		CRM_LeadsPage lp = hp.clickLeads();
		lp.clickCreateLeadButtonAndGetTitle();
		
		System.out.println("Phone: " + phoneInput);
		String enteredPhone = lp.enterPhone(phoneInput);
		
		System.out.println("Entered Phone: " + enteredPhone);
		
		// Verify that the phone number is numeric and matches the entered value
		if (shouldAccept) {
			Assert.assertTrue(enteredPhone.matches("\\d+"),
					"Phone field should accept numeric input only. Entered: " + enteredPhone);
		} else {
			Assert.assertFalse(enteredPhone.matches("\\d+"),
					"Phone field should not accept non-numeric input. Entered: " + enteredPhone);
		}
	}
//**************************************************
// 5.Ensure Annual Revenue accepts numeric values
//**************************************************

	@DataProvider(name = "AnnualRevenueValidation")
	public Object[][] annualRevenueValidation() {
		return new Object[][] { 
			{ "1000000", true }, // Valid: numeric
			{ "!@#$$%", false }, // Invalid: special chars
		};
	}

	@Test(priority = 5, 
			description = "Ensure Annual Revenue accepts numeric values", 
			enabled = true, 
			dataProvider = "AnnualRevenueValidation")

	public void TC_5_verifyAnnualRevenueField(String RevenueInput, boolean shouldAccept)throws InterruptedException, IOException {
		
		WebDriver driver = getBrowser();
		CRM_LeadsPage lp = hp.clickLeads();
		lp.clickCreateLeadButtonAndGetTitle();

		String annualRevenue = lp.enterAnnualRevenue(RevenueInput);
		
		System.out.println("Entered Annual Revenue: " + annualRevenue);
		// Verify that the annual revenue is numeric and matches the entered value
		if (shouldAccept) {
			Assert.assertTrue(annualRevenue.matches("\\d+"),
					"Annual Revenue field should accept numeric input only. Entered: " + annualRevenue);
		} else {
			Assert.assertFalse(annualRevenue.matches("\\d+"),
					"Annual Revenue field should not accept non-numeric input. Entered: " + annualRevenue);
		}
	}

//**************************************************
//6. Ensure Lead Name is mandatory
//**************************************************

	@DataProvider(name = "LeadNameisMandatory")
	public Object[][] leadCreationData() {
		return new Object[][] {
				// leadName, company, leadSource, industry, phone, leadStatus, campaignID
				{ "", "Oil and Gas", "Phone", "Retail", "5551234567", "Open", "CAM00003" }, // Missing leadName (should
																							// fail)
		};
	}

	@Test(priority = 6, 
			description = "Ensure Lead name is mandatory", 
			enabled = true, 
			dataProvider = "LeadNameisMandatory")
	public void TC_6_EnsureLeadNameisMandatory(String leadName, String company, String leadSource, String industry,
			String phone, String leadStatus, String campaignID) throws InterruptedException {
		
		WebDriver driver = getBrowser();
		logger.info("Starting test for mandatory lead name validation");
		CRM_LeadsPage lp = hp.clickLeads();
		logger.info("Leads page is opened");
		lp.clickCreateLeadButtonAndGetTitle();
		logger.info("Create Lead button is clicked");

		lp.enterLeadName(leadName);
		logger.info("Entered Lead Name: " + leadName);
		lp.enterCompany(company);
		logger.info("Entered Company: " + company);
		lp.enterLeadSource(leadSource);
		logger.info("Entered Lead Source: " + leadSource);
		lp.enterIndustry(industry);
		logger.info("Entered Industry: " + industry);
		lp.enterPhone(phone);
		logger.info("Entered Phone: " + phone);
		lp.enterLeadStatus(leadStatus);
		logger.info("Entered Lead Status: " + leadStatus);
		lp.lookupCampaign(campaignID);
		logger.info("Looked up Campaign ID: " + campaignID);
		lp.SaveLeadButton();
		logger.info("Lead save button clicked");

		String required = lp.getAttributeLeadName("required");
		logger.info("Lead Name 'required' attribute: " + required);
		Assert.assertNotNull(required, "Field is not marked as required");
		Assert.assertTrue(required.equals("true"),
				"Lead Name field should be mandatory but is not marked as required.");

	}

//*****************************************************
//7. Ensure Company is mandatory
//*****************************************************
	@DataProvider(name = "CompanyIsMandatoryData")
	public Object[][] companyIsMandatoryData() {
		return new Object[][] {
				// leadName, company, leadSource, industry, phone, leadStatus, campaignID
				{ "Madhu Sahu", "", "Phone", "Retail", "5551234567", "Open", "CAM00003" } // Missing company (should
																							// fail)
		};
	}

	@Test(priority = 7,
			description = "Ensure Company is mandatory", 
			enabled = true, 
			dataProvider = "CompanyIsMandatoryData")
	public void TC_7_EnsureCompanyIsMandatory(String leadName, String company, String leadSource, String industry,
			String phone, String leadStatus, String campaignID) throws InterruptedException {
		WebDriver driver = getBrowser();
		logger.info("Starting test for mandatory company validation");
		CRM_LeadsPage lp = hp.clickLeads();
		logger.info("Leads page is opened");
		lp.clickCreateLeadButtonAndGetTitle();
		logger.info("Create Lead button is clicked");

		lp.enterLeadName(leadName);
		logger.info("Entered Lead Name: " + leadName);
		lp.enterCompany(company);
		logger.info("Entered Company: " + company);
		lp.enterLeadSource(leadSource);
		logger.info("Entered Lead Source: " + leadSource);
		lp.enterIndustry(industry);
		logger.info("Entered Industry: " + industry);
		lp.enterPhone(phone);
		logger.info("Entered Phone: " + phone);
		lp.enterLeadStatus(leadStatus);
		logger.info("Entered Lead Status: " + leadStatus);
		lp.lookupCampaign(campaignID);
		logger.info("Looked up Campaign ID: " + campaignID);
		lp.SaveLeadButton();
		logger.info("Lead save button clicked");

		// Here check the 'required' attribute or error message for company field
		// instead of leadName
		String required = lp.getAttributeCompany("required");
		logger.info("Company 'required' attribute: " + required);
		Assert.assertNotNull(required, "Company field is not marked as required");
		Assert.assertTrue(required.equals("true"), "Company field should be mandatory but is not marked as required.");
	}

//**************************************************
// 8.Ensure Lead Source is mandatory
//**************************************************
	@DataProvider(name = "LeadSourceMandatoryData")
	public Object[][] leadSourceMandatoryData() {
		return new Object[][] {
				// leadName, company, leadSource, industry, phone, leadStatus, campaignID
				{ "Madhu Sahu", "Oil and Gas", "", "Retail", "5551234567", "Open", "CAM00003" } // Blank Lead Source
																								// (should fail)
		};
	}

	@Test(priority = 8,
			description = "Ensure Lead Source is mandatory",
			enabled = true, 
			dataProvider = "LeadSourceMandatoryData")
	public void TC_8_EnsureLeadSourceIsMandatory(String leadName, String company, String leadSource, String industry,
			String phone, String leadStatus, String campaignID) throws InterruptedException {
		WebDriver driver = getBrowser();
		logger.info("Starting test for mandatory Lead Source validation");
		CRM_LeadsPage lp = hp.clickLeads();
		logger.info("Leads page is opened");
		lp.clickCreateLeadButtonAndGetTitle();
		logger.info("Create Lead button is clicked");

		lp.enterLeadName(leadName);
		logger.info("Entered Lead Name: " + leadName);
		lp.enterCompany(company);
		logger.info("Entered Company: " + company);
		lp.enterLeadSource(leadSource);
		logger.info("Entered Lead Source: '" + leadSource + "'");
		lp.enterIndustry(industry);
		logger.info("Entered Industry: " + industry);
		lp.enterPhone(phone);
		logger.info("Entered Phone: " + phone);
		lp.enterLeadStatus(leadStatus);
		logger.info("Entered Lead Status: " + leadStatus);
		lp.lookupCampaign(campaignID);
		logger.info("Looked up Campaign ID: " + campaignID);

		lp.SaveLeadButton();
		logger.info("Lead save button clicked");

		// You can check for the 'required' attribute or capture the validation error
		// message for Lead Source.
		String required = lp.getAttributeLeadSource("required");
		logger.info("Lead Source 'required' attribute: " + required);
		Assert.assertNotNull(required, "Lead Source field is not marked as required");
		Assert.assertTrue(required.equals("true"),
				"Lead Source field should be mandatory but is not marked as required.");
	}
//**************************************************
// 9.Ensure Industry is mandatory
//**************************************************

	@DataProvider(name = "IndustryMandatoryData")
	public Object[][] industryMandatoryData() {
		return new Object[][] {
				// leadName, company, leadSource, industry, phone, leadStatus, campaignID
				{ "Madhu Sahu", "Oil and Gas", "Phone", "", "5551234567", "Open", "CAM00003" } // Blank Industry (should
																								// fail)
		};
	}

	@Test(priority = 9, 
			description = "Ensure Industry is mandatory", 
			enabled = true, 
			dataProvider = "IndustryMandatoryData")
	public void TC_9_EnsureIndustryIsMandatory(String leadName, String company, String leadSource, String industry,
			String phone, String leadStatus, String campaignID) throws InterruptedException {
		WebDriver driver = getBrowser();
		logger.info("Starting test for mandatory Industry validation");
		CRM_LeadsPage lp = hp.clickLeads();
		logger.info("Leads page is opened");
		lp.clickCreateLeadButtonAndGetTitle();
		logger.info("Create Lead button is clicked");

		lp.enterLeadName(leadName);
		logger.info("Entered Lead Name: " + leadName);
		lp.enterCompany(company);
		logger.info("Entered Company: " + company);
		lp.enterLeadSource(leadSource);
		logger.info("Entered Lead Source: " + leadSource);
		lp.enterIndustry(industry);
		logger.info("Entered Industry: '" + industry + "'");
		lp.enterPhone(phone);
		logger.info("Entered Phone: " + phone);
		lp.enterLeadStatus(leadStatus);
		logger.info("Entered Lead Status: " + leadStatus);
		lp.lookupCampaign(campaignID);
		logger.info("Looked up Campaign ID: " + campaignID);

		lp.SaveLeadButton();
		logger.info("Lead save button clicked");

		// Check if Industry field has 'required' attribute or validation error
		String required = lp.getAttributeIndustry("required");
		logger.info("Industry 'required' attribute: " + required);
		Assert.assertNotNull(required, "Industry field is not marked as required");
		Assert.assertTrue(required.equals("true"), "Industry field should be mandatory but is not marked as required.");
	}

//**************************************************
//10. Ensure Phone is mandatory
//*************************************************     
	@DataProvider(name = "PhoneMandatoryData")
	public Object[][] phoneMandatoryData() {
		return new Object[][] {
				// leadName, company, leadSource, industry, phone, leadStatus, campaignID
				{ "Madhu Sahu", "Oil and Gas", "Phone", "Retail", "", "Open", "CAM00003" } // Blank Phone (should fail)
		};
	}

	@Test(priority = 10, 
			description = "Ensure Phone is mandatory",
			enabled = true, 
			dataProvider = "PhoneMandatoryData")
	public void TC_10_EnsurePhoneIsMandatory(String leadName, String company, String leadSource, String industry,
			String phone, String leadStatus, String campaignID) throws InterruptedException {
		WebDriver driver = getBrowser();
		logger.info("Starting test for mandatory Phone validation");
		CRM_LeadsPage lp = hp.clickLeads();
		logger.info("Leads page is opened");
		lp.clickCreateLeadButtonAndGetTitle();
		logger.info("Create Lead button is clicked");

		lp.enterLeadName(leadName);
		logger.info("Entered Lead Name: " + leadName);
		lp.enterCompany(company);
		logger.info("Entered Company: " + company);
		lp.enterLeadSource(leadSource);
		logger.info("Entered Lead Source: " + leadSource);
		lp.enterIndustry(industry);
		logger.info("Entered Industry: " + industry);
		lp.enterPhone(phone);
		logger.info("Entered Phone: '" + phone + "'");
		lp.enterLeadStatus(leadStatus);
		logger.info("Entered Lead Status: " + leadStatus);
		lp.lookupCampaign(campaignID);
		logger.info("Looked up Campaign ID: " + campaignID);

		lp.SaveLeadButton();
		logger.info("Lead save button clicked");

		// Check if Phone field has 'required' attribute or validation error
		String required = lp.getAttributePhone("required");
		logger.info("Phone 'required' attribute: " + required);
		Assert.assertNotNull(required, "Phone field is not marked as required");
		Assert.assertTrue(required.equals("true"), "Phone field should be mandatory but is not marked as required.");

	}

//**************************************************
//11. Ensure Lead Status is mandatory
//**************************************************
	@DataProvider(name = "LeadStatusMandatoryData")
	public Object[][] leadStatusMandatoryData() {
		return new Object[][] {
				// leadName, company, leadSource, industry, phone, leadStatus, campaignID
				{ "Madhu Sahu", "Oil and Gas", "Phone", "Retail", "5551234567", "", "CAM00003" } // Blank Lead Status
																									// (should fail)
		};
	}

	@Test(priority = 11,
			description = "Ensure Lead Status is mandatory", 
			enabled = true, 
			dataProvider = "LeadStatusMandatoryData")
	public void TC_11_EnsureLeadStatusIsMandatory(String leadName, String company, String leadSource, String industry,
			String phone, String leadStatus, String campaignID) throws InterruptedException {
		WebDriver driver = getBrowser();
		logger.info("Starting test for mandatory Lead Status validation");
		CRM_LeadsPage lp = hp.clickLeads();
		logger.info("Leads page is opened");
		lp.clickCreateLeadButtonAndGetTitle();
		logger.info("Create Lead button is clicked");

		lp.enterLeadName(leadName);
		logger.info("Entered Lead Name: " + leadName);
		lp.enterCompany(company);
		logger.info("Entered Company: " + company);
		lp.enterLeadSource(leadSource);
		logger.info("Entered Lead Source: " + leadSource);
		lp.enterIndustry(industry);
		logger.info("Entered Industry: " + industry);
		lp.enterPhone(phone);
		logger.info("Entered Phone: " + phone);
		lp.enterLeadStatus(leadStatus);
		logger.info("Entered Lead Status: '" + leadStatus + "'");
		lp.lookupCampaign(campaignID);
		logger.info("Looked up Campaign ID: " + campaignID);

		lp.SaveLeadButton();
		logger.info("Lead save button clicked");

		// Check if Lead Status field has 'required' attribute or validation error
		String required = lp.getAttributeLeadStatus("required");
		logger.info("Lead Status 'required' attribute: " + required);
		Assert.assertNotNull(required, "Lead Status field is not marked as required");
		Assert.assertTrue(required.equals("true"),
				"Lead Status field should be mandatory but is not marked as required.");

	}
//**************************************************
// 12.Ensure Campaign is mandatory
//**************************************************

	@DataProvider(name = "CampaignMandatoryData")
	public Object[][] campaignMandatoryData() {
		return new Object[][] {
				// leadName, company, leadSource, industry, phone, leadStatus, campaignID
				{ "Madhu Sahu", "Oil and Gas", "Phone", "Retail", "5551234567", "Open", "" } // Blank Campaign ID
																								// (should fail)
		};
	}

	@Test(priority = 12, 
			description = "Ensure Campaign is mandatory", 
			enabled = true,
			dataProvider = "CampaignMandatoryData")
	public void TC_12_EnsureCampaignIsMandatory(String leadName, String company, String leadSource, String industry,
			String phone, String leadStatus, String campaignID) throws InterruptedException {
		WebDriver driver = getBrowser();
		logger.info("Starting test for mandatory Campaign validation");
		CRM_LeadsPage lp = hp.clickLeads();
		logger.info("Leads page is opened");
		lp.clickCreateLeadButtonAndGetTitle();
		logger.info("Create Lead button is clicked");

		lp.enterLeadName(leadName);
		logger.info("Entered Lead Name: " + leadName);
		lp.enterCompany(company);
		logger.info("Entered Company: " + company);
		lp.enterLeadSource(leadSource);
		logger.info("Entered Lead Source: " + leadSource);
		lp.enterIndustry(industry);
		logger.info("Entered Industry: " + industry);
		lp.enterPhone(phone);
		logger.info("Entered Phone: " + phone);
		lp.enterLeadStatus(leadStatus);
		logger.info("Entered Lead Status: " + leadStatus);

		lp.SaveLeadButton();
		logger.info("Lead save button clicked");

		// Wait for toast message to appear
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@role='alert' and contains(text(),'please select a campaign')]")));

		// Validate message content
		String actualMessage = toast.getText().trim();
		Assert.assertEquals(actualMessage, "please select a campaign before submitting",
				"Expected Campaign validation message not shown");
		logger.info("Campaign validation message displayed: " + actualMessage);
		// Wait for the toast to disappear
		wait.until(ExpectedConditions.invisibilityOf(toast));
	}

// **************************************************
//13. Ensure Email field accepts valid email formats
	// **************************************************

	@DataProvider(name = "EmailValidation")
	public Object[][] emailValidation() {
		return new Object[][] { 
			     { "test@example.com", true }, 
			     { "user.name@domain.co", true },
				 { "user123@gmail", false },
				 { "@gmail.com", false }, 
				 { "user@.com", false },
				 { "user#mail.com", false },
				 { "123@gmail.com", true },
				 {"madhu@gmailcom",false }
	};
	}

	@Test(priority = 13, 
			description = "Ensure Email field accepts only valid email formats", 
			enabled = true, 
			dataProvider = "EmailValidation")
	public void TC_13_verifyEmailFieldValidation(String emailInput, boolean shouldAccept)
			throws InterruptedException, IOException {
		WebDriver driver = getBrowser();
		CRM_LeadsPage lp = hp.clickLeads();
		lp.clickCreateLeadButtonAndGetTitle();

		String enteredEmail = lp.enterEmail(emailInput);
		System.out.println("Entered Email: " + enteredEmail);

		boolean isValidEmailFormat = enteredEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
		logger.info("Email format validation for '" + emailInput + "': " + isValidEmailFormat);

		if (shouldAccept) {
			Assert.assertTrue(isValidEmailFormat,
					"Expected valid email format to be accepted, but it was rejected. Input: " + emailInput);
		} else {
			Assert.assertFalse(isValidEmailFormat,
					"Expected invalid email format to be rejected, but it was accepted. Input: " + emailInput);
			String errorMsg = lp.getEmailErrorMessage();
			logger.info("Email validation error message: " + errorMsg);
			Assert.assertTrue(errorMsg.toLowerCase().contains("valid email"),
					"Expected email validation error message not found. Actual: " + errorMsg);
		}
	}

// ********************************************************
//14. Ensure Secondary Email field accepts valid email formats
// ********************************************************

	@DataProvider(name = "SecondaryEmailValidation")
	public Object[][] SecondaryemailValidation() {
		return new Object[][] { 
			     { "test@example.com", true }, 
			     { "user.name@domain.co", true },
				 { "user123@gmail", false },
				 { "@gmail.com", false },
				 { "user@.com", false },
				 { "user#mail.com", false }, 
				  { "123@gmail.com", true },
				  {"madhu@gmailcom",false }
				  };
	}

	@Test(priority = 14,
			description = "Ensure Email field accepts only valid email formats",
			enabled = true, 
			dataProvider = "EmailValidation")
	
	public void TC_14_verifySecondaryEmailFieldValidation(String emailInput, boolean shouldAccept)throws InterruptedException, IOException {
		WebDriver driver = getBrowser();
		CRM_LeadsPage lp = hp.clickLeads();
		lp.clickCreateLeadButtonAndGetTitle();

		String enteredEmail = lp.enterSecondayEmail(emailInput);
		System.out.println("Entered Email: " + enteredEmail);

		boolean isValidEmailFormat = enteredEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
		logger.info("Email format validation for '" + emailInput + "': " + isValidEmailFormat);

		if (shouldAccept) {
			Assert.assertTrue(isValidEmailFormat,
					"Expected valid email format to be accepted, but it was rejected. Input: " + emailInput);
		} else {
			Assert.assertFalse(isValidEmailFormat,
					"Expected invalid email format to be rejected, but it was accepted. Input: " + emailInput);
			String errorMsg = lp.getEmailErrorMessage();
			logger.info("Email validation error message: " + errorMsg);
			Assert.assertTrue(errorMsg.toLowerCase().contains("valid email"),
					"Expected email validation error message not found. Actual: " + errorMsg);
		}
	}

// *****************************************************
//15. Verify that Annual Revenue field defaults to 0
// *****************************************************

	@Test(priority = 15,
			enabled = true, 
			description = "Check if Annual Revenue field defaults to 0")
	public void TC_15_verifyAnnualRevenueDefaultValue() throws InterruptedException, IOException {
		WebDriver driver = getBrowser();
		CRM_LeadsPage lp = hp.clickLeads();
		lp.clickCreateLeadButtonAndGetTitle();

		String revenueValue = lp.getAnnualRevenue();
		System.out.println("Default Annual Revenue: " + revenueValue);
		Assert.assertEquals(revenueValue, "0", "Annual Revenue field should default to 0, but found: " + revenueValue);
	}

// *****************************************************
// 16.Verify that Number of Employees field defaults to 1
// *****************************************************

	@Test(priority = 16, 
			enabled = true, 
			description = "Check if Number of Employees field defaults to 1")
	public void TC_16_verifyNumberOfEmployeesDefaultValue() throws InterruptedException, IOException {
		WebDriver driver = getBrowser();
		CRM_LeadsPage lp = hp.clickLeads();
		lp.clickCreateLeadButtonAndGetTitle();

		String numEmployees = lp.getNumberOfEmployeesValue();
		System.out.println("Default Number of Employees: " + numEmployees);
		Assert.assertEquals(numEmployees, "1",
				"Number of Employees field should default to 1, but found: " + numEmployees);
	}

// *****************************************************
// 17.Verify that Rating field defaults to 0
// *****************************************************

	@Test(priority = 17, 
			enabled = true,
			description = "Check if Rating field defaults to 0")
	public void TC_17_verifyRatingFieldDefaultValue() throws InterruptedException, IOException {
		WebDriver driver = getBrowser();
		CRM_LeadsPage lp = hp.clickLeads();
		lp.clickCreateLeadButtonAndGetTitle();

		String rating = lp.getRatingDefaultValue();
		System.out.println("Default Rating: " + rating);
		Assert.assertEquals(rating, "0", "Rating field should default to 0, but found: " + rating);
	}

// ***************************************************************
//18. Verify that Number of Employees field accepts only numeric input
// ****************************************************************

	@DataProvider(name = "NumberOfEmployeesValidation")
	public Object[][] numberOfEmployeesValidation() {
		return new Object[][] { 
			{ "10", true }, 
			{ "0", true },
			{ "-5", true }, 
			{ "abc", false }, 
			{ "12abc", true },
			{ "!@#", false },
			{ "1234567890", true }, 
			{ "1.5", false } };
	}

	@Test(priority = 18, 
			description = "Ensure Number of Employees accepts only numeric input",
			enabled = true, 
			dataProvider = "NumberOfEmployeesValidation")
	public void TC_18_verifyNumberOfEmployeesFieldValidation(String input, boolean shouldAccept)throws InterruptedException, IOException {
		WebDriver driver = getBrowser();
		CRM_LeadsPage lp = hp.clickLeads();
		lp.clickCreateLeadButtonAndGetTitle();

		String enteredValue = lp.enterNoOfEmployees(input);
		boolean isNumeric = enteredValue.matches("-?\\d+");
		logger.info("Entered Number of Employees: " + enteredValue + " | isNumeric: " + isNumeric);

		if (shouldAccept) {
			Assert.assertTrue(isNumeric, "Expected numeric input to be accepted, but it was rejected. Input: " + input);
		} else {
			Assert.assertFalse(isNumeric,
					"Expected non-numeric input to be rejected, but it was accepted. Input: " + input);
		}
	}
// *****************************************************
// 19.Verify that Rating field accepts only numeric input
// *****************************************************

	@DataProvider(name = "RatingFieldValidation")
	public Object[][] ratingFieldValidation() {
		return new Object[][] { 
			    { "5", true }, // Valid integer
				{ "0", true }, // Valid zero
				{ "-3", false }, // Invalid: negative number
				{ "4.5", false }, // Invalid: decimal (if only integers allowed)
				{ "abc", false }, // Invalid: alphabets
				{ "123abc", true }, // Valid alphanumeric as it is removing the alphabets
				{ "12.34", false }, // Invalid: decimal number
				{ "1000", true }, // Valid large number
				{ "1,000", false }, // Invalid: comma in number
				{ "12-34", false }, // Invalid: hyphen in number
				{ "!@#", false }, // Invalid: special characters
				{ "999999999", true }, // Valid large number (if allowed)
				{ "", false } // Invalid: empty input
		};
	}

	@Test(priority = 19, 
			dataProvider = "RatingFieldValidation",
			enabled = true, 
			description = "Validate Rating field allows only numeric input")

	public void TC_19_testRatingFieldInput(String input, boolean shouldBeAccepted) throws InterruptedException {
		WebDriver driver = getBrowser();
		CRM_LeadsPage lp = hp.clickLeads();
		lp.clickCreateLeadButtonAndGetTitle();

		String actualValue = lp.RatingFieldInput(input);
		boolean isValid = actualValue.matches("^\\d+$"); // accepts only positive integers

		if (shouldBeAccepted) {
			Assert.assertTrue(isValid, "Expected valid numeric input, but got: " + actualValue);
		} else {
			Assert.assertFalse(isValid, "Expected rejection of invalid input: " + input);
		}
	}
	// *****************************************************
	// 20.Verify that Lead ID field is read-only
	// *****************************************************

	@Test(priority = 20, 
			enabled = true, 
			description = "Ensure Lead ID field is read-only")
	 
	public void TC_20_verifyLeadIDIsReadOnly() throws InterruptedException {
		WebDriver driver = getBrowser();
		CRM_LeadsPage lp = hp.clickLeads();
		lp.clickCreateLeadButtonAndGetTitle();

		// Check for 'readonly' attribute
		String isReadOnly = lp.getLeadIdAttribute(createdLeadName);
		logger.info("Lead ID 'readonly' attribute: " + isReadOnly);
		// Assert that the Lead ID field is read-only
		Assert.assertNotNull(isReadOnly, "Lead ID field is not marked as read-only");

	}
//************************************************************************
// 21. Ensure lead creation with all optional and mandatory fields filled
//************************************************************************

	@Test(priority = 21,
			description = "Verify Lead can be created without Email field",
			enabled = true, 
			dataProvider = "createLeadwithAllOptionalFeildsExceptEmail", 
			dataProviderClass = Lead_DataProviderUtil.class)
	public void TC_21_VerifyLeadCreationWithoutEmail(Map<String, String> data) throws InterruptedException {
		{

			CRM_LeadsPage lp = hp.clickLeads();
			logger.info("Leads page is opened");

			lp.clickCreateLeadButtonAndGetTitle();
			logger.info("Create Lead button is clicked");

			// Extract data from the map
			String leadName = data.get("leadName");
        //Extract leadName and store in shared variable

			String createdLeadName = data.get("leadName");
			String company = data.get("company");
			String leadSource = data.get("leadSource");
			String industry = data.get("industry");
			String phone = data.get("phone");
			String leadStatus = data.get("leadStatus");
			String campaignID = data.get("campaignID");
			String annualRevenue = data.get("annualRevenue");
			String noOfEmployees = data.get("noOfEmployees");
			String Phone = data.get("Phone");
		//	String email = data.get("Email");---No Email Data Entered
			String secondaryEmail = data.get("Secondary Email");
			String assignedTo = data.get("Assigned To");
			String Address = data.get("Address");
			String city = data.get("City");
			String country = data.get("Country");
			String postalCode = data.get("PostalCode");
			String website = data.get("Website");
			String description = data.get("Description");

			// Log the extracted values
			logger.info("Lead Name: " + leadName);
			logger.info("Company: " + company);
			logger.info("Lead Source: " + leadSource);
			logger.info("Industry: " + industry);

			logger.info("Lead Status: " + leadStatus);
			logger.info("Campaign ID: " + campaignID);
			logger.info("Annual Revenue: " + annualRevenue);
			logger.info("No Of Employees: " + noOfEmployees);
			logger.info("Phone: " + Phone);
			logger.info("Email is not entered: ");
			logger.info("Secondary Email: " + secondaryEmail);
			logger.info("Assigned To: " + assignedTo);
			logger.info("Address: " + Address);
            //logger.info("Email: " + Email);
			logger.info("City: " + city);
			logger.info("Country: " + country);
			logger.info("Postal Code: " + postalCode);
			logger.info("Website: " + website);
			logger.info("Description: " + description);

			// Fill in mandatory and Optional fields
			lp.enterLeadName(leadName);
			lp.enterCompany(company);
			lp.enterLeadSource(leadSource);
			lp.enterIndustry(industry);

			lp.enterLeadStatus(leadStatus);
			lp.lookupCampaign(campaignID);
			lp.enterAnnualRevenue(annualRevenue);
			lp.enterNoOfEmployees(noOfEmployees);
			lp.enterPhone(Phone);
		//	lp.enterEmail(email);

			lp.enterSecondaryEmail(secondaryEmail);
			lp.enterAssignedTo(assignedTo);
			lp.enterAddress(Address);

			lp.enterCity(city);
			lp.enterCountry(country);
			lp.enterPostalCode(postalCode);
			lp.enterWebsite(website);
			lp.enterDescription(description);

			lp.SaveLeadButton();
			logger.info("Lead save button clicked");

			System.out.println("Lead created with all feilds except Email:" + leadName + ", " + company + ", " + leadSource);

			// Search for the created lead to verify creation
			logger.info("Searching for created lead: " + leadName);

			String LeadCreated = lp.searchLead(leadName);
			Assert.assertTrue(LeadCreated.contains(leadName), "Lead creation failed for: " + leadName);
			logger.info("Lead found successfully: " + leadName);

		}
	}
	//************************************************************************
	//22. Verify Lead can be created without Secondary Email field
	//************************************************************************
	@Test(priority = 22,
			description = "Verify Lead can be created without Seondary Email field",
			enabled = true, 
			dataProvider = "createLeadwithAllOptionalFeildsExceptSecondaryEmail", 
			dataProviderClass = Lead_DataProviderUtil.class)
	public void TC_22_VerifyLeadCreationWithoutSecEmail(Map<String, String> data) throws InterruptedException {
		{

			CRM_LeadsPage lp = hp.clickLeads();
			logger.info("Leads page is opened");

			lp.clickCreateLeadButtonAndGetTitle();
			logger.info("Create Lead button is clicked");

			// Extract data from the map
			String leadName = data.get("leadName");
        //Extract leadName and store in shared variable

			String createdLeadName = data.get("leadName");
			String company = data.get("company");
			String leadSource = data.get("leadSource");
			String industry = data.get("industry");
			String phone = data.get("phone");
			String leadStatus = data.get("leadStatus");
			String campaignID = data.get("campaignID");
			String annualRevenue = data.get("annualRevenue");
			String noOfEmployees = data.get("noOfEmployees");
			String Phone = data.get("Phone");
			String email = data.get("Email");
		//	String secondaryEmail = data.get("Secondary Email");
			String assignedTo = data.get("Assigned To");
			String Address = data.get("Address");
			String city = data.get("City");
			String country = data.get("Country");
			String postalCode = data.get("PostalCode");
			String website = data.get("Website");
			String description = data.get("Description");

			// Log the extracted values
			logger.info("Lead Name: " + leadName);
			logger.info("Company: " + company);
			logger.info("Lead Source: " + leadSource);
			logger.info("Industry: " + industry);

			logger.info("Lead Status: " + leadStatus);
			logger.info("Campaign ID: " + campaignID);
			logger.info("Annual Revenue: " + annualRevenue);
			logger.info("No Of Employees: " + noOfEmployees);
			logger.info("Phone: " + Phone);
			logger.info("Email is not entered: ");
		//	logger.info("Secondary Email: " + secondaryEmail);
			logger.info("Assigned To: " + assignedTo);
			logger.info("Address: " + Address);
            logger.info("Email: " + email);
			logger.info("City: " + city);
			logger.info("Country: " + country);
			logger.info("Postal Code: " + postalCode);
			logger.info("Website: " + website);
			logger.info("Description: " + description);

			// Fill in mandatory and Optional fields
			lp.enterLeadName(leadName);
			lp.enterCompany(company);
			lp.enterLeadSource(leadSource);
			lp.enterIndustry(industry);

			lp.enterLeadStatus(leadStatus);
			lp.lookupCampaign(campaignID);
			lp.enterAnnualRevenue(annualRevenue);
			lp.enterNoOfEmployees(noOfEmployees);
			lp.enterPhone(Phone);
		    lp.enterEmail(email);

		//	lp.enterSecondaryEmail(secondaryEmail);
			lp.enterAssignedTo(assignedTo);
			lp.enterAddress(Address);

			lp.enterCity(city);
			lp.enterCountry(country);
			lp.enterPostalCode(postalCode);
			lp.enterWebsite(website);
			lp.enterDescription(description);

			lp.SaveLeadButton();
			logger.info("Lead save button clicked");

			System.out.println("Lead created with all feilds except Secondary Email:" + leadName + ", " + company + ", " + leadSource);

			// Search for the created lead to verify creation
			logger.info("Searching for created lead: " + leadName);

			String LeadCreated = lp.searchLead(leadName);
			Assert.assertTrue(LeadCreated.contains(leadName), "Lead creation failed for: " + leadName);
			logger.info("Lead found successfully: " + leadName);

		}
	}
	//************************************************************************
	//23. Verify Lead can be created without Address field
	//************************************************************************
	@Test(priority = 23,
			description = "Verify Lead can be created without Address field",
			enabled = true, 
			dataProvider = "createLeadwithNoAddressFeilds", 
			dataProviderClass = Lead_DataProviderUtil.class)
	public void TC_23_VerifyLeadWithoutAddressFeilds(Map<String, String> data) throws InterruptedException {
		{

			CRM_LeadsPage lp = hp.clickLeads();
			logger.info("Leads page is opened");

			lp.clickCreateLeadButtonAndGetTitle();
			logger.info("Create Lead button is clicked");

			// Extract data from the map
			String leadName = data.get("leadName");
        //Extract leadName and store in shared variable

			String createdLeadName = data.get("leadName");
			String company = data.get("company");
			String leadSource = data.get("leadSource");
			String industry = data.get("industry");
			String phone = data.get("phone");
			String leadStatus = data.get("leadStatus");
			String campaignID = data.get("campaignID");
			String annualRevenue = data.get("annualRevenue");
			String noOfEmployees = data.get("noOfEmployees");
			String Phone = data.get("Phone");
			String email = data.get("Email");
		    String secondaryEmail = data.get("Secondary Email");
			String assignedTo = data.get("Assigned To");
			//String Address = data.get("Address");
			//String city = data.get("City");
			//String country = data.get("Country");
		//	String postalCode = data.get("PostalCode");
			String website = data.get("Website");
			String description = data.get("Description");

			// Log the extracted values
			logger.info("Lead Name: " + leadName);
			logger.info("Company: " + company);
			logger.info("Lead Source: " + leadSource);
			logger.info("Industry: " + industry);

			logger.info("Lead Status: " + leadStatus);
			logger.info("Campaign ID: " + campaignID);
			logger.info("Annual Revenue: " + annualRevenue);
			logger.info("No Of Employees: " + noOfEmployees);
			logger.info("Phone: " + Phone);
			logger.info("Email is not entered: ");
			logger.info("Secondary Email: " + secondaryEmail);
			logger.info("Assigned To: " + assignedTo);
			//logger.info("Address: " + Address);
            logger.info("Email: " + email);
		//	logger.info("City: " + city);
			//logger.info("Country: " + country);
		//	logger.info("Postal Code: " + postalCode);
			logger.info("Website: " + website);
			logger.info("Description: " + description);

			// Fill in mandatory and Optional fields
			lp.enterLeadName(leadName);
			lp.enterCompany(company);
			lp.enterLeadSource(leadSource);
			lp.enterIndustry(industry);

			lp.enterLeadStatus(leadStatus);
			lp.lookupCampaign(campaignID);
			lp.enterAnnualRevenue(annualRevenue);
			lp.enterNoOfEmployees(noOfEmployees);
			lp.enterPhone(Phone);
		    lp.enterEmail(email);

		    lp.enterSecondaryEmail(secondaryEmail);
			lp.enterAssignedTo(assignedTo);
		//	lp.enterAddress(Address);

		//	lp.enterCity(city);
		//	lp.enterCountry(country);
		//	lp.enterPostalCode(postalCode);
			lp.enterWebsite(website);
			lp.enterDescription(description);

			lp.SaveLeadButton();
			logger.info("Lead save button clicked");

			System.out.println("Lead created with all feilds except Address Feilds" + leadName + ", " + company + ", " + leadSource);

			// Search for the created lead to verify creation
			logger.info("Searching for created lead: " + leadName);

			String LeadCreated = lp.searchLead(leadName);
			Assert.assertTrue(LeadCreated.contains(leadName), "Lead creation failed for: " + leadName);
			logger.info("Lead found successfully: " + leadName);

		}
	}
	//************************************************************************
	//24. Verify Lead can be created without Website field
	
	@Test(priority = 24,
			description = "Verify Lead can be created without Website field",
			enabled = true, 
			dataProvider = "createLeadwithNoWebSiteFeild", 
			dataProviderClass = Lead_DataProviderUtil.class)
	public void TC_24_VerifyLeadWithoutWebsiteFeilds(Map<String, String> data) throws InterruptedException {
		{

			CRM_LeadsPage lp = hp.clickLeads();
			logger.info("Leads page is opened");

			lp.clickCreateLeadButtonAndGetTitle();
			logger.info("Create Lead button is clicked");

			// Extract data from the map
			String leadName = data.get("leadName");
        //Extract leadName and store in shared variable

			String createdLeadName = data.get("leadName");
			String company = data.get("company");
			String leadSource = data.get("leadSource");
			String industry = data.get("industry");
			String phone = data.get("phone");
			String leadStatus = data.get("leadStatus");
			String campaignID = data.get("campaignID");
			String annualRevenue = data.get("annualRevenue");
			String noOfEmployees = data.get("noOfEmployees");
			String Phone = data.get("Phone");
			String email = data.get("Email");
		    String secondaryEmail = data.get("Secondary Email");
			String assignedTo = data.get("Assigned To");
			String Address = data.get("Address");
			String city = data.get("City");
			String country = data.get("Country");
		    String postalCode = data.get("PostalCode");
			//String website = data.get("Website");
			String description = data.get("Description");

			// Log the extracted values
			logger.info("Lead Name: " + leadName);
			logger.info("Company: " + company);
			logger.info("Lead Source: " + leadSource);
			logger.info("Industry: " + industry);

			logger.info("Lead Status: " + leadStatus);
			logger.info("Campaign ID: " + campaignID);
			logger.info("Annual Revenue: " + annualRevenue);
			logger.info("No Of Employees: " + noOfEmployees);
			logger.info("Phone: " + Phone);
			logger.info("Email is not entered: ");
			logger.info("Secondary Email: " + secondaryEmail);
			logger.info("Assigned To: " + assignedTo);
			logger.info("Address: " + Address);
            logger.info("Email: " + email);
		    logger.info("City: " + city);
			logger.info("Country: " + country);
		    logger.info("Postal Code: " + postalCode);
		//	logger.info("Website: " + website);
			logger.info("Description: " + description);

			// Fill in mandatory and Optional fields
			lp.enterLeadName(leadName);
			lp.enterCompany(company);
			lp.enterLeadSource(leadSource);
			lp.enterIndustry(industry);

			lp.enterLeadStatus(leadStatus);
			lp.lookupCampaign(campaignID);
			lp.enterAnnualRevenue(annualRevenue);
			lp.enterNoOfEmployees(noOfEmployees);
			lp.enterPhone(Phone);
		    lp.enterEmail(email);

		    lp.enterSecondaryEmail(secondaryEmail);
			lp.enterAssignedTo(assignedTo);
		    lp.enterAddress(Address);

		    lp.enterCity(city);
		    lp.enterCountry(country);
		    lp.enterPostalCode(postalCode);
		//	lp.enterWebsite(website);
			lp.enterDescription(description);

			lp.SaveLeadButton();
			logger.info("Lead save button clicked");

			System.out.println("Lead created with all feilds except Website Feilds" + leadName + ", " + company + ", " + leadSource);

			// Search for the created lead to verify creation
			logger.info("Searching for created lead: " + leadName);

			String LeadCreated = lp.searchLead(leadName);
			Assert.assertTrue(LeadCreated.contains(leadName), "Lead creation failed for: " + leadName);
			logger.info("Lead found successfully: " + leadName);

		}
	}
	//************************************************************************
	//25. Verify Lead can be created without Description field
	//************************************************************************
	@Test(priority = 25,
			description = "Verify Lead can be created without Website field",
			enabled = true, 
			dataProvider = "createLeadwithNoDescFeild", 
			dataProviderClass = Lead_DataProviderUtil.class)
	public void TC_25_VerifyLeadWithoutDescFeild(Map<String, String> data) throws InterruptedException {
		{

			CRM_LeadsPage lp = hp.clickLeads();
			logger.info("Leads page is opened");

			lp.clickCreateLeadButtonAndGetTitle();
			logger.info("Create Lead button is clicked");

			// Extract data from the map
			String leadName = data.get("leadName");
        //Extract leadName and store in shared variable

			String createdLeadName = data.get("leadName");
			String company = data.get("company");
			String leadSource = data.get("leadSource");
			String industry = data.get("industry");
			String phone = data.get("phone");
			String leadStatus = data.get("leadStatus");
			String campaignID = data.get("campaignID");
			String annualRevenue = data.get("annualRevenue");
			String noOfEmployees = data.get("noOfEmployees");
			String Phone = data.get("Phone");
			String email = data.get("Email");
		    String secondaryEmail = data.get("Secondary Email");
			String assignedTo = data.get("Assigned To");
			String Address = data.get("Address");
			String city = data.get("City");
			String country = data.get("Country");
		    String postalCode = data.get("PostalCode");
			String website = data.get("Website");
			//String description = data.get("Description");

			// Log the extracted values
			logger.info("Lead Name: " + leadName);
			logger.info("Company: " + company);
			logger.info("Lead Source: " + leadSource);
			logger.info("Industry: " + industry);

			logger.info("Lead Status: " + leadStatus);
			logger.info("Campaign ID: " + campaignID);
			logger.info("Annual Revenue: " + annualRevenue);
			logger.info("No Of Employees: " + noOfEmployees);
			logger.info("Phone: " + Phone);
			logger.info("Email is not entered: ");
			logger.info("Secondary Email: " + secondaryEmail);
			logger.info("Assigned To: " + assignedTo);
			logger.info("Address: " + Address);
            logger.info("Email: " + email);
		    logger.info("City: " + city);
			logger.info("Country: " + country);
		    logger.info("Postal Code: " + postalCode);
			logger.info("Website: " + website);
			//logger.info("Description: " + description);

			// Fill in mandatory and Optional fields
			lp.enterLeadName(leadName);
			lp.enterCompany(company);
			lp.enterLeadSource(leadSource);
			lp.enterIndustry(industry);

			lp.enterLeadStatus(leadStatus);
			lp.lookupCampaign(campaignID);
			lp.enterAnnualRevenue(annualRevenue);
			lp.enterNoOfEmployees(noOfEmployees);
			lp.enterPhone(Phone);
		    lp.enterEmail(email);

		    lp.enterSecondaryEmail(secondaryEmail);
			lp.enterAssignedTo(assignedTo);
		    lp.enterAddress(Address);

		    lp.enterCity(city);
		    lp.enterCountry(country);
		    lp.enterPostalCode(postalCode);
		    lp.enterWebsite(website);
		//	lp.enterDescription(description);

			lp.SaveLeadButton();
			logger.info("Lead save button clicked");
			

			System.out.println("Lead created with all feilds except Desc Feild" + leadName + ", " + company + ", " + leadSource);
            createdLeadName = leadName;
			// Search for the created lead to verify creation
			logger.info("Searching for created lead: " + createdLeadName);

			String LeadCreated = lp.searchLead(createdLeadName);
			Assert.assertTrue(LeadCreated.contains(createdLeadName), "Lead creation failed for: " + leadName);
			logger.info("Lead found successfully: " + createdLeadName);

		}
	}

	
}