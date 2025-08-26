package api_tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import api_POJOS.OpportunitiesPojo;
import static io.restassured.RestAssured.*;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;

public class Opportunity_APITests extends api_BaseTest {
	private static String oppId;

	// -------------------------------
	// Create opportunity with Mandatory Fields
	// -------------------------------

	@Test(priority = 3, enabled = true)
	public void createOpportunityWithMandatoryFields(ITestContext context)
			throws StreamReadException, DatabindException, IOException {
		test.set(extent.createTest("Verify Lead Creation with mandatory fields"));

		String leadId = prop.getProperty("leadId");
		// Load mandatory Payload From Json
		ObjectMapper mapper = new ObjectMapper();
		OpportunitiesPojo oppPayload = mapper.readValue(
				getClass().getClassLoader().getResourceAsStream("api_testData/OppMandatoyTestData.json"),
				OpportunitiesPojo.class);

//		System.out.println(new ObjectMapper().writeValueAsString(oppPayload));

		// Post request

		OpportunitiesPojo createdOppPayload = 
				given()
					.queryParam("leadId", leadId)
					.body(oppPayload, ObjectMapperType.JACKSON_2)
					.contentType(ContentType.JSON)
				.when()
					.post("/opportunity")
				.then()
					.statusCode(201)
					.extract()
					.as(OpportunitiesPojo.class, ObjectMapperType.JACKSON_2);

		context.setAttribute("OpportunityId", createdOppPayload.getOpportunityId());

		assert createdOppPayload.getAmount().equals(oppPayload.getAmount()) : "Amount is not matching";
		assert createdOppPayload.getBusinessType().equals(oppPayload.getBusinessType())
				: "Business Type is not matching";
		assert createdOppPayload.getNextStep().equals(oppPayload.getNextStep()) : "Next Step is not matching";
		assert createdOppPayload.getOpportunityName().equals(oppPayload.getOpportunityName())
				: "Opportunity name is not matching";
		assert createdOppPayload.getSalesStage().equals(oppPayload.getSalesStage()) : "Sales Stage is not matching";

		test.get().pass("Opportunity with only mandatory fields created Successfully");

	}

	@Test(priority = 2, enabled = true, dependsOnMethods = "createOpportunity")
	public void updateOpportunityWithMandatoryFields(ITestContext context)
			throws StreamReadException, DatabindException, IOException {
		test.set(extent.createTest("Update Opportunity with Mandatory Fields"));

		ObjectMapper mapper = new ObjectMapper();

		// Step1 create opportunity first
		// This code reads JSON test data from the classpath file and converts it into a
		// POJO that you can directly use in your API request body.
		OpportunitiesPojo oppPayload = mapper.readValue(
				getClass().getClassLoader().getResourceAsStream("api_testData/OppMandatoyTestData.json"),
				OpportunitiesPojo.class);

		String createdOppId = (String) context.getAttribute("opportunityId");
		String leadId = prop.getProperty("leadId");

		// Step2. Update Some fields
		oppPayload.setAmount("99000");
		oppPayload.setOpportunityName("opp_test23456");
		oppPayload.setBusinessType("pharmacuetical");

		Map<String, Object> queryParameters = new HashMap<>();
		queryParameters.put("opportunityId", createdOppId);
		queryParameters.put("leadId", leadId);

		// Step3: Send put request and capture response
		OpportunitiesPojo updatedOppPayload = given().queryParams(queryParameters)
				.body(oppPayload, ObjectMapperType.JACKSON_2).when().put("/opportunity").then().statusCode(200)
				.extract().as(OpportunitiesPojo.class, ObjectMapperType.JACKSON_2);

		// Step4: assertion to validate updated fields
		assert updatedOppPayload.getAmount().equals("99000") : "Amount is not updated";
		assert updatedOppPayload.getOpportunityName().equals("opp_test23456") : "Opportunity name is not updated";
		assert updatedOppPayload.getBusinessType().equals("pharmacuetical") : "Business Type is not updated";
		assert updatedOppPayload.getOpportunityId().equals(createdOppId) : "OpportunityId is not matching after update";

		test.get().pass("Opportunity updated successfully and validated");

	}

