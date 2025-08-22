package api_tests;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import api_POJOS.CreateLead_POJO;

public class Lead_APITests extends api_BaseTest {
	@Test
	public void createLeadFromJson() throws IOException {
		test.set(extent.createTest("Create Lead API Test"));

		// Load properties
		String baseURL = prop.getProperty("baseURL");
		String campaignId = prop.getProperty("campaignId");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");

		// Load JSON payload into POJO
		ObjectMapper mapper = new ObjectMapper();
		CreateLead_POJO lead = mapper.readValue(
				getClass().getClassLoader().getResourceAsStream("api_testData/leadPayload.json"),
				CreateLead_POJO.class);

		// POST request
		given().auth().preemptive().basic(username, password).contentType(ContentType.JSON).accept(ContentType.JSON)
				.queryParam("campaignId", campaignId).body(lead, ObjectMapperType.JACKSON_2).when()
				.post(baseURL + "/lead").then().log().all().statusCode(201);

		test.get().pass("Lead created successfully");
	}
}