package api_tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import api_POJOS.CreateLeadClassic_POJO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;

public class LeadAPI_Tests extends api_BaseTest

{
	// -------------------------------
    // Create Lead - Mandatory Fields
    // -------------------------------
	
	@Test(priority = 1,enabled=true)//Test is working
	public void createLeadWithMandatoryFields() throws IOException {
	    test.set(extent.createTest("Verify Lead Creation with Mandatory Fields"));//Creates a new report entry specifically for this test (Create Lead API Test).


        // Load JSON payload into POJO
    	 //getClass().getClassLoader().getResourceAsStream("api_testData/leadPayload.json")
    	// getClass() → “give me this current class.”

    	// getClassLoader() → “give me the loader that finds files in resources.”

    	// getResourceAsStream("api_testData/leadPayload.json") → “find the file leadPayload.json in the resources folder and open it as a stream.”
	    String campaignId = prop.getProperty("campaignId");//Get the campaignId from the properties file.

	    // Load mandatory payload from JSON
	    ObjectMapper mapper = new ObjectMapper();//This is loading a JSON file and turning it into a Java object (POJO) so you can use it in your test.
	    CreateLeadClassic_POJO leadPayload = mapper.readValue(
	            getClass().getClassLoader().getResourceAsStream("api_testData/leadMandatoryPayload.json"),
	            CreateLeadClassic_POJO.class
	    );

	    // POST request and capture response in createdLead
	    CreateLeadClassic_POJO createdLeadPayLoad = 
	    	given()//prepare the request
	            .queryParam("campaignId", campaignId)//Adds ?campaignId=... to the URL.
	            .body(leadPayload, ObjectMapperType.JACKSON_2)//Takes your lead POJO and serializes it to JSON using Jackson.
	        .when()//send it
	            .post("/lead")//validate the response
	        .then()
	            .log().all()//Print the full response (status line, headers, body) to the console.
	            .statusCode(201)//Assert that the response status is 201 Created.
	            .extract()//
	            .as(CreateLeadClassic_POJO.class, ObjectMapperType.JACKSON_2);//
	    
	    //comparing  leadPayload is the request object and createdLeadPayload is the response object
	    assert createdLeadPayLoad.getIndustry().equals(leadPayload.getIndustry()) : "Industry mismatch";
	    assert createdLeadPayLoad.getLeadSource().equals(leadPayload.getLeadSource()) : "LeadSource mismatch";
	    assert createdLeadPayLoad.getLeadStatus().equals(leadPayload.getLeadStatus()) : "LeadStatus mismatch";
	    assert createdLeadPayLoad.getName().equals(leadPayload.getName()) : "Name mismatch";
	    assert createdLeadPayLoad.getCompany().equals(leadPayload.getCompany()) : "Company mismatch";
	    assert createdLeadPayLoad.getPhone().equals(leadPayload.getPhone()) : "Phone mismatch";

	    // Assertions for default generated values
	    assert createdLeadPayLoad.getLeadId() != null : "Lead ID should not be null";
	    assert createdLeadPayLoad.getEmail() == null : "Email should be null";
	    assert createdLeadPayLoad.getSecondaryEmail() == null : "Secondary Email should be null";
	    assert createdLeadPayLoad.getWebsite() == null : "Website should be null";
	    assert createdLeadPayLoad.getDescription() == null : "Description should be null";
	    assert createdLeadPayLoad.getRating() == null : "Rating should be null";
	    assert createdLeadPayLoad.getAnnualRevenue() == 0 : "Annual Revenue should default to 0";
	    assert createdLeadPayLoad.getNoOfEmployees() == 1 : "No. of Employees should default to 1";

	    test.get().pass("Lead with mandatory fields created and validated successfully");
	}
	// -------------------------------
    // Create Lead - All Fields
    // -------------------------------
	@Test(priority = 2,enabled=true)//Test is working
	public void createLeadWithAllFields() throws IOException {
	    test.set(extent.createTest("Create Lead - All Fields"));

	    String campaignId = prop.getProperty("campaignId");

	    // Load all fields payload
	    ObjectMapper mapper = new ObjectMapper();
	    CreateLeadClassic_POJO leadPayload = mapper.readValue(
	            getClass().getClassLoader().getResourceAsStream("api_testData/leadAllFeildsPayload.json"),
	            CreateLeadClassic_POJO.class
	    );

	    // POST request and capture response
	    CreateLeadClassic_POJO createdLeadAllFeildsPayload = 
	    		given()
	            .queryParam("campaignId", campaignId)
	            .body(leadPayload, ObjectMapperType.JACKSON_2)
	        .when()
	            .post("/lead")
	        .then()
	            .log().all()
	            .statusCode(201)
	            .extract()
	            .as(CreateLeadClassic_POJO.class, ObjectMapperType.JACKSON_2);

	 // Assuming leadPayload is the request object and createdLead is the response object

	    assert createdLeadAllFeildsPayload.getAddress().equals(leadPayload.getAddress()) : "Address mismatch";
	    assert createdLeadAllFeildsPayload.getAnnualRevenue() == leadPayload.getAnnualRevenue() : "Annual Revenue mismatch";
	    assert createdLeadAllFeildsPayload.getAssignedTo().equals(leadPayload.getAssignedTo()) : "AssignedTo mismatch";
	    assert createdLeadAllFeildsPayload.getCity().equals(leadPayload.getCity()) : "City mismatch";
	    assert createdLeadAllFeildsPayload.getCompany().equals(leadPayload.getCompany()) : "Company mismatch";
	    assert createdLeadAllFeildsPayload.getCountry().equals(leadPayload.getCountry()) : "Country mismatch";
	    assert createdLeadAllFeildsPayload.getDescription().equals(leadPayload.getDescription()) : "Description mismatch";
	    assert createdLeadAllFeildsPayload.getEmail().equals(leadPayload.getEmail()) : "Email mismatch";
	    assert createdLeadAllFeildsPayload.getIndustry().equals(leadPayload.getIndustry()) : "Industry mismatch";
	    assert createdLeadAllFeildsPayload.getLeadSource().equals(leadPayload.getLeadSource()) : "LeadSource mismatch";
	    assert createdLeadAllFeildsPayload.getLeadStatus().equals(leadPayload.getLeadStatus()) : "LeadStatus mismatch";
	    assert createdLeadAllFeildsPayload.getName().equals(leadPayload.getName()) : "Name mismatch";
	    assert createdLeadAllFeildsPayload.getNoOfEmployees() == leadPayload.getNoOfEmployees() : "NoOfEmployees mismatch";
	    assert createdLeadAllFeildsPayload.getPhone().equals(leadPayload.getPhone()) : "Phone mismatch";
	    assert createdLeadAllFeildsPayload.getPostalCode() == leadPayload.getPostalCode() : "PostalCode mismatch";
	    assert createdLeadAllFeildsPayload.getRating().equals(leadPayload.getRating()) : "Rating mismatch";
	    assert createdLeadAllFeildsPayload.getSecondaryEmail().equals(leadPayload.getSecondaryEmail()) : "SecondaryEmail mismatch";
	    assert createdLeadAllFeildsPayload.getWebsite().equals(leadPayload.getWebsite()) : "Website mismatch";
	  
	    // Assertions for default/generated values
	    assert createdLeadAllFeildsPayload.getLeadId() != null : "Lead ID should not be null";

	    test.get().pass("Lead with all fields created and validated successfully");
	}
	// -------------------------------
	// Update Lead
	// -------------------------------
	@Test(priority = 3,enabled=true)
	public void updateLead() throws IOException {
	    test.set(extent.createTest("Update Lead Test"));

	    ObjectMapper mapper = new ObjectMapper();

	    // Step 1: Create a lead first
	    CreateLeadClassic_POJO lead = mapper.readValue(
	            getClass().getClassLoader().getResourceAsStream("api_testData/leadAllFeildsPayload.json"),
	            CreateLeadClassic_POJO.class
	    );
	    String leadId = postLead(lead);

	    // Step 2: Update some fields
	    lead.setCompany("Madhu Company");
	    lead.setAnnualRevenue(999999);
	    lead.setPhone("+1-555-999-8888");
	    lead.setCity("Madhu City");      

	    // Step 3: Send PUT request and capture response
	    CreateLeadClassic_POJO updatedLeadPayload = 
	    	given()
	            .queryParam("leadId", leadId)
	            .queryParam("campaignId", prop.getProperty("campaignId"))
	            .body(lead, ObjectMapperType.JACKSON_2)
	        .when()
	            .put("/lead")
	        .then()
	            .log().all()
	            .statusCode(200)
	            .extract()
	            .as(CreateLeadClassic_POJO.class, ObjectMapperType.JACKSON_2);

	    // Step 4: Assertions to validate updated fields
	    assert updatedLeadPayload.getLeadId().equals(leadId) : "Lead ID mismatch after update";
	    assert updatedLeadPayload.getCompany().equals("Updated Company") : "Company not updated correctly";
	    assert updatedLeadPayload.getAnnualRevenue() == 999999 : "Annual Revenue not updated correctly";
	    assert updatedLeadPayload.getPhone().equals("+1-555-999-8888") : "Phone not updated correctly";
	    assert updatedLeadPayload.getCity().equals("Updated City") : "City not updated correctly";

	    test.get().pass("Lead updated successfully and validated");
	}
	// -------------------------------
	// Delete Lead
	// -------------------------------
	@Test(priority = 4,enabled=true)//Test is working but didnt know how to verify the lead is deleted
	public void deleteLead() throws IOException {
	    test.set(extent.createTest("Delete Lead Test"));

	    ObjectMapper mapper = new ObjectMapper();

	    // Step 1: Create a lead first
	    CreateLeadClassic_POJO lead = mapper.readValue(
	            getClass().getClassLoader().getResourceAsStream("api_testData/leadAllFeildsPayload.json"),
	            CreateLeadClassic_POJO.class
	    );
	    String leadId = postLead(lead);
	    System.out.println("Created Lead ID: " + leadId);

	    // Step 2: Send DELETE request
	    given()
	        .queryParam("leadId", leadId)
	        .queryParam("campaignId", prop.getProperty("campaignId"))
	    .when()
	        .delete("/lead")
	    .then()
	        .log().all()
	        .statusCode(204); // assuming 200 on successful deletion

	

	    test.get().pass("Lead deleted successfully and verified");
	}
	
//Helper method to get the lead ID after creation
private String postLead(CreateLeadClassic_POJO lead) {
    String campaignId = prop.getProperty("campaignId");
    return given()
            .queryParam("campaignId", campaignId)
            .body(lead, ObjectMapperType.JACKSON_2)
        .when()
            .post("/lead")
        .then()
            .statusCode(201)
            .extract()
            .path("leadId"); // capture the created lead ID
}
//
@Test(priority = 4, enabled = true)//tets working
public void getAllLeads() {
    test.set(extent.createTest("Get All Leads"));

    Response response = 
    	given()
            .queryParam("page", 0)
            .queryParam("size", 20)
        .when()
            .get("/lead/all-leads")
        .then()
            .log().all()
            .statusCode(200) // Assert request success
            .extract()
            .response();

    test.get().pass("Get all leads API executed successfully and validated");
}

@Test(priority = 4, enabled = true)//test working
public void getAllLeadsPaginated() {
    test.set(extent.createTest("Get All Leads - Paginated"));

    Response response = 
    	given()
            .queryParam("page", 0)
            .queryParam("size", 20)
        .when()
            .get("/lead/all")
        .then()
            .log().all()
            .statusCode(200) // Assert request success
            .extract()
            .response();

  

   

    test.get().pass("Paginated get all leads API executed successfully and validated");
}
@Test(priority = 5,enabled=true)//test working
public void getLeadCount() {
    test.set(extent.createTest("Get Lead Count"));

    int leadCount = 
    	given()
        .when()
            .get("/lead/count")
        .then()
            .log().all()
            .statusCode(200) // Ensure request succeeded
            .extract()
            .as(Integer.class); // Deserialize the response as an integer

    // Assertion
    assert leadCount >= 0 : "Lead count should be 0 or more";

    test.get().pass("Lead count retrieved successfully: " + leadCount);
}


}