package api_tests;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;//Jackson’s JSON helper. Turns JSON text ⇄ Java objects.

import api_POJOS.CreateLeadClassic_POJO;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;

public class Lead_APITests_old extends api_BaseTest {

    @Test
    public void createLeadFromJson() throws IOException {
    	 test.set(extent.createTest("Create Lead API Test"));//Creates a new report entry specifically for this test (Create Lead API Test).

       

        // Load JSON payload into POJO
    	 //getClass().getClassLoader().getResourceAsStream("api_testData/leadPayload.json")
    	// getClass() → “give me this current class.”

    	// getClassLoader() → “give me the loader that finds files in resources.”

    	// getResourceAsStream("api_testData/leadPayload.json") → “find the file leadPayload.json in the resources folder and open it as a stream.”
    	 		String campaignId = prop.getProperty("campaignId");//Get the campaignId from the properties file.
        ObjectMapper mapper = new ObjectMapper();//This is loading a JSON file and turning it into a Java object (POJO) so you can use it in your test.
        CreateLeadClassic_POJO lead = mapper.readValue(
                getClass().getClassLoader().getResourceAsStream("api_testData/leadPayload.json"),
                CreateLeadClassic_POJO.class
        );

        // POST request
        given()//prepare the request
           
            .queryParam("campaignId", campaignId)//Adds ?campaignId=... to the URL.
            .body(lead, ObjectMapperType.JACKSON_2)//Takes your lead POJO and serializes it to JSON using Jackson.
             .when()//send it
             .post("/lead")
             .then()//validate the response
            .log().all()//Print the full response (status line, headers, body) to the console.
            .statusCode(201);//Assert that the response status is 201 Created.

        test.get().pass("Lead created successfully");
    }
}