	// Helper Test to get the OpportunityId after creation
	@Test(priority = 1, enabled = true)
	public void createOpportunity(ITestContext context) throws StreamReadException, DatabindException, IOException {
		String leadId = prop.getProperty("leadId");
		ObjectMapper mapper = new ObjectMapper();
		OpportunitiesPojo oppPayload = mapper.readValue(
				getClass().getClassLoader().getResourceAsStream("api_testData/OppMandatoyTestData.json"),
				OpportunitiesPojo.class);
		oppId = given().queryParam("leadId", leadId).body(oppPayload, ObjectMapperType.JACKSON_2).when()
				.post("/opportunity").then().statusCode(201).extract().path("opportunityId");// capture the created
																								// opportunity Id
		context.setAttribute("opportunityId", oppId);
	}

	@Test(priority = 4, enabled = true, dependsOnMethods = "createOpportunityWithMandatoryFields")
	public void deleteCreatedOpportunity(ITestContext context) {
		test.set(extent.createTest("Verify Opportunity is deleted successfuly"));
		String createdOpp = (String) context.getAttribute("opportunityId");
		System.out.println(createdOpp);
		given().queryParam("opportunityId", createdOpp).when().delete("/opportunity").then().statusCode(204);

		test.get().pass("Opportunity deleted successfully and verified");
	}

	@Test(priority = 5, enabled = true)
	public void getAllTheOpportunity() {
		test.set(extent.createTest("Get All Opportunities"));
		Response response = given().queryParam("page", 0).queryParam("size", 10).when().get("/opportunity/all").then()
				.log().all().statusCode(200).body("content", hasSize(10)) // to check the array length, use hasSize().
				.extract().response();

//		List<String> ids = response.jsonPath().getList("content.opportunityId");
//		Assert.assertTrue(ids.size()==10,"Expected 10 opportunities");
//		
		test.get().pass("Get all Opportunities API executed successfully and assertions passed");

	}

	@Test(priority = 6, enabled = true)
	public void getAllOpportunityNonPaginated() {
		test.set(extent.createTest("verify get all opportunity non pageable api"));

		Response response = given().when().get("/opportunity/all-opportunities").then().statusCode(200)
				.body("$", not(empty())) // Asserts that the root array is not empty
				.extract().response();
		test.get().pass("Get all-opportunies API executed succesfully and assertion passed");
	}

	@Test(priority = 7, enabled = true)
	public void getOpportunityCount() {
		test.set(extent.createTest("Verify the Opportunity count API is returning count"));

		int opportunityCount = given().when().get("/opportunity/count").then().statusCode(200).extract()
				.as(Integer.class);// Deserialize the response as an integer

		test.get().pass("opportunity count retrived successfully:" + opportunityCount);
	}
	
	@Test(priority = 8, enabled = true)
	public void createOpportunityWithAllTheFields(ITestContext context)
			throws StreamReadException, DatabindException, IOException {
		test.set(extent.createTest("verify opportunity is created with all the fields"));

		String leadId = prop.getProperty("leadId");
		ObjectMapper mapper = new ObjectMapper();
		OpportunitiesPojo oppPayload = mapper.readValue(
				getClass().getClassLoader().getResourceAsStream("api_testData/OppAllFieldsTestData.json"),
				OpportunitiesPojo.class);

		// post request

		OpportunitiesPojo createdOppPayload = given().queryParam("leadId", leadId).body(oppPayload)
				.contentType(ContentType.JSON).when().post("/opportunity").then().statusCode(201)
				.body("opportunityId", not(equalTo(null))).extract().as(OpportunitiesPojo.class);

		context.setAttribute("opportunityId", createdOppPayload.getOpportunityId());

		assert createdOppPayload.getAmount().equals(oppPayload.getAmount()) : "Amount is not matching";
		assert createdOppPayload.getAssignedTo().equals(oppPayload.getAssignedTo()) : "AssignedTo is not matching";
		assert createdOppPayload.getBusinessType().equals(oppPayload.getBusinessType())
				: "Business Type is not matching";
		assert createdOppPayload.getExpectedCloseDate().equals(oppPayload.getExpectedCloseDate())
				: "Expected Close Date is not matching";
		assert createdOppPayload.getNextStep().equals(oppPayload.getNextStep()) : "Next Step is not matching";
		assert createdOppPayload.getOpportunityName().equals(oppPayload.getOpportunityName())
				: "Opportunity Name is not matching";
		assert createdOppPayload.getProbability().equals(oppPayload.getProbability()) : "Probability is not matching";
		assert createdOppPayload.getSalesStage().equals(oppPayload.getSalesStage()) : "Sales Stage is not matching";

	}

