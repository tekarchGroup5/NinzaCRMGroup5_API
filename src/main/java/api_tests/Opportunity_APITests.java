package api_tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import api_POJOS.OpportunitiesPojo;
import static io.restassured.RestAssured.*;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

public class Opportunity_APITests extends api_BaseTest {
	private static String oppId;
	
	// -------------------------------
	// Create opportunity with Mandatory Fields
	// -------------------------------

	@Test( priority = 3,enabled = true)
	public void createOpportunityWithMandatoryFields(ITestContext context) throws StreamReadException, DatabindException, IOException {
		test.set(extent.createTest("Verify Lead Creation with mandatory fields"));

		String leadId = prop.getProperty("leadId");
		// Load mandatory Payload From Json
		ObjectMapper mapper = new ObjectMapper();
		OpportunitiesPojo oppPayload = mapper.readValue(
				getClass().getClassLoader().getResourceAsStream("api_testData/OppTestData.json"),
				OpportunitiesPojo.class);

//		System.out.println(new ObjectMapper().writeValueAsString(oppPayload));

		// Post request

		OpportunitiesPojo createdOppPayload = given().queryParam("leadId", leadId)
				.body(oppPayload, ObjectMapperType.JACKSON_2).contentType(ContentType.JSON).when().post("/opportunity")
				.then().statusCode(201).extract().as(OpportunitiesPojo.class, ObjectMapperType.JACKSON_2);
		
		context.setAttribute("OpportunityId", createdOppPayload.getOpportunityId());
		
	assert createdOppPayload.getAmount().equals(oppPayload.getAmount()) : "Amount is not matching";
	assert createdOppPayload.getBusinessType().equals(oppPayload.getBusinessType()) : "Business Type is not matching";
	assert createdOppPayload.getNextStep().equals(oppPayload.getNextStep()) : "Next Step is not matching";
	assert createdOppPayload.getOpportunityName().equals(oppPayload.getOpportunityName()) : "Opportunity name is not matching";
	assert createdOppPayload.getSalesStage().equals(oppPayload.getSalesStage()) : "Sales Stage is not matching";
	
	test.get().pass("Opportunity with only mandatory fields created Successfully");
		
	}
	@Test(priority=2, enabled = true, dependsOnMethods= "createOpportunity")
	public void updateOpportunityWithMandatoryFields(ITestContext context) throws StreamReadException, DatabindException, IOException {
	test.set(extent.createTest("Update Opportunity with Mandatory Fields"));
	
	ObjectMapper mapper = new ObjectMapper();
	
	//Step1 create opportunity first
	//This code reads JSON test data from the classpath file and converts it into a POJO that you can directly use in your API request body.
	OpportunitiesPojo oppPayload = mapper.readValue(getClass().getClassLoader().getResourceAsStream("api_testData/OppTestData.json"),
			OpportunitiesPojo.class);
	
	String createdOppId = (String)context.getAttribute("opportunityId");
	String leadId = prop.getProperty("leadId");
	
	//Step2. Update Some fields
	oppPayload.setAmount("99000");
	oppPayload.setOpportunityName("opp_test23456");
	oppPayload.setBusinessType("pharmacuetical");
	
	Map<String,Object> queryParameters = new HashMap<>();
	queryParameters.put("opportunityId", createdOppId);
	queryParameters.put("leadId",leadId);
	
	//Step3: Send put request and capture response
	OpportunitiesPojo updatedOppPayload = 
			given()
				.queryParams(queryParameters)
				.body(oppPayload,ObjectMapperType.JACKSON_2)
			.when()
				.put("/opportunity")
			.then()
				.statusCode(200)
				.extract()
				.as(OpportunitiesPojo.class, ObjectMapperType.JACKSON_2);
	
	//Step4: assertion to validate updated fields
	assert updatedOppPayload.getAmount().equals("99000") : "Amount is not updated";
	assert updatedOppPayload.getOpportunityName().equals("opp_test23456") :"Opportunity name is not updated";
	assert updatedOppPayload.getBusinessType().equals("pharmacuetical") : "Business Type is not updated";
	assert updatedOppPayload.getOpportunityId().equals(createdOppId) : "OpportunityId is not matching after update";
	
	test.get().pass("Opportunity updated successfully and validated");
	
	}

	//Helper Test to get the OpportunityId after creation
		@Test(priority = 1,enabled =true)
		public void  createOpportunity(ITestContext context) throws StreamReadException, DatabindException, IOException {
			String leadId = prop.getProperty("leadId");
			ObjectMapper mapper = new ObjectMapper();
			OpportunitiesPojo oppPayload = mapper.readValue(getClass().getClassLoader().getResourceAsStream("api_testData/OppTestData.json"),
					OpportunitiesPojo.class);
			oppId = given()
					.queryParam("leadId",leadId )
					.body(oppPayload, ObjectMapperType.JACKSON_2)
				.when()
					.post("/opportunity")
				.then()
					.statusCode(201)
					.extract()
					.path("opportunityId");//capture the created opportunity Id
			context.setAttribute("opportunityId", oppId);
		}
	
	@Test(priority =4, enabled= true,dependsOnMethods= "createOpportunityWithMandatoryFields")
	public void deleteCreatedOpportunity(ITestContext context) {
		test.set(extent.createTest("Verify Opportunity is deleted successfuly"));
		String createdOpp = (String)context.getAttribute("opportunityId");
		System.out.println(createdOpp);
		given()
			.queryParam("opportunityId",createdOpp)
		.when()
			.delete("/opportunity")
		.then()
			.statusCode(204);
		
	}
	
}
