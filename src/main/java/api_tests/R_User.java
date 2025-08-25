package api_tests;

import java.io.IOException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class R_User extends api_BaseTest {
    
    @Test(priority = 1, enabled = true)
    public void createUserWithMandatoryFields() throws IOException {
        test.set(extent.createTest("Verify User Creation with Mandatory Fields"));

        // Headers
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        // Request Body
        String requestBody = "{\n" +
                "  \"department\": \"Test4\",\n" +
                "  \"designation\": \"QA3\",\n" +
                "  \"dob\": \"07/24/2003\",\n" +
                "  \"email\": \"sk64@gmail.com\",\n" +
                "  \"empName\": \"SrK\",\n" +
                "  \"experience\": 1,\n" +
                "  \"mobileNo\": \"1934567097\",\n" +
                "  \"role\": \"IT\",\n" +
                "  \"username\": \"Ramen123\",\n" +
                "  \"password\": \"123478\"\n" +
                "}";

        // Send POST request
        Response response = RestAssured
                .given()
                    .auth().preemptive().basic("rmgyantra", "rmgy@9999")
                    .headers(headers)
                    .body(requestBody)
                .when()
                    .post("/admin/create-user")   
                .then()
                    .log().all()
                    .extract().response();

        // Assertions
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 201, "Expected status code is 201 but found " + statusCode);

        String username = response.jsonPath().getString("username");
        Assert.assertEquals(username, "Ramen", "Username mismatch!");
    }
}



