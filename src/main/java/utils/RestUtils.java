package utils;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestUtils {
	public static Response postReq(HashMap<String, String> headers, Object payload, String path, int expectedStatus) {

		Response loginResponse = RestAssured.given().headers(headers).when().body(payload).post(path).then().statusCode(expectedStatus)
				.extract().response();
		return loginResponse;
	}
	
	public static Response postReqWithQuery(HashMap<String, String> headers, Object payload, String path, HashMap<String, String> queryParams, int expectedStatus) {
	    Response response = RestAssured.given()
	            .headers(headers)
	            .queryParams(queryParams)   // add query params here
	            .body(payload)              // attach your payload
	            .post(path)
	            .then()
	            .statusCode(expectedStatus)
	            .extract()
	            .response();
	    return response;
	}
//	  // 🔹 Overloaded: accept JsonNode headers + single path param
//    public static Response postReq(JsonNode headersJson, Object payload, String path, String paramKey, String paramValue, int expectedStatus) {
//        HashMap<String,String> headers = new HashMap<>();
//        headersJson.fieldNames().forEachRemaining(field -> headers.put(field, headersJson.get(field).asText()));
//
//        Map<String,String> pathParams = Map.of(paramKey, paramValue);
//
//        return postReq(headers, payload, path, pathParams, expectedStatus);
//    }

//	public static Response postReq(String header, String payload, String path) {
//
//		Response loginResponse = RestAssured.given().contentType(path).when().body(payload).post(path).then().statusCode(201)
//				.extract().response();
//		return loginResponse;
//	}

	public static Response putReq(HashMap<String, String> headers, String payload, String path, int expectedStatus) {
		Response putRes = RestAssured.given().headers(headers).when().body(payload).put(path).then().statusCode(expectedStatus)
				.extract().response();
		return putRes;

	}

	public static Response getReq(HashMap<String, String> headers, String path , int expectedStatus) {
		Response getRes = RestAssured.given().headers(headers).when().get(path).then().statusCode(expectedStatus).extract()
				.response();
		return getRes;

	}
	 // 🔹 Overloaded GET with queryParams
    public static Response getReq(HashMap<String, String> headers, String path, Map<String, String> queryParams, int expectedStatus) {
        return RestAssured.given()
                .headers(headers)
                .queryParams(queryParams)
                .when()
                .get(path)
                .then()
                .statusCode(expectedStatus)
                .extract()
                .response();
    }

	public static Response deleteReq(HashMap<String, String> headers, String payload, String path , int expectedStatus) {
		Response delRes = RestAssured.given().headers(headers).when().body(payload).delete(path).then().statusCode(expectedStatus)
				.extract().response();
		return delRes;
	}
	
	public static void validateSchema(Response response, String filePath) {
		response.then().assertThat().body(matchesJsonSchema(new File(filePath)));
	}
	
	public static Response postReq(HashMap<String, String> headers, Object payload, String path) {

		Response postResponse = RestAssured.given().headers(headers).when().body(payload).post(path).then().statusCode(201)
				.extract().response();
		return postResponse;
	}
	
	public static Response putReq(HashMap<String, String> headers, String payload, String path) {
		Response putRes = RestAssured.given().headers(headers).when().body(payload).put(path).then().statusCode(200)
				.extract().response();
		return putRes;

	}

	public static Response getReq(HashMap<String, String> headers, String path) {
		Response getRes = RestAssured.given().headers(headers).when().get(path).then().statusCode(200).extract()
				.response();
		return getRes;

	}
	
	public static Response deleteReq(HashMap<String, String> headers, String payload, String path) {
		Response delRes = RestAssured.given().headers(headers).when().body(payload).delete(path).then().statusCode(204)
				.extract().response();
		return delRes;
	}
	
}
