package api_tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import constants.FileConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import api_POJOS.AddCampaignPojo;
import utils.DataUtils;
import utils.RestUtils;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class Campaign_APITests extends api_BaseTest {

	@BeforeTest
	public void initialize() throws IOException {
		RestAssured.baseURI = "http://49.249.28.218:8098";
		RestUtils request = new RestUtils();
		// Step 1: Login (Basic Auth Example)
		Response loginResponse = RestAssured.given().auth().preemptive().basic("rmgyantra", "rmgy@9999")
				.contentType(ContentType.JSON).when().get("/login");

		// Verify login success (status code 200 or 202 based on API design)
		Assert.assertEquals(loginResponse.getStatusCode(), 202, "Login failed!");

	}

	@Test(enabled = true)
	public void TC1_Get_Campaign_with_Ten_the_records() throws IOException {

		test.set(extent.createTest("Create campaign with all the records"));

		ObjectMapper objMapper = new ObjectMapper();
		// String endPoint = DataUtils.getTestData("$.endpoints.getdata");
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		Response getUsersResponse = utils.RestUtils.getReq(headers,
				DataUtils.getTestData("$.endpoints.getData").toString());

		System.out.println(getUsersResponse.asPrettyString());
		String getUser1 = objMapper.writeValueAsString(getUsersResponse.jsonPath().get("[2]"));
		// System.out.println(getUser1);
		// DeserializeExample ds =
		// objMapper.readValue(getUser1,DeserializeExample.class);
		assertThat("Expecting 200 status code ", 200 == getUsersResponse.getStatusCode());
		Assert.assertEquals(getUsersResponse.statusCode(), 200);
	}

	// public void TC2_Get_Records_with_Invalid_Credentials() {}
	@Test
	public void TC3_Successful_Campaign_Creation() throws JsonProcessingException, IOException {

		test.set(extent.createTest("Verify that campaign is created with all the records"));

		ObjectMapper objMapper = new ObjectMapper();
		objMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String payload = objMapper.writeValueAsString(DataUtils.getTestData("$.payloads.addUser"));
		System.out.println(payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		// String path1 =
		// objMapper.writeValueAsString(DataUtils.getTestData("$.endpoints.adddata").toString());
		Response addUsersResponse = RestUtils.postReq(headers, payload,
				DataUtils.getTestData("$.endpoints.addData").toString());
		System.out.println("Response Body: " + addUsersResponse.getBody().asString());
		RestUtils.validateSchema(addUsersResponse, FileConstants.LOGIN_SCHEMA_FILE_PATH);

		Assert.assertEquals(addUsersResponse.statusCode(), 201);
		assertThat(addUsersResponse.statusCode(), is(201));
		assertThat(addUsersResponse.jsonPath().getString("campaignId"), not(isEmptyOrNullString()));
		assertThat(addUsersResponse.jsonPath().getString("campaignName"), not(isEmptyOrNullString()));
		assertThat(addUsersResponse.jsonPath().getString("description"), notNullValue());
		assertThat(addUsersResponse.jsonPath().getInt("targetSize"), greaterThanOrEqualTo(0));
		assertThat(addUsersResponse.jsonPath().getString("expectedCloseDate"),
				anyOf(nullValue(), not(isEmptyString())));
		assertThat(addUsersResponse.jsonPath().getString("targetAudience"), anyOf(nullValue(), not(isEmptyString())));
		assertThat(addUsersResponse.jsonPath().getString("campaignStatus"), anyOf(nullValue(), not(isEmptyString())));
	}

	@Test
	public void TC4_Create_records_with_mandatory_fields() throws JsonProcessingException, IOException {

		test.set(extent.createTest("Campaign is created with mandatory records"));

		ObjectMapper objMapper = new ObjectMapper();
		objMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String payload = objMapper.writeValueAsString(DataUtils.getTestData("$.payloads.mandatoryField"));
		System.out.println(payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		// String path1 =
		// objMapper.writeValueAsString(DataUtils.getTestData("$.endpoints.adddata").toString());
		Response addUsersResponse = RestUtils.postReq(headers, payload,
				DataUtils.getTestData("$.endpoints.addData").toString());
		System.out.println("Response Body: " + addUsersResponse.getBody().asString());
		// RestUtils.validateSchema(addUsersResponse,
		// FileConstants.LOGIN_SCHEMA_FILE_PATH);
		Assert.assertEquals(addUsersResponse.statusCode(), 201);
		System.out.println(" Record created with mandatory fields.");
	}

	@Test // failed: expected [400] but found [201]
	public void TC5_Create_record_with_Size_lessthan_1() throws JsonProcessingException, IOException {

		test.set(extent.createTest("Campaign is created with Size less than 1"));

		ObjectMapper objMapper = new ObjectMapper();
		objMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String payload = objMapper.writeValueAsString(DataUtils.getTestData("$.payloads.targetsize"));
		System.out.println(payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		// String path1 =
		// objMapper.writeValueAsString(DataUtils.getTestData("$.endpoints.adddata").toString());
		Response addUsersResponse = RestUtils.postReq(headers, payload,
				DataUtils.getTestData("$.endpoints.addData").toString());
		System.out.println("Response Body: " + addUsersResponse.getBody().asString());
		// RestUtils.validateSchema(addUsersResponse,
		// FileConstants.LOGIN_SCHEMA_FILE_PATH);
		Assert.assertEquals(addUsersResponse.statusCode(), 400);
		System.out.println(" Record cannot be created with less than 0 targetsize.");
	}

	@Test // Failed:expected [400] but found [201]
	public void TC6_Create_record_without_mandatory_field_Name() throws JsonProcessingException, IOException {

		test.set(extent.createTest("Campaign is created without mandatory Name"));

		ObjectMapper objMapper = new ObjectMapper();
		objMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String payload = objMapper.writeValueAsString(DataUtils.getTestData("$.payloads.noName"));
		System.out.println(payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		// String path1 =
		// objMapper.writeValueAsString(DataUtils.getTestData("$.endpoints.adddata").toString());
		Response addUsersResponse = RestUtils.postReq(headers, payload,
				DataUtils.getTestData("$.endpoints.addData").toString());
		System.out.println("Response Body: " + addUsersResponse.getBody().asString());
		// RestUtils.validateSchema(addUsersResponse,
		// FileConstants.LOGIN_SCHEMA_FILE_PATH);
		Assert.assertEquals(addUsersResponse.statusCode(), 400);
		System.out.println(" Record cannot be created without campaign name .");
	}

	@Test // Failed: expected [400] but found [201]
	public void TC7_Create_record_without_mandatory_field_Size() throws JsonProcessingException, IOException {

		test.set(extent.createTest("Campaign is created without mandatory field Size"));

		ObjectMapper objMapper = new ObjectMapper();
		objMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String payload = objMapper.writeValueAsString(DataUtils.getTestData("$.payloads.notSize"));
		System.out.println(payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		// String path1 =
		// objMapper.writeValueAsString(DataUtils.getTestData("$.endpoints.adddata").toString());
		Response addUsersResponse = RestUtils.postReq(headers, payload,
				DataUtils.getTestData("$.endpoints.addData").toString());
		System.out.println("Response Body: " + addUsersResponse.getBody().asString());
		// RestUtils.validateSchema(addUsersResponse,
		// FileConstants.LOGIN_SCHEMA_FILE_PATH);
		Assert.assertEquals(addUsersResponse.statusCode(), 400);
		System.out.println(" Record cannot be created with 0 targetsize.");
	}

	@Test
	public void TC8_Update_Campaign_with_mandatory_field_change_in_Name() throws JsonProcessingException, IOException {

		test.set(extent.createTest("Update Campaign with change in mandatory name"));
		// Creating mandatory records
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String payload = objMapper.writeValueAsString(DataUtils.getTestData("$.payloads.mandatoryField"));
		System.out.println(payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		Response addUsersResponse = RestUtils.postReq(headers, payload,
				DataUtils.getTestData("$.endpoints.addData").toString());
		System.out.println("Response Body: " + addUsersResponse.getBody().asString());
		Assert.assertEquals(addUsersResponse.statusCode(), 201);

		// Update name in record
		String cId = addUsersResponse.jsonPath().getString("campaignId");
		System.out.println(" Created CampaignName : " + cId);
		AddCampaignPojo campaign3 = new AddCampaignPojo("", "AutoTech123", "", "25", "", "", "");

		String payload3 = objMapper.writeValueAsString(campaign3);
		System.out.println(payload3);
		// HashMap<String, String> headers = new HashMap<String, String>();
		// headers.put("Content-Type", "application/json");

		Response updateNameResponse = RestUtils.putReq(headers, payload3,
				DataUtils.getTestData("$.endpoints.update").toString() + cId);
		System.out.println("Response Body: " + updateNameResponse.getBody().asString());
		int statusCode1 = updateNameResponse.statusCode();
		Assert.assertTrue(statusCode1 == 200,
				"Expected status code 200 for successful updation but was: " + statusCode1);
	}

	@Test // failed
	public void TC9_Update_Campaign_with_mandatory_field_change_in_Size() throws JsonProcessingException, IOException {

		test.set(extent.createTest("Update Campaign is created with change in mandatory field Size"));

		// Creating mandatory records
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String payload = objMapper.writeValueAsString(DataUtils.getTestData("$.payloads.mandatoryField"));
		System.out.println(payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		Response addUsersResponse = RestUtils.postReq(headers, payload,
				DataUtils.getTestData("$.endpoints.addData").toString());
		System.out.println("Response Body: " + addUsersResponse.getBody().asString());
		Assert.assertEquals(addUsersResponse.statusCode(), 201);

		// Update name in record
		String cId = addUsersResponse.jsonPath().getString("campaignId");
		System.out.println(" Updated TargetSize for campaign Id : " + cId);
		AddCampaignPojo campaign3 = new AddCampaignPojo("", "AutoTechExpo123", "", "1", "", "", "");

		String payload3 = objMapper.writeValueAsString(campaign3);
		System.out.println(payload3);

		Response updateSizeResponse = RestUtils.putReq(headers, payload3,
				DataUtils.getTestData("$.endpoints.update").toString() + cId);
		System.out.println("Response Body: " + updateSizeResponse.getBody().asString());

		String nameValue = campaign3.gettargetSize();
		System.out.println("From POJO :Campaign TargetSize: " + nameValue);

		String cSize = updateSizeResponse.jsonPath().getString("targetSize");
		System.out.println("From Response :Updated TargetSize: " + cSize);

		if (cSize != nameValue) {
			System.out.println("Update operation failed: expected Size" + cSize + " but got " + nameValue);
		}
		assertThat(cSize, is(nameValue));

		// int statusCode1 = updateSizeResponse.statusCode();
		// Assert.assertTrue(statusCode1 == 200, "Expected status code 200 for
		// successful updation but was: " + statusCode1);

	}

	@Test
	public void TC14_Delete_Campaign_using_ID() throws JsonProcessingException, IOException {

		test.set(extent.createTest("Delete Campaign using ID"));

		// Create record before deletion
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String payload = objMapper.writeValueAsString(DataUtils.getTestData("$.payloads.addUser"));
		System.out.println(payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		// String path1 =
		// objMapper.writeValueAsString(DataUtils.getTestData("$.endpoints.adddata").toString());
		Response addUsersResponse = RestUtils.postReq(headers, payload,
				DataUtils.getTestData("$.endpoints.addData").toString());
		System.out.println("Response Body: " + addUsersResponse.getBody().asString());

		// Delete Campaign record
		String cId = addUsersResponse.jsonPath().getString("campaignId");
		System.out.println(" Created CampaignId : " + cId);
		Response deleteRes = RestUtils.deleteReq(headers, " ",
				DataUtils.getTestData("$.endpoints.delete").toString() + cId);
		System.out.println("Response Body: " + deleteRes.getBody().asString());
		int statusCode1 = deleteRes.statusCode();
		Assert.assertTrue(statusCode1 == 200 || statusCode1 == 204,
				"Expected status code 200 or 204 for successful deletion but was: " + statusCode1);

	}
	// public void TC15_Before_delete_Update() {}

	@Test
	public void TC16_Delete_Campaign_after_Updation() throws JsonProcessingException, IOException {
		// Creating record for deletion

		test.set(extent.createTest("Delete Campaign after updation "));

		AddCampaignPojo campaign1 = new AddCampaignPojo("", "AutoTechExpo", "ongoing", "25", "2025-09-30",
				"Tech Companies", "This Campaign is to reach out technology companies");

		// String cId ="";
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String payload = objMapper.writeValueAsString(campaign1);
		System.out.println(payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		Response addUsersResponse = RestUtils.postReq(headers, payload,
				DataUtils.getTestData("$.endpoints.addData").toString());
		System.out.println("Response Body: " + addUsersResponse.getBody().asString());

		String cId = addUsersResponse.jsonPath().getString("campaignId");
		System.out.println(" Created CampaignId : " + cId);

		// Deleting record using newly generated id

		Response deleteRes = RestUtils.deleteReq(headers, " ",
				DataUtils.getTestData("$.endpoints.delete").toString() + cId);
		System.out.println("Response Body: " + deleteRes.getBody().asString());
		int statusCode1 = deleteRes.statusCode();
		Assert.assertTrue(statusCode1 == 200 || statusCode1 == 204,
				"Expected status code 200 or 204 for successful deletion but was: " + statusCode1);
		// Assert.assertEquals(deleteRes.statusCode(), 200, "Expected status code 204
		// for successful deletion");
		Assert.assertTrue(deleteRes.statusCode() == 204 || deleteRes.statusCode() == 200,
				"Expected status code 200 or 204 for successful deletion");
		if (deleteRes.statusCode() == 200) {
			deleteRes.then().body("message", equalTo("Campaign deleted successfully"));
		}

	}

	@Test // Failing : expected [400] but found [201]
	public void TC21_Create_Record_with_Invalid_Date() throws IOException {

		test.set(extent.createTest("Create Campaign with invalid date"));

		AddCampaignPojo campaign2 = new AddCampaignPojo("", "AutoTechExpo", "ongoing", "25", "456-09-40",
				"Tech Companies", "This Campaign is to reach out technology companies");

		ObjectMapper objMapper = new ObjectMapper();
		objMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String payload = objMapper.writeValueAsString(campaign2);
		System.out.println(payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");

		Response addUsersResponse = RestUtils.postReq(headers, payload,
				DataUtils.getTestData("$.endpoints.addData").toString());
		System.out.println("Response Body: " + addUsersResponse.getBody().asString());
		Assert.assertEquals(addUsersResponse.statusCode(), 400);
		Assert.assertTrue(addUsersResponse.statusCode() == 400,
				"Expected status code 400 for successful deletion but was: " + addUsersResponse.statusCode());
	}

	/*
	 * public void TC10_Update_Campaign_with_date() {} public void
	 * TC11_Update_Campaign_with_Status() {} public void
	 * TC12_Update_Campaign_with_TargetAudience() {} public void
	 * TC13_Delete_Campaign_Using_Name() {}
	 */

	// public void TC17_Check_Pagination() {}
	// public void TC18_Duplicate_Campaign_name() {}
	/*
	 * public void TC19_Create_record_with_Zero_Size() {} public void
	 * TC20_Update_Campaign_with_mandatory_field_change_in_Size() {}
	 * 
	 * public void TC22_Create_Campaign_with_empty_fields() {} public void
	 * TC23_Delete_nonexisted_ID() {} public void
	 * TC24_Campaign_name_with_invalid_datatypes() {}
	 */

}
