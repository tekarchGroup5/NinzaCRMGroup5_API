package api_tests;

import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import io.restassured.response.Response;
import utils.DataUtils;
import utils.RestUtils;

public class Opportunity_APITests extends api_BaseTest {
	// -------------------------------
    // Create opportunity with Mandatory Fields
    // -------------------------------
	
	@Test(priority = 1,enabled=true)
	public void createOpportunityWithMandatoryFields() {
		test.set(extent.createTest("Verify opportunity is created with mandatory fields"));
		
		String leadId = prop.getProperty("leadId");
		
		//load Mandatory payload from JSON
		String endpoint =DataUtils.getEndpoint("createOpportunity");
		JsonNode payload=DataUtils.getTestCase("TC_001_CreateOpportunityWithMandatoryFields");
		JsonNode headers = DataUtils.getHeaders();
		// Post request and capture response
		
//		Response loginRes = RestUtils.postReq(headers, payload, endpoint,201);

	}
	

}
