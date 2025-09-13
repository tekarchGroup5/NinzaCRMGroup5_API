package api_tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import api_POJOS.CreateContactClassic_POJO;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import randomDataGenerator.CreateContact;

public class Contact_APITest extends api_BaseTest {
	  Faker faker = new Faker(); // Make sure you have the dependency

	// Create Contact - Mandatory Fields
	@Test(priority = 1, enabled = true) // Test is working
	public void createContactWithMandatoryFieldsTC_008() throws IOException {
		test.set(extent.createTest("Verify Contact Creation with Mandatory Fields"));

		String campaignId = prop.getProperty("campaignId");
		// Generate random contact data instead of loading from JSON
		CreateContactClassic_POJO contactPayload = CreateContact.generateMandtoryContact();
		// POST request and capture response in createdContact
		CreateContactClassic_POJO createdContactPayLoad = given().header("accept", "application/json")
				.queryParam("campaignId", campaignId).body(contactPayload, ObjectMapperType.JACKSON_2).when()
				.post("/contact").then().log().all().statusCode(201).extract()
				.as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);

		// comparing contactPayload is the request object and createdContactPayLoad is
		// the response object
		assert createdContactPayLoad.getContactName().equals(contactPayload.getContactName());
		assert createdContactPayLoad.getTitle().equals(contactPayload.getTitle());
		assert createdContactPayLoad.getContactName().equals(contactPayload.getContactName());
		assert createdContactPayLoad.getMobile().equals(contactPayload.getMobile());
		test.get().pass("Contact with mandatory fields created and validated successfully");
	}

	// Create Contact - All Fields
	@Test(priority = 2, enabled = true) // Test is working
	public void createContactWithAllFieldsTC_001() throws IOException {
		test.set(extent.createTest("Create Contact - All Fields"));

		String campaignId = prop.getProperty("campaignId");
		// Generate random contact data instead of loading from JSON
		CreateContactClassic_POJO contactPayload = CreateContact.generateRandomContact();
		// POST request and capture response
		CreateContactClassic_POJO createdContactAllFeildsPayload = given().header("accept", "application/json")
				.queryParam("campaignId", campaignId).body(contactPayload, ObjectMapperType.JACKSON_2).when()
				.post("/contact").then().log().all().statusCode(201).extract()
				.as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);

		// Assuming contactPayload is the request object and
		// createdContactAllFeildsPayload is the response object
		assert createdContactAllFeildsPayload.getContactName().equals(contactPayload.getContactName());
		assert createdContactAllFeildsPayload.getOrganizationName().equals(contactPayload.getOrganizationName());
		assert createdContactAllFeildsPayload.getDepartment().equals(contactPayload.getDepartment());
		assert createdContactAllFeildsPayload.getTitle().equals(contactPayload.getTitle());
		assert createdContactAllFeildsPayload.getMobile().equals(contactPayload.getMobile());
		assert createdContactAllFeildsPayload.getOfficePhone().equals(contactPayload.getOfficePhone());
		assert createdContactAllFeildsPayload.getEmail().equals(contactPayload.getEmail());
		assert createdContactAllFeildsPayload.getContactId() != null : "Contact ID should not be null";

		test.get().pass("Contact with all fields created and validated successfully");

	}

	// Update Contact
	// -------------------------------
	@Test(priority = 3, enabled = true)
	public void updateContactTC_0010() throws IOException {
		test.set(extent.createTest("Update Contact Test"));
		CreateContactClassic_POJO contactPayload = CreateContact.generateRandomContact();
		String contactId = postContact(contactPayload);
		System.out.println("contactId is :" + contactId);
		// Step 2: Generate new random values for update
	  
	    String updatedContactName = faker.name().fullName();
	    String updatedOrgName = faker.company().name();
	    String updatedTitle = faker.job().title();
	    String updatedMobile = faker.phoneNumber().cellPhone();

		// Step 2: Update some fields
		contactPayload.setContactName(updatedContactName);
		contactPayload.setOrganizationName(updatedOrgName);
		contactPayload.setTitle(updatedTitle);
		contactPayload.setMobile(updatedMobile);

		// Step 3: Send PUT request and capture response
		CreateContactClassic_POJO updatedContactPayload = given()
				.header("accept", "application/json")
				.queryParam("contactId", contactId)
				.queryParam("campaignId", prop.getProperty("campaignId"))
				.body(contactPayload, ObjectMapperType.JACKSON_2).when().put("/contact").then().log().all()
				.statusCode(200).extract().as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);

		// Step 4: Assertions to validate updated fields
		assert updatedContactPayload.getContactId().equals(contactId) : "contact ID mismatch after update";
		assert updatedContactPayload.getOrganizationName().equals(updatedOrgName) : "Oraganization name not updated correctly";
		assert updatedContactPayload.getTitle().equals(updatedTitle) : "Title is not updated correctly";
		assert updatedContactPayload.getContactName().equals(updatedContactName): "contact name is not updated correctly";
		assert updatedContactPayload.getMobile().equals(updatedMobile) : "mobile is not  updated correctly";

		test.get().pass("contact updated successfully and validated");
	}
	

	
	// Delete Contact
		
		@Test(priority = 4,enabled=true)//Test is working
		public void deleteContactTC_028() throws IOException {
		    test.set(extent.createTest("Delete Contact Test"));
		    CreateContactClassic_POJO contactPayload = CreateContact.generateRandomContact();
		    String contactId = postContact(contactPayload);
		    System.out.println("Created contact ID: " + contactId);

		    // Step 2: Send DELETE request
		    given()
		    .header("accept", "application/json")
		        .queryParam("contactId", contactId)
		        .queryParam("campaignId", prop.getProperty("campaignId"))
		    .when()
		        .delete("/contact")
		    .then()
		        .log().all()
		        .statusCode(204); // assuming 200 on successful deletion
		  
		    test.get().pass("Contact deleted successfully and verified");
		}
		
		// GET All contact with pagination
		@Test(priority = 5, enabled = true)//tets working
		public void getAllContactsPaginatedTC_037() {
		    test.set(extent.createTest("Get All Contacts"));

		    Response response = 
		    	given()
		    	
		    	 .queryParam("page", 0)
		            .queryParam("size", 20)
		        .when()
		            .get("/contact/all")
		        .then()
		            .log().all()
		            .statusCode(200) // Assert request success
		            .extract()
		            .response();
		   

		    test.get().pass("Get all contact API executed successfully and validated");
		}
		@Test(priority = 6, enabled = true)//test working
		public void getAllContactsTC_034() {
		    test.set(extent.createTest("Get All Contacts - Paginated"));

		    Response response = 
		    	given()
		        .when()
		            .get("/contact/all-contacts")
		        .then()
		            .log().all()
		            .statusCode(200) // Assert request success
		            .extract()
		            .response();
		   

		    test.get().pass("Paginated get all contact API executed successfully and validated");
		}

		
		@Test(priority = 7,enabled=true)//test working
		public void getContactCountTC_039() {
		    test.set(extent.createTest("Get Contact Count"));

		    int contactCount = 
		    	given()
		        .when()
		            .get("/contact/count")
		        .then()
		            .log().all()
		            .statusCode(200) // Ensure request succeeded
		            .extract()
		            .as(Integer.class); // Deserialize the response as an integer

		    // Assertion
		    assert contactCount >= 0 : "Contact count should be 0 or more";

		    test.get().pass("Contact count retrieved successfully: " + contactCount);
		}
	  @Test(priority=8,enabled=true)
		public void missingRequiredQueryParametercampaignIdTC_002() throws StreamReadException, DatabindException, IOException{
			
			 test.set(extent.createTest("Missing Required Query Parameter campaignId"));
			 CreateContactClassic_POJO contactPayload = CreateContact.generateMandtoryContact();
		
			    // POST request and capture response in createdContact
			   // CreateContactClassic_POJO createdContactPayLoad = 
			    	given()
			    	.header("accept", "application/json")
			            .body(contactPayload, ObjectMapperType.JACKSON_2)
			        .when()
			            .post("/contact")
			        .then()
			            .log().all()
			            .statusCode(500)
			            .extract()
			            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);
			    
			    //comparing  contactPayload is the request object and createdContactPayLoad is the response object
	
			    test.get().pass("Missing Required Query Parameter campaignId");

		}
	  //TC : is failing BUG
	  @Test(priority=9,enabled=true)
		public void missingRequiredFieldcontactNameorganizationNametitleinBodyTC_004() throws StreamReadException, DatabindException, IOException{
			
			 test.set(extent.createTest("Missing Required Field contactName, organizationName,title  in Body"));

			   String campaignId = prop.getProperty("campaignId");

			   CreateContactClassic_POJO contactPayload = CreateContact.generateMissingFieldContact();
			    	given()
			    	.header("accept", "application/json")
			            .queryParam("campaignId", campaignId)
			            .body(contactPayload, ObjectMapperType.JACKSON_2)
			        .when()
			            .post("/contact")
			        .then()
			            .log().all()
			            .statusCode(500)//it is 201 wrong
			            .extract()
			            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);
			    
			    //comparing  contactPayload is the request object and createdContactPayLoad is the response object
			 
			    

			    test.get().pass("Missing Required Field contactName, organizationName,title  in Body");

			
			
		}
	  //TC is failing Its a bug
	  @Test(priority=10,enabled=true)
		public void invalidEmailFormatTC_005() throws StreamReadException, DatabindException, IOException{
			
			 test.set(extent.createTest("Invalid Email Format"));

			   String campaignId = prop.getProperty("campaignId");

			   CreateContactClassic_POJO contactPayload = CreateContact.generateMissingFieldContact();
			   String invalidEmail= faker.name().username() + "@@gmail.com";
			   System.out.println(invalidEmail);
			   contactPayload.setEmail(invalidEmail);
			    // POST request and capture response in createdContact
			    CreateContactClassic_POJO createdContactPayLoad = 
			    	given()
			    	.header("accept", "application/json")
			            .queryParam("campaignId", campaignId)
			            .body(contactPayload, ObjectMapperType.JACKSON_2)
			        .when()
			            .post("/contact")
			        .then()
			            .log().all()
			            .statusCode(500)//it is 201 wrong
			            .extract()
			            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);
			    
			     test.get().pass("Invalid Email Format");
		
			
			
		}
		
	  //TC is failing Its a bug
	 @Test(priority=11,enabled=true)
		public void invalidMobileNumberFormatTC_007() throws StreamReadException, DatabindException, IOException{
			
			 test.set(extent.createTest("Invalid Mobile Number Format"));

			   String campaignId = prop.getProperty("campaignId");

			   CreateContactClassic_POJO contactPayload = CreateContact.generateMissingFieldContact();
			   String invalidLong = faker.number().digits(11);
			   System.out.println("*****"+ invalidLong);
			   contactPayload.setMobile(invalidLong);
			    // POST request and capture response in createdContact
			    CreateContactClassic_POJO createdContactPayLoad = 
			    	given()
			    	.header("accept", "application/json")
			            .queryParam("campaignId", campaignId)
			            .body(contactPayload, ObjectMapperType.JACKSON_2)
			        .when()
			            .post("/contact")
			        .then()
			            .log().all()
			            .statusCode(500)//it is 201 wrong
			            .extract()
			            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);
		
			    test.get().pass("Invalid Mobile Number Format");

			
			
		}
	 
	@Test(priority=12,enabled=true)
		public void largeInputValuesforContactNameTC_009() throws StreamReadException, DatabindException, IOException{
			
			 test.set(extent.createTest("Large Input Values for ContactName"));

			   String campaignId = prop.getProperty("campaignId");

			   CreateContactClassic_POJO contactPayload = CreateContact.generateRandomContact();
			  
			// Create a long contact name by combining multiple names
			String longContactName = faker.name().firstName() + " " +
			                         faker.name().lastName() + " " +
			                         faker.name().firstName() + " " +
			                         faker.name().lastName() + " " +
			                         faker.name().firstName() + " " +
			                         faker.name().lastName();

			// Optionally, make it exactly 100 characters (if needed)
			longContactName = longContactName.substring(0, Math.min(longContactName.length(), 100));
			contactPayload.setContactName(longContactName);
			
			  
			    // POST request and capture response in createdContact
			    CreateContactClassic_POJO createdContactPayLoad = 
			    	given()
			    	.contentType("application/json")
			        .accept("application/json")
			            .queryParam("campaignId", campaignId)
			            .body(contactPayload, ObjectMapperType.JACKSON_2)
			        .when()
			            .post("/contact")
			        .then()
			            .log().all()
			            .statusCode(500)//it is 201 wrong
			            .extract()
			            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);
			    
			    //comparing  contactPayload is the request object and createdContactPayLoad is the response object
