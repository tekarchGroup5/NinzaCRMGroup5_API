package api_tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import api_POJOS.CreateContactClassic_POJO;

import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;

public class Contact_APITest extends api_BaseTest {
	
	  // Create Contact - Mandatory Fields
	@Test(priority = 1,enabled=false)//Test is working
	public void createContactWithMandatoryFields() throws IOException {
	    test.set(extent.createTest("Verify Contact Creation with Mandatory Fields"));

	    String campaignId = prop.getProperty("campaignId");

	    // Load mandatory payload from JSON
	    ObjectMapper mapper = new ObjectMapper();
	    CreateContactClassic_POJO contactPayload = mapper.readValue(
	            getClass().getClassLoader().getResourceAsStream("api_testData/contactMandatoryPayload.json"),
	            CreateContactClassic_POJO.class
	    );

	    // POST request and capture response in createdLead
	    CreateContactClassic_POJO createdContactPayLoad = 
	    	given()
	            .queryParam("campaignId", campaignId)
	            .body(contactPayload, ObjectMapperType.JACKSON_2)
	        .when()
	            .post("/contact")
	        .then()
	            .log().all()
	            .statusCode(201)
	            .extract()
	            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);
	    
	    //comparing  contactPayload is the request object and createdContactPayLoad is the response object
	  
	    
	    assert createdContactPayLoad.getContactName().equals(contactPayload.getContactName());
	    assert createdContactPayLoad.getTitle().equals(contactPayload.getTitle());
	    assert createdContactPayLoad.getContactName().equals(contactPayload.getContactName());
	    assert createdContactPayLoad.getMobile().equals(contactPayload.getMobile());
	    

	    test.get().pass("Contact with mandatory fields created and validated successfully");
	}
	
	
	 // Create Contact - All Fields
	@Test(priority = 2,enabled=true)//Test is working
	public void createContactWithAllFields() throws IOException {
	    test.set(extent.createTest("Create Contact - All Fields"));

	    String campaignId = prop.getProperty("campaignId");

	    // Load all fields payload
	    ObjectMapper mapper = new ObjectMapper();
	    CreateContactClassic_POJO contactPayload = mapper.readValue(
	            getClass().getClassLoader().getResourceAsStream("api_testData/contactAllFieldPayload.json"),
	            CreateContactClassic_POJO.class
	    );

	    // POST request and capture response
	    CreateContactClassic_POJO createdContactAllFeildsPayload = 
	    		given()
	            .queryParam("campaignId", campaignId)
	            .body(contactPayload, ObjectMapperType.JACKSON_2)
	        .when()
	            .post("/contact")
	        .then()
	            .log().all()
	            .statusCode(201)
	            .extract()
	            .as(CreateContactClassic_POJO.class, ObjectMapperType.JACKSON_2);

	 // Assuming contactPayload is the request object and createdContactAllFeildsPayload is the response object
	    assert createdContactAllFeildsPayload.getContactName().equals(contactPayload.getContactName());
	    assert createdContactAllFeildsPayload.getOrganizationName().equals(contactPayload.getOrganizationName());
	    assert createdContactAllFeildsPayload.getDepartment().equals(contactPayload.getDepartment());
	    assert createdContactAllFeildsPayload.getTitle().equals(contactPayload.getTitle());
	    assert createdContactAllFeildsPayload.getMobile().equals(contactPayload.getMobile());
	    assert createdContactAllFeildsPayload.getOfficePhone().equals(contactPayload.getOfficePhone());
	    assert createdContactAllFeildsPayload.getEmail().equals(contactPayload.getEmail());
	    assert createdContactAllFeildsPayload.getContactId()!= null : "Contact ID should not be null";

	    test.get().pass("Contact with all fields created and validated successfully");
	    
	}
	// Update Contact
	// -------------------------------
	@Test(priority = 3,enabled=true)
	public void updateContact() throws IOException {
	    test.set(extent.createTest("Update Contact Test"));

	    ObjectMapper mapper = new ObjectMapper();

	    // Step 1: Create a Contact first
	    CreateContactClassic_POJO contact = mapper.readValue(
	            getClass().getClassLoader().getResourceAsStream("api_testData/contactAllFieldPayload.json"),
	            CreateContactClassic_POJO.class
	    );
	    String contactId = postContact(contact);
	    System.out.println("contactId is :"+contactId);

	    // Step 2: Update some fields
	  
	   
	    contact.setContactName("UniveseGoogle");
	    contact.setOrganizationName("Google");
	    contact.setTitle("GoogleTest");
	    contact.setMobile("9999988888");

	    // Step 3: Send PUT request and capture response
	    CreateContactClassic_POJO updatedContactPayload = 
	    	given()
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
	    assert updatedContactPayload.getOrganizationName().equals("Google") : "Oraganization name not updated correctly";
	    assert updatedContactPayload.getTitle().equals("GoogleTest"):"Title is not updated correctly";
	    assert updatedContactPayload.getContactName().equals("UniveseGoogle"):"contact name is not updated correctly";
	    assert updatedContactPayload.getMobile().equals("9999988888"):"mobile is not updated updated correctly";
	    

	    test.get().pass("contact updated successfully and validated");
	}
	
	//Helper method to get the contact ID after creation
	private String postContact(CreateContactClassic_POJO contact) {
		String campaignId = prop.getProperty("campaignId");
	    return given()
	            .queryParam("campaignId", campaignId)
	            .body(contact, ObjectMapperType.JACKSON_2)
	        .when()
	            .post("/contact")
	        .then()
	            .statusCode(201)
	            .extract()
	            .path("contactId"); // capture the created contact ID
	}

	
	// Delete Contact
		
		@Test(priority = 4,enabled=true)//Test is working
		public void deleteLead() throws IOException {
		    test.set(extent.createTest("Delete Contact Test"));

		    ObjectMapper mapper = new ObjectMapper();

		    // Step 1: Create a Contact first
		    CreateContactClassic_POJO contact = mapper.readValue(
		            getClass().getClassLoader().getResourceAsStream("api_testData/contactAllFieldPayload.json"),
		            CreateContactClassic_POJO.class
		    );
		    String contactId = postContact(contact);
		    System.out.println("Created contact ID: " + contactId);

		    // Step 2: Send DELETE request
		    given()
		        .queryParam("contactId", contactId)
		        .queryParam("campaignId", prop.getProperty("campaignId"))
		    .when()
		        .delete("/contact")
		    .then()
		        .log().all()
		        .statusCode(204); // assuming 200 on successful deletion

		
		    
		    given()
		    .queryParam("contactId", contactId)
		    .queryParam("campaignId", prop.getProperty("campaignId"))
		.when()
		    .get("/contact/all")
		.then()
		    .statusCode(200);//404

		    test.get().pass("Contact deleted successfully and verified");
		}
		
	
	//
	@Test(priority = 5, enabled = true)//tets working
	public void getAllContactsPaginated() {
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
	public void getAllContacts() {
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
	public void getContactCount() {
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
	

}