	@Test(priority = 9, enabled = true, dependsOnMethods = "createOpportunityWithAllTheFields")
	public void updateCreatedOpportunityWithAllTheFields(ITestContext context)
			throws StreamReadException, DatabindException, IOException {
		test.set(extent.createTest("verify that the created Opportunity with all the fields should be updated"));

		ObjectMapper mapper = new ObjectMapper();
		OpportunitiesPojo oppPayload = mapper.readValue(
				getClass().getClassLoader().getResourceAsStream("api_testData/OppAllFieldsTestData.json"),
				OpportunitiesPojo.class);

		String oppId = (String) context.getAttribute("opportunityId");
		String leadId = prop.getProperty("leadId");

		// update fields
		oppPayload.setAmount("9000");
		oppPayload.setExpectedCloseDate("2025-08-30");
		oppPayload.setNextStep("create Invoice");
		// put request

		OpportunitiesPojo updatedPayload = given().queryParam("opportunityId", oppId).queryParam("leadId", leadId)
				.body(oppPayload).contentType(ContentType.JSON).when().put("/opportunity").then().statusCode(200)
				.body("amount", equalTo("9000")).body("expectedCloseDate", equalTo("2025-08-30"))
				.body("nextStep", equalTo("create Invoice")).extract().as(OpportunitiesPojo.class);

//		assert updatedPayload.getAmount().contentEquals("9000");
//		assert updatedPayload.getExpectedCloseDate().contentEquals("2025-08-30");
//		assert updatedPayload.getNextStep().contentEquals("create Invoice");
		test.get().pass("created Opportunity with all field is updated and asserted succesfully");
	}

	@Test(priority = 10, enabled = true, dependsOnMethods = "createOpportunityWithAllTheFields")
	public void deleteCreatedOpportunityWithAllTheFields(ITestContext context) {
		test.set(extent.createTest("Verify created Opportunity with all fields is deleted successfuly"));
		String createdOpp = (String) context.getAttribute("opportunityId");
		System.out.println(createdOpp);
		RestAssured.responseSpecification  = null;
		given().queryParam("opportunityId", createdOpp).when().delete("/opportunity").then().statusCode(204);

		test.get().pass("Opportunity deleted successfully and verified");
	}

	@Test(priority = 11, enabled = true)
	public void createOpportunityWithMissingLead() throws StreamReadException, DatabindException, IOException {
		test.set(extent.createTest("verify opportunity is created without lead"));

		ObjectMapper mapper = new ObjectMapper();
		OpportunitiesPojo oppPayload = mapper.readValue(
				getClass().getClassLoader().getResourceAsStream("api_testData/OppAllFieldsTestData.json"),
				OpportunitiesPojo.class);

		// post request

		Response Response = given().body(oppPayload).contentType(ContentType.JSON).when().post("/opportunity").then()
				.statusCode(anyOf(is(400), is(422), is(500))).body("message", containsString("leadId")).extract()
				.response();

		// validate opportunity is not ccreated
		Response allOppResponse = given().when().get("/opportunity/all").then().statusCode(200).extract().response();

		List<String> names = allOppResponse.jsonPath().getList("content.opportunityName");
		Assert.assertFalse(names.contains("string"), "Opportunity created even when leadId is missing");

		test.get().pass("Opportunity created without lead test is succesfull ");

	}
}