//			    String contactId= createdContactPayLoad.getContactId();
//			    System.out.println("Created contact ID: " + contactId);
			    test.get().pass("Large Input Values for ContactName");

			
			
		}

	 @Test(priority=13,enabled=true)
		public void updateContactWithOnlyRequiredfieldsTC_011() throws StreamReadException, DatabindException, IOException{
			
			 test.set(extent.createTest("Update contact with only required fields"));

			 ObjectMapper mapper = new ObjectMapper();

			    // Step 1: Create a Contact first
			 CreateContactClassic_POJO contact = CreateContact.generateMandtoryContact();
			    String contactId = postContact(contact);
			    System.out.println("contactId is :"+contactId);

			    // Step 2: Update some fields
			  
			   

			    contact.setContactName(faker.name().fullName());
			    contact.setOrganizationName(faker.company().name());
			    contact.setTitle(faker.job().title());
			    contact.setMobile(faker.phoneNumber().cellPhone());
			    String expectedName = contact.getContactName();
			    String expectedOrg = contact.getOrganizationName();
			    String expectedTitle = contact.getTitle();
			    String expectedMobile = contact.getMobile();


			    // Step 3: Send PUT request and capture response
			    CreateContactClassic_POJO updatedContactPayload = 
			    	given()
			    	.header("accept", "application/json")
			            .queryParam("contactId", contactId)
			            .queryParam("campaignId", prop.getProperty("campaignId"))
			            .body(contact, ObjectMapperType.JACKSON_2)
			        .when()
			            .put("/contact")
			        .then()
			            .log().all()
			            .statusCode(200)
			            .extract()
			            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);

			    // Step 4: Assertions to validate updated fields
			    assert updatedContactPayload.getContactId().equals(contactId): "contact ID mismatch after update";
			    assert updatedContactPayload.getOrganizationName().equals(expectedOrg) : "Oraganization name not updated correctly";
			    assert updatedContactPayload.getTitle().equals(expectedTitle):"Title is not updated correctly";
			    assert updatedContactPayload.getContactName().equals(expectedName):"contact name is not updated correctly";
			    assert updatedContactPayload.getMobile().equals(expectedMobile):"mobile is not updated updated correctly";
			    
		

			    test.get().pass("Update contact with only required fields");

			
			
		}
	  //TC: it s a bug
	  @Test(priority=14,enabled=true)
		public void updatemobilenumbermorethan10digitTC_012() throws StreamReadException, DatabindException, IOException{
			
			 test.set(extent.createTest("Update mobile number more than 10 digit"));

			 

			    // Step 1: Create a Contact first
			 CreateContactClassic_POJO contact = CreateContact.generateMandtoryContact();
			    String contactId = postContact(contact);
			    System.out.println("contactId is :"+contactId);

			    // Step 2: Update some fields
			  
			    contact.setContactName(faker.name().fullName());
			    contact.setOrganizationName(faker.company().name());
			    contact.setTitle(faker.job().title());
			    contact.setMobile(faker.phoneNumber().cellPhone()+"2");
			    String expectedName = contact.getContactName();
			    String expectedOrg = contact.getOrganizationName();
			    String expectedTitle = contact.getTitle();
			    String expectedMobile = contact.getMobile();

			    // Step 3: Send PUT request and capture response
			    CreateContactClassic_POJO updatedContactPayload = 
			    	given()
			    	.header("accept", "application/json")
			            .queryParam("contactId", contactId)
			            .queryParam("campaignId", prop.getProperty("campaignId"))
			            .body(contact, ObjectMapperType.JACKSON_2)
			        .when()
			            .put("/contact")
			        .then()
			            .log().all()
			            .statusCode(500)
			            .extract()
			            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);

			    // Step 4: Assertions to validate updated fields
			    assert updatedContactPayload.getContactId().equals(contactId): "contact ID mismatch after update";
			    assert updatedContactPayload.getOrganizationName().equals(expectedOrg) : "Oraganization name not updated correctly";
			    assert updatedContactPayload.getTitle().equals(expectedTitle):"Title is not updated correctly";
			    assert updatedContactPayload.getContactName().equals(expectedName):"contact name is not updated correctly";
			    assert updatedContactPayload.getMobile().equals(expectedMobile):"mobile is not updated updated correctly";
			    
		
			    test.get().pass("Update mobile number more than 10 digit");

			
			
		}
	  
	  @Test(priority=15,enabled=true)
		public void MissingcontactIdinqueryparamsTC_015() throws StreamReadException, DatabindException, IOException{
			
			 test.set(extent.createTest("Missing contactId in query params"));

			    // Step 1: Create a Contact first
						 CreateContactClassic_POJO contact = CreateContact.generateMandtoryContact();
						    String contactId = postContact(contact);
						    System.out.println("contactId is :"+contactId);

						    // Step 2: Update some fields
						  
						    contact.setContactName(faker.name().fullName());
						    contact.setOrganizationName(faker.company().name());
						    contact.setTitle(faker.job().title());
						    contact.setMobile(faker.phoneNumber().cellPhone()+"2");
						    String expectedName = contact.getContactName();
						    String expectedOrg = contact.getOrganizationName();
						    String expectedTitle = contact.getTitle();
						    String expectedMobile = contact.getMobile();

			    // Step 3: Send PUT request and capture response
			    CreateContactClassic_POJO updatedContactPayload = 
			    	given()
			    	.header("accept", "application/json")
			           // .queryParam("contactId", contactId)
			            .queryParam("campaignId", prop.getProperty("campaignId"))
			            .body(contact, ObjectMapperType.JACKSON_2)
			        .when()
			            .put("/contact")
			        .then()
			            .log().all()
			            .statusCode(500)
			            .extract()
			            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);

			    test.get().pass("Missing contactId in query params");

			
			
		}
	  
	  //TC: it s a bug
	  @Test(priority=16,enabled=true)
		public void invalidContactNamedatatypeTC_022() throws StreamReadException, DatabindException, IOException{
			
			 test.set(extent.createTest("Invalid ContactName datatype"));

			 

			    // Step 1: Create a Contact first
			 CreateContactClassic_POJO contact = CreateContact.generateMandtoryContact();
			    String contactId = postContact(contact);
			    System.out.println("contactId is :"+contactId);

			    // Step 2: Update some fields
			   
			    contact.setContactName(faker.name().fullName() + "##");
			    contact.setOrganizationName(faker.company().name());
			    contact.setTitle(faker.job().title());
			    contact.setMobile(faker.phoneNumber().cellPhone()+"2");
			    String expectedName = contact.getContactName();
			    String expectedOrg = contact.getOrganizationName();
			    String expectedTitle = contact.getTitle();
			    String expectedMobile = contact.getMobile();

			    // Step 3: Send PUT request and capture response
			    CreateContactClassic_POJO updatedContactPayload = 
			    	given()
			    	.header("accept", "application/json")
			            .queryParam("contactId", contactId)
			            .queryParam("campaignId", prop.getProperty("campaignId"))
			            .body(contact, ObjectMapperType.JACKSON_2)
			        .when()
			            .put("/contact")
			        .then()
			            .log().all()
			            .statusCode(500)
			            .extract()
			            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);

			    // Step 4: Assertions to validate updated fields
			    assert updatedContactPayload.getContactId().equals(contactId): "contact ID mismatch after update";
			    assert updatedContactPayload.getOrganizationName().equals(expectedOrg) : "Oraganization name not updated correctly";
			    assert updatedContactPayload.getTitle().equals(expectedTitle):"Title is not updated correctly";
			    assert updatedContactPayload.getContactName().equals(expectedName):"contact name is not updated correctly";
			    assert updatedContactPayload.getMobile().equals(expectedMobile):"mobile is not updated updated correctly";
			    
		
			    test.get().pass("Invalid ContactName datatype");

			
			
		}
	  //TC: it s a bug
	  @Test(priority=17,enabled=true)
		public void invalidDepartmentTC_023() throws StreamReadException, DatabindException, IOException{
			
			 test.set(extent.createTest("Invalid Department"));

			 

			    // Step 1: Create a Contact first
			 CreateContactClassic_POJO contact = CreateContact.generateRandomContact();
			    String contactId = postContact(contact);
			    System.out.println("contactId is :"+contactId);

			    // Step 2: Update some fields
			   
			    contact.setDepartment(faker.company().profession().replaceAll("[a-zA-Z]", "@"));
			    String departnameName = contact.getDepartment();
			   

			    // Step 3: Send PUT request and capture response
			    CreateContactClassic_POJO updatedContactPayload = 
			    	given()
			    	.header("accept", "application/json")
			            .queryParam("contactId", contactId)
			            .queryParam("campaignId", prop.getProperty("campaignId"))
			            .body(contact, ObjectMapperType.JACKSON_2)
			        .when()
			            .put("/contact")
			        .then()
			            .log().all()
			            .statusCode(500)
			            .extract()
			            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);

			    // Step 4: Assertions to validate updated fields
			    assert updatedContactPayload.getContactId().equals(contactId): "contact ID mismatch after update";
			    assert updatedContactPayload.getDepartment().equals(departnameName) : "Oraganization name not updated correctly";
			   
			    test.get().pass("Invalid Department");

			
			
		}
	  //TC: it s a bug
	  @Test(priority=18,enabled=true)
		public void invalidEmaildatatypeFormat_TC024() throws StreamReadException, DatabindException, IOException{
			
			 test.set(extent.createTest("Invalid Email data type Format"));

			 

			    // Step 1: Create a Contact first
			 CreateContactClassic_POJO contact = CreateContact.generateRandomContact();
			    String contactId = postContact(contact);
			    System.out.println("contactId is :"+contactId);

			    // Step 2: Update some fields
			    
			   contact.setEmail("100");
			    String emailName = contact.getEmail();
			   

			    // Step 3: Send PUT request and capture response
			    CreateContactClassic_POJO updatedContactPayload = 
			    	given()
			    	.header("accept", "application/json")
			            .queryParam("contactId", contactId)
			            .queryParam("campaignId", prop.getProperty("campaignId"))
			            .body(contact, ObjectMapperType.JACKSON_2)
			        .when()
			            .put("/contact")
			        .then()
			            .log().all()
			            .statusCode(500)
			            .extract()
			            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);

			    // Step 4: Assertions to validate updated fields
			    assert updatedContactPayload.getContactId().equals(contactId): "contact ID mismatch after update";
			    assert updatedContactPayload.getEmail().equals(emailName) : "Oraganization name not updated correctly";
			   
			    test.get().pass("Invalid Email data type Format");

			
			
		}
	  //TC: it s a bug
	  @Test(priority=19,enabled=true)
		public void invalidMobileNumberdatatypeFormatTC025() throws StreamReadException, DatabindException, IOException{
			
			 test.set(extent.createTest("Invalid Mobile Number datatype Format"));

			 

			    // Step 1: Create a Contact first
			 CreateContactClassic_POJO contact = CreateContact.generateRandomContact();
			    String contactId = postContact(contact);
			    System.out.println("contactId is :"+contactId);

			    // Step 2: Update some fields
			    
			    
			 // Example invalid mobile numbers
			    List<String> invalidMobiles = Arrays.asList(
			        faker.lorem().characters(8),                    // Random letters
			        faker.lorem().characters(10, true, true),       // Letters + digits
			        "!@#" + faker.number().digits(4),               // Special characters + digits
			        faker.number().digits(15),                      // Too long
			        faker.number().digits(2),                       // Too short
			        "",                                             // Empty string
			        "123.456",                                      // Float as string
			        faker.internet().emailAddress(),                // Completely unrelated format
			        "null"                                          // Literal string "null"
			    );

			    // Pick a random invalid number
			    String invalidMobile = invalidMobiles.get(new Random().nextInt(invalidMobiles.size()));
			    
			  // contact.setMobile("!@#$%%*");
			    contact.setMobile(invalidMobile);
			    String mobile = contact.getMobile();
			   

			    // Step 3: Send PUT request and capture response
			    CreateContactClassic_POJO updatedContactPayload = 
			    	given()
			    	.header("accept", "application/json")
			            .queryParam("contactId", contactId)
			            .queryParam("campaignId", prop.getProperty("campaignId"))
			            .body(contact, ObjectMapperType.JACKSON_2)
			        .when()
			            .put("/contact")
			        .then()
			            .log().all()
			            .statusCode(500)
			            .extract()
			            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);

			    // Step 4: Assertions to validate updated fields
			    assert updatedContactPayload.getContactId().equals(contactId): "contact ID mismatch after update";
			    assert updatedContactPayload.getMobile().equals(mobile): "Mobile is not updated correctly";
			   
			    test.get().pass("Invalid Mobile Number datatype Format");

			
			
		}
	  
	  //TC: it s a bug
	  @Test(priority=20,enabled=true)
		public void invalidOrganizationNameDatatypeTC026() throws StreamReadException, DatabindException, IOException{
			
			 test.set(extent.createTest("Invalid Organization Name datatype "));

			 

			    // Step 1: Create a Contact first
			 CreateContactClassic_POJO contact = CreateContact.generateRandomContact();
			    String contactId = postContact(contact);
			    System.out.println("contactId is :"+contactId);

			    // Step 2: Update some fields
			    
			    List<String> invalidOrgNames = Arrays.asList(
			    	    faker.number().digits(5),
			    	    "!@#" + faker.lorem().characters(5),
			    	    "",
			    	    faker.lorem().characters(300),
			    	    faker.internet().emailAddress(),
			    	    "null",
			    	    String.valueOf(faker.bool().bool()),
			    	    faker.regexify("[0-9]{3}[A-Z]{2}[^a-zA-Z0-9]")
			    	);

			    	String invalidOrgName = invalidOrgNames.get(new Random().nextInt(invalidOrgNames.size()));
			    	contact.setOrganizationName(invalidOrgName);
			  // contact.setOrganizationName("100");
			    String orgName = contact.getOrganizationName();
			 
			    // Step 3: Send PUT request and capture response
			    CreateContactClassic_POJO updatedContactPayload = 
			    	given()
			    	.header("accept", "application/json")
			            .queryParam("contactId", contactId)
			            .queryParam("campaignId", prop.getProperty("campaignId"))
			            .body(contact, ObjectMapperType.JACKSON_2)
			        .when()
			            .put("/contact")
			        .then()
			            .log().all()
			            .statusCode(500)
			            .extract()
			            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);

			    // Step 4: Assertions to validate updated fields
			    assert updatedContactPayload.getContactId().equals(contactId): "contact ID mismatch after update";
			    assert updatedContactPayload.getOrganizationName().equals(orgName): "Oraganization name not updated correctly";
			   
			    test.get().pass("Invalid Organization Name datatype");

			
			
		}
	  @Test(priority=21,enabled=true)
		public void InvalidTitleNamedatatypeTC027() throws StreamReadException, DatabindException, IOException{
			
			 test.set(extent.createTest("Invalid Title Name datatype "));

			 

			    // Step 1: Create a Contact first
			 CreateContactClassic_POJO contact = CreateContact.generateRandomContact();
			    String contactId = postContact(contact);
			    System.out.println("contactId is :"+contactId);

			    // Step 2: Update some fields
			    
			    List<String> invalidTitleNames = Arrays.asList(
			    	    faker.number().digits(5),
			    	    "!@#" + faker.lorem().characters(5),
			    	    "",
			    	    faker.lorem().characters(300),
			    	    faker.internet().emailAddress(),
			    	    "null",
			    	    String.valueOf(faker.bool().bool()),
			    	    faker.regexify("[0-9]{3}[A-Z]{2}[^a-zA-Z0-9]")
			    	);

			    	String invalidTitleName = invalidTitleNames.get(new Random().nextInt(invalidTitleNames.size()));
			    	contact.setTitle(invalidTitleName);
			  // contact.setOrganizationName("100");
			    String titleName = contact.getTitle();
			 
			    // Step 3: Send PUT request and capture response
			    CreateContactClassic_POJO updatedContactPayload = 
			    	given()
			    	.header("accept", "application/json")
			            .queryParam("contactId", contactId)
			            .queryParam("campaignId", prop.getProperty("campaignId"))
			            .body(contact, ObjectMapperType.JACKSON_2)
			        .when()
			            .put("/contact")
			        .then()
			            .log().all()
			            .statusCode(500)
			            .extract()
			            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);

			    // Step 4: Assertions to validate updated fields
			    assert updatedContactPayload.getContactId().equals(contactId): "contact ID mismatch after update";
			    assert updatedContactPayload.getTitle().equals(titleName): "Title is not updated correctly";
			   
			    test.get().pass("Invalid Title Name datatype");

			
			
		}
	  @Test(priority =22,enabled=true)//Test is working
		public void VerifyResponseWhenDeletingtheSamecontacttwiceTC_029() throws IOException {
		    test.set(extent.createTest("Verify response when deleting the same contact twice"));
		    CreateContactClassic_POJO contactPayload = CreateContact.generateRandomContact();
		    String contactId = postContact(contactPayload);
		    System.out.println("Created contact ID: " + contactId);

		    // Step 2: Send DELETE request
		    given()
		    .header("accept", "application/json")
		        .queryParam("contactId", contactId)
		        .queryParam("campaignId", prop.getProperty("campaignId"))
		    .when()
		        .delete("/contact")
		    .then()
		        .log().all()
		        .statusCode(204); 
		  
		    // Step 2: Send DELETE request
		    given()
		    .header("accept", "application/json")
		        .queryParam("contactId", contactId)
		        .queryParam("campaignId", prop.getProperty("campaignId"))
		    .when()
		        .delete("/contact")
		    .then()
		        .log().all()
		        .statusCode(500); 
		  
		    test.get().pass("Verify response when deleting the same contact twice");
		}
	  
	  @Test(priority = 23,enabled=true)//Test is working
		public void DeletecontactwithmissingcontactIdTC_029() throws IOException {
		    test.set(extent.createTest("Delete contact with missing contactId"));
		    CreateContactClassic_POJO contactPayload = CreateContact.generateRandomContact();
		    String contactId = postContact(contactPayload);
		    System.out.println("Created contact ID: " + contactId);

		    // Step 2: Send DELETE request
		    given()
		    .header("accept", "application/json")
		      //  .queryParam("contactId", contactId)
		        .queryParam("campaignId", prop.getProperty("campaignId"))
		    .when()
		        .delete("/contact")
		    .then()
		        .log().all()
		        .statusCode(500); 
		  
		    
		    test.get().pass("Delete contact with missing contactId");
		}
	  @Test(priority = 24,enabled=true)//Test is working
		public void DeletecontactwithmissingcontactIdTC_030() throws IOException {
		    test.set(extent.createTest("Delete contact with missing contactId"));
		    CreateContactClassic_POJO contactPayload = CreateContact.generateRandomContact();
		    String contactId = postContact(contactPayload);
		    System.out.println("Created contact ID: " + contactId);

		    // Step 2: Send DELETE request
		    given()
		    .header("accept", "application/json")
		      //  .queryParam("contactId", contactId)
		        .queryParam("campaignId", prop.getProperty("campaignId"))
		    .when()
		        .delete("/contact")
		    .then()
		        .log().all()
		        .statusCode(500); 
		  
		    
		    test.get().pass("Delete contact with missing contactId");
		}
	  @Test(priority = 25,enabled=true)//Test is working
		public void DeleteContactWithInvalidContactIdformatTC_031() throws IOException {
		    test.set(extent.createTest("Delete contact with invalid contactId format"));
		    CreateContactClassic_POJO contactPayload = CreateContact.generateRandomContact();
		    String contactId = postContact(contactPayload);
		    System.out.println("Created contact ID: " + contactId);

		    // Step 2: Send DELETE request
		    given()
		    .header("accept", "application/json")
		       .queryParam("contactId", 34)
		        .queryParam("campaignId", prop.getProperty("campaignId"))
		    .when()
		        .delete("/contact")
		    .then()
		        .log().all()
		        .statusCode(500); 
		  
		    
		    test.get().pass("Delete contact with invalid contactId format");
		}
	  
	  @Test(priority = 26,enabled=true)//Test is working
		public void UnauthorizedAccessToDeleteContact_032() throws IOException {
		    test.set(extent.createTest("Unauthorized access to delete contact"));
		    CreateContactClassic_POJO contactPayload = CreateContact.generateRandomContact();
		    String contactId = postContact(contactPayload);
		    System.out.println("Created contact ID: " + contactId);

		    // Step 2: Send DELETE request
		    given()
		    .header("accept", "application/json")
		       .queryParam("contactId", 34)
		        .queryParam("campaignId", prop.getProperty("campaignId"))
		    .when()
		        .delete("/contact")
		    .then()
		        .log().all()
		        .statusCode(401); 
		  
		    
		    test.get().pass("Unauthorized access to delete contact");
		}
	  
	  @Test(priority = 27,enabled=true)//Test is working
		public void DeletecontactwithnonexistentcontactId_033() throws IOException {
		    test.set(extent.createTest("Delete contact with non-existent contactId"));
		    CreateContactClassic_POJO contactPayload = CreateContact.generateRandomContact();
		    String contactId = postContact(contactPayload);
		    System.out.println("Created contact ID: " + contactId);
		    contactId="CAM13056";

		    // Step 2: Send DELETE request
		    given()
		    .header("accept", "application/json")
		       .queryParam("contactId", contactId)
		        .queryParam("campaignId", prop.getProperty("campaignId"))
		    .when()
		        .delete("/contact")
		    .then()
		        .log().all()
		        .statusCode(500); 
		  
		    
		    test.get().pass("Delete contact with non-existent contactId");
		}
	  @Test(priority = 28,enabled=true)//Test is working
			public void Verifyunauthorizedaccess_034() throws IOException {
			    test.set(extent.createTest("Verify unauthorized access"));
			  

			    Response response = 
			    	given()
			    	 
			        .when()
			        
			            .get("/contact/all-contacts")
			        .then()
			            .log().all()
			            .statusCode(200) // Assert request success
			            .extract()
			            .response();
			   
			    
			    test.get().pass("Verify unauthorized access");
			}
	  
	  @Test(priority = 29,enabled=true)//Test is working
		public void CreateContactAndVerifyItExistEndToEndScenario_029() throws IOException, SQLException {
		    test.set(extent.createTest("Database Validation for Created contact"));
		    CreateContactClassic_POJO contactPayload = CreateContact.generateRandomContact();
		    String contactId = postContact(contactPayload);
		    System.out.println("Created contact ID: " + contactId);
		   
		    
		    String url = "jdbc:mysql://49.249.28.218:3333/crm";
			String dbUser = "root@%";
			String dbPassword = "root";

			Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
			Statement stmt = con.createStatement();
			
			String query = "Select * from contacts where contact_id= '" + contactId + "'";
			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				
				String dbConId = rs.getString("contact_id");

				
				Assert.assertEquals(dbConId, contactId,
						"DB Contact Id does not match API response!");
			} else {
				Assert.fail("No record found in DB for ContactId" +contactId );
			}
			con.close();
		  
		    
		    test.get().pass("Database Validation for Created contact");
		}
	// Helper method to get the contact ID after creation
	private String postContact(CreateContactClassic_POJO contact) {
		String campaignId = prop.getProperty("campaignId");
		return given().header("accept", "application/json").queryParam("campaignId", campaignId)
				.body(contact, ObjectMapperType.JACKSON_2).when().post("/contact").then().statusCode(201).extract()
				.path("contactId"); // capture the created contact ID
	}

}
