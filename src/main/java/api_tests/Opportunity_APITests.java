package api_tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
	// -------------------------------
	// Create opportunity with Mandatory Fields
	// -------------------------------

	@Test(priority = 1, enabled = true)
	public void createOpportunityWithMandatoryFields() throws StreamReadException, DatabindException, IOException {
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

	assert createdOppPayload.getAmount().equals(oppPayload.getAmount()) : "Amount is not matching";
	assert createdOppPayload.getBusinessType().equals(oppPayload.getBusinessType()) : "Business Type is not matching";
	assert createdOppPayload.getNextStep().equals(oppPayload.getNextStep()) : "Next Step is not matching";
	assert createdOppPayload.getOpportunityName().equals(oppPayload.getOpportunityName()) : "Opportunity name is not matching";
	assert createdOppPayload.getSalesStage().equals(oppPayload.getSalesStage()) : "Sales Stage is not matching";
	
	
	
		
	}
}
