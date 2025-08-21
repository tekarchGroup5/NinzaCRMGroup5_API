package api_tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import api_POJOS.CreateContactClassic_POJO;
import api_POJOS.CreateLeadClassic_POJO;
import io.restassured.mapper.ObjectMapperType;

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
	@Test(priority = 1,enabled=false)//Test is working
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
	// Update Lead
	// -------------------------------
	@Test(priority = 1,enabled=true)
	public void updateContact() throws IOException {
	    test.set(extent.createTest("Update Lead Test"));

	    ObjectMapper mapper = new ObjectMapper();

	    // Step 1: Create a lead first
	    CreateContactClassic_POJO contact = mapper.readValue(
	            getClass().getClassLoader().getResourceAsStream("api_testData/contactAllFieldPayload.json"),
	            CreateContactClassic_POJO.class
	    );
	    String contactId = postContact(contact);

	    // Step 2: Update some fields
	  
	   
	    contact.setContactName("Univese2");
	    contact.setOrganizationName("AWS");
	    contact.setTitle("AI@");
	    contact.setMobile("9000000121");

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
	    assert updatedContactPayload.getOrganizationName().equals("AWS") : "Oraganization name not updated correctly";
	    assert updatedContactPayload.getTitle().equals("AI@"):"Title is not updated correctly";
	    assert updatedContactPayload.getContactName().equals("Univese2"):"contact name is not updated correctly";
	    assert updatedContactPayload.getMobile().equals("9000000121"):"mobile is not updated updated correctly";
	    

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


	
	
	

}
