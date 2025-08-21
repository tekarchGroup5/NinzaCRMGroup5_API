package api_tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import api_POJOS.OpportunitiesPojo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.DataUtils;
import utils.RestUtils;

public class Opportunity_APITests extends api_BaseTest {
	// -------------------------------
    // Create opportunity with Mandatory Fields
    // -------------------------------
	
	@Test(priority = 1,enabled=true)
	public void createOpportunityWithMandatoryFields() {
		   // -------------------------------
        // Step 1: Prepare Headers
        // -------------------------------
        HashMap<String, String> headers = new HashMap<>(DataUtils.convertJsonNodeToMap(DataUtils.getHeaders()));

        // -------------------------------
        // Step 2: Prepare Endpoint
        // -------------------------------
        String endpoint = DataUtils.getEndpoint("createOpportunity");

        // -------------------------------
        // Step 3: Prepare Payload (POJO)
        // -------------------------------
        OpportunitiesPojo opportunity = DataUtils.getTestCaseAsPojo(
                "TC_001_CreateOpportunityWithMandatoryFields");

        // -------------------------------
        // Step 4: Prepare Query Params
        // -------------------------------
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("leadId", prop.getProperty("leadId"));

        // -------------------------------
        // Step 5: Call POST API with query params
        // -------------------------------
        Response response = RestUtils.postReqWithQuery(headers, opportunity, endpoint, queryParams, 201);

        // -------------------------------
        // Step 6: Validate Response
        // -------------------------------
        Assert.assertEquals(response.getStatusCode(), 201, "Status code mismatch!");
        
        String opportunityId = response.jsonPath().getString("opportunityId");
        Assert.assertNotNull(opportunityId, "Opportunity ID should not be null!");

        System.out.println("Created Opportunity ID: " + opportunityId);
    }
}


