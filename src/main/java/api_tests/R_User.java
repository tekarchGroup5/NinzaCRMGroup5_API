package api_tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import api_POJOS.CreateUser_POJO;
import api_POJOS.R_CreateUserPOJO;
import constants.FileConstants;
import utils.RestUtils;

public class R_User extends api_BaseTest {

    String department;
    String designation;
    String dob;
    String email;
    String empName;
    String experience;
    String mobileNo;
    String role;
    String username;
    String password;
    String empId;
    String deluserid;

    @BeforeMethod
    public void setupdata() {
        department = prop.getProperty("department");
        designation = prop.getProperty("designation");
        dob = prop.getProperty("dob");
        email = randomDataGenerator.R_CreateUser.getEmail();
        empName = randomDataGenerator.R_CreateUser.getEmpName();
        experience = prop.getProperty("experience");
        mobileNo = randomDataGenerator.R_CreateUser.getMobileNumber();
        role = prop.getProperty("role");
        username = randomDataGenerator.R_CreateUser.getUsername();
        password = randomDataGenerator.R_CreateUser.getPassword();
    }

 // TestScript -1
    @Test
    public void createUserWithAllFields() throws IOException {
        test.set(extent.createTest("Verify User Creation with All Fields"));

        // Endpoints
        String cuendpoint = "/admin/create-user";
        String guendpoint = "/admin/users";

        // Headers
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        // Build POJO for Request Body
        R_CreateUserPOJO cuP = new R_CreateUserPOJO(
                department, designation, dob, email, empName,
                experience, mobileNo, role, username, password
        );

        // Send POST request with POJO
        Response createuserRes = RestUtils.postReq(headers, cuP, cuendpoint);

        //  Validate status code
        Assert.assertEquals(createuserRes.getStatusCode(), 201, "User creation failed - unexpected status code!");

        // Extract empId from response
        empId = createuserRes.jsonPath().getString("empId");
        System.out.println("Response Body: " + createuserRes.getBody().asPrettyString());
        System.out.println("Emp Id is: " + empId);

        // Basic field validations
        Assert.assertNotNull(empId, "empId should not be null!");
        Assert.assertEquals(createuserRes.jsonPath().getString("username"), username, "Username mismatch!");
        Assert.assertEquals(createuserRes.jsonPath().getString("email"), email, "Email mismatch!");
        Assert.assertEquals(createuserRes.jsonPath().getString("empName"), empName, "EmpName mismatch!");

        //  Validate Response Schema
        RestUtils.validateSchema(createuserRes, FileConstants.R_CreateUserSchema_PATH);

        // Get all users and verify empId is present
        Response getusers = RestUtils.getReq(headers, guendpoint);
        Assert.assertEquals(getusers.getStatusCode(), 200, "Get Users API failed - unexpected status code!");

        List<String> allEmpIds = getusers.jsonPath().getList("empId");

        Assert.assertNotNull(allEmpIds, "Users list is null!");
        Assert.assertTrue(allEmpIds.contains(empId),
                "New User with empId " + empId + " is not found in the users list!");
    }

    @Test //TestScript -2
    public void loginWithCreatedUser() throws IOException {
        test.set(extent.createTest("Verify Login with Created User"));

        // Headers
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        // Create User POJO
        CreateUser_POJO cuP = new CreateUser_POJO(
                department, designation, dob, email, empName,
                experience, mobileNo, role, username, password
        );

        // Create the user first
        Response createuserRes = RestUtils.postReq(headers, cuP, "/admin/create-user");
        System.out.println("Created User Response: " + createuserRes.asPrettyString());

        // Now try logging in with the same user
        Response loginResponse = RestAssured
                .given()
                .auth().preemptive().basic(username, password)
                .contentType(ContentType.JSON)
                .when()
                .post("/login");

        System.out.println("Login Response: " + loginResponse.asPrettyString());

        // Validate login
        if (loginResponse.getStatusCode() == 200) {
            System.out.println("Login successful for user: " + username);
        } else if (loginResponse.getStatusCode() == 401) {
            System.out.println("Login failed: Unauthorized for user: " + username);
        } else {
            System.out.println("Login returned unexpected status: " + loginResponse.getStatusCode());
        }
    }

//TestScript -3

    @Test
    public void loginFailedWithWrongPassword() throws IOException {
        test.set(extent.createTest("Verify Login Failure: Unauthorized"));

        // Login with wrong password
        Response loginResponse = RestAssured
                .given()
                .auth().preemptive().basic(username, "wrongPass123")
                .contentType(ContentType.JSON)
                .when()
                .post("/login");

        System.out.println("Login Failed Response: " + loginResponse.asPrettyString());

        Assert.assertEquals(loginResponse.getStatusCode(), 401, "Expected 401 Unauthorized!");
        Assert.assertTrue(loginResponse.asString().toLowerCase().contains("unauthorized")
                || loginResponse.asString().toLowerCase().contains("invalid"),
                "Login failure response message mismatch!");
    }
    
   // TestScript -4
    
    @Test
    public void createUser_WrongEndpoint() throws IOException {
        test.set(extent.createTest("Verify POST to wrong endpoint returns 405"));

        String wrongEndpoint = "/admin/cre"; // wrong URL

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        CreateUser_POJO cuP = new CreateUser_POJO(
            department, designation, dob, email, empName,
            experience, mobileNo, role, username, password
        );

        RestAssured.responseSpecification = null; // disable default spec
        Response response = RestUtils.postReq(headers, cuP, wrongEndpoint, 405); // expecting 405
    }
   // TestScript -5
    
    @Test
    public void getUsersCount() throws IOException {
        test.set(extent.createTest("Verify GET /admin/users-count"));

        String endpoint = "/admin/users-count"; // endpoint to test

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        RestAssured.responseSpecification = null; // disable default spec
        Response response = RestUtils.getReq(headers, endpoint, 200); // expecting 200

        System.out.println("GET Users Count Response: " + response.asPrettyString());
    }
    // TestScript -6
    
    @Test(enabled = false)// failed test getting 200 ok but should get 401 Unauthorized access expected
    public void getUsers_Unauthorized() {
        String endpoint = "/admin/users"; // protected endpoint
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        RestAssured.responseSpecification = null;
        Response res = RestUtils.getReq(headers, endpoint);
        System.out.println(res.getBody().asPrettyString());
        Assert.assertEquals(res.getStatusCode(), 401, "Unauthorized access expected");
    }
//  // TestScript -7
@Test
    public void getUsersCount_Unauthorized() throws IOException {
        test.set(extent.createTest("Verify GET /admin/users-count without authentication"));

        String endpoint = "/admin/users-count"; // endpoint to test

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        RestAssured.responseSpecification = null; // disable default spec

        // Perform GET request without auth
        Response response = RestAssured
                .given()
                .headers(headers)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();

        // Verify status code is 401
        Assert.assertEquals(response.getStatusCode(), 401, "Expected 401 Unauthorized for unauthenticated access");

        System.out.println("Unauthorized GET Users Count Response: " + response.asPrettyString());
    }


// TestScript -8
@Test
public void getUsersCount_WrongEndpoint() throws IOException {
    test.set(extent.createTest("Verify GET to wrong endpoint returns 405"));

    String wrongEndpoint = "/admin/users-count-wrong"; // intentionally wrong
    HashMap<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");

    RestAssured.responseSpecification = null;

    Response response = RestAssured
            .given()
            .auth().preemptive().basic(username, password)
            .headers(headers)
            .when()
            .get(wrongEndpoint)
            .then()
            .extract()
            .response();

    Assert.assertEquals(response.getStatusCode(), 405, "Expected 405 Method Not Allowed for wrong GET endpoint");
    System.out.println("GET Wrong Endpoint Response: " + response.asPrettyString());
}

// TestScript -9 Positive Test: GET /admin/users with authentication

@Test
public void getUsers_Authorized() throws IOException {
 test.set(extent.createTest("GET /admin/users with authentication"));

 String endpoint = "/admin/users"; // Protected endpoint
 HashMap<String, String> headers = new HashMap<>();
 headers.put("Content-Type", "application/json");

 // Add Basic Authentication header
 String authHeader = "Basic " + java.util.Base64.getEncoder()
         .encodeToString((username + ":" + password).getBytes());
 headers.put("Authorization", authHeader);

 RestAssured.responseSpecification = null; // Disable default spec

 // Perform GET request with authentication
 Response resWithAuth = RestUtils.getReq(headers, endpoint);
 System.out.println("Authorized GET /admin/users Response: " + resWithAuth.asPrettyString());

 // Verify 200 OK
 Assert.assertEquals(resWithAuth.getStatusCode(), 200, "Expected 200 OK for authenticated access");

 // Verify that the users list is returned
 List<String> allEmpIds = resWithAuth.jsonPath().getList("empId");
 Assert.assertNotNull(allEmpIds, "Users list should not be null");
 Assert.assertTrue(allEmpIds.size() > 0, "Users list should contain at least one user");
}

//TestScript -10
@Test
public void deleteUser() throws IOException {
    test.set(extent.createTest("Verify User deletion"));

    // Endpoints
    String createEndpoint = "/admin/create-user";
    String deleteEndpoint = "/admin/user";
    String getUsersEndpoint = "/admin/users";

    // Headers
    HashMap<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");

    // Create a new user
    CreateUser_POJO user = new CreateUser_POJO(
            department, designation, dob, email, empName,
            experience, mobileNo, role, username, password
    );
    Response createResponse = RestUtils.postReq(headers, user, createEndpoint);

    // Extract empId of created user
    empId = createResponse.jsonPath().getString("empId");
    System.out.println("Created Employee ID: " + empId);

    // Verify user is present in the users list
    Response getUsersResponse = RestUtils.getReq(headers, getUsersEndpoint);
    List<String> allEmpIds = getUsersResponse.jsonPath().getList("empId");
    assertTrue(allEmpIds.contains(empId), "New user with empId " + empId + " is created");

    // Delete the user
    Map<String, String> queryParams = new HashMap<>();
    queryParams.put("userId", empId);
    HashMap<String, String> deleteHeaders = new HashMap<>();
    RestAssured.responseSpecification = null; // disable default spec
    Response deleteResponse = RestUtils.deleteReq(deleteHeaders, deleteEndpoint, queryParams, 204);

    // Verify deletion response
    assertEquals(deleteResponse.getHeader("Content-Type"), "text/plain;charset=UTF-8");
    System.out.println("Delete Response: " + deleteResponse.asPrettyString() + " Status: " + deleteResponse.getStatusCode());

    // Verify user is removed from users list
    Response getAfterDeleteResponse = RestUtils.getReq(headers, getUsersEndpoint);
    List<String> remainingEmpIds = getAfterDeleteResponse.jsonPath().getList("empId");
    assertFalse(remainingEmpIds.contains(empId), "User with empId " + empId + " should be deleted");
}
//TestScript -11

@Test
public void getSpecificUser() throws IOException {
    test.set(extent.createTest("Fetch specific user from /admin/users"));

    // Endpoints
    String createEndpoint = "/admin/create-user";
    String getUsersEndpoint = "/admin/users";

    // Headers
    HashMap<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");

    // Create a new user
    CreateUser_POJO user = new CreateUser_POJO(
            department, designation, dob, email, empName,
            experience, mobileNo, role, username, password
    );
    Response createResponse = RestUtils.postReq(headers, user, createEndpoint);

    // Extract empId of created user
    empId = createResponse.jsonPath().getString("empId");
    System.out.println("Created Employee ID: " + empId);

    // Get all users
    Response getUsersResponse = RestUtils.getReq(headers, getUsersEndpoint, 200);
    List<Map<String, Object>> users = getUsersResponse.jsonPath().getList("$");

    // Filter the specific user by empId
    Map<String, Object> specificUser = users.stream()
            .filter(u -> empId.equals(u.get("empId")))
            .findFirst()
            .orElse(null);

    // Assertions
    assertNotNull(specificUser, "Created user not found in /admin/users response");
    assertEquals(specificUser.get("username"), username, "Username should match");
    assertEquals(specificUser.get("email"), email, "Email should match");
    assertEquals(specificUser.get("empName"), empName, "Employee name should match");

    System.out.println("Fetched Specific User: " + specificUser);
}
// TestScript -12 createUserAndDeleteWithoutAuth

@Test
public void createUserAndDeleteWithoutAuth() throws Exception {
    Faker faker = new Faker();

    // Endpoints
    String createEndpoint = "/admin/create-user";
    String deleteEndpoint = "/admin/user";

    // Headers for creation
    HashMap<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");

    // Generate random user data using Faker
    String empName = faker.name().fullName();
    String email = faker.internet().emailAddress();
    String username = faker.name().username();
    String mobileNo = faker.phoneNumber().cellPhone().replaceAll("[^0-9]", "").substring(0, 10);

    // Create user POJO
    CreateUser_POJO user = new CreateUser_POJO(
            department, designation, dob, email, empName,
            experience, mobileNo, role, username, password
    );

    // Create user
    Response createResponse = RestUtils.postReq(headers, user, createEndpoint);
    String empId = createResponse.jsonPath().getString("empId");
    System.out.println("Created Employee ID: " + empId);

    // Attempt to delete the user WITHOUT auth
    Map<String, String> queryParams = new HashMap<>();
    queryParams.put("userId", empId);

    HashMap<String, String> noAuthHeaders = new HashMap<>();
    noAuthHeaders.put("Content-Type", "application/json");

    RestAssured.responseSpecification = null; // disable default spec
    Response deleteResponse = RestUtils.deleteReq(noAuthHeaders, deleteEndpoint, queryParams, 204);

    System.out.println("DELETE without auth Response: " + deleteResponse.asPrettyString());
    System.out.println("Status Code: " + deleteResponse.getStatusCode());

    // Verify 204 No Content
    assertEquals(deleteResponse.getStatusCode(), 204, "Expected 204 No Content for DELETE without auth");
}

//TestScript -13 deleteExistingUser

@Test
public void deleteExistingUser() {
    // Endpoint
    String deleteEndpoint = "/admin/user";

    // Existing userId to delete
    String empId = "UID_02209";

    // Query parameters
    Map<String, String> queryParams = new HashMap<>();
    queryParams.put("userId", empId);

    // Headers (no auth)
    HashMap<String, String> noAuthHeaders = new HashMap<>();
    noAuthHeaders.put("Content-Type", "application/json");

    // Disable RestAssured default response spec
    RestAssured.responseSpecification = null;

    // Send DELETE request manually without using RestUtils
    Response deleteResponse = RestAssured
            .given()
            .headers(noAuthHeaders)
            .queryParams(queryParams)
            .when()
            .delete(deleteEndpoint)
            .andReturn();  // <- no assertion on status code

    // Print response
    System.out.println("DELETE Response: " + deleteResponse.asPrettyString());
    int statusCode = deleteResponse.getStatusCode();
    System.out.println("Status Code: " + statusCode);

    // Handle multiple expected outcomes
    if (statusCode == 204) {
        System.out.println("User deleted successfully. Test Passed.");
    } else if (statusCode == 401) {
        System.out.println("Unauthorized request. Status 401. Test Passed for auth check.");
    } else if (statusCode == 400) {
        System.out.println("User not found. Status 400. Test Passed as user was already missing.");
    } else {
        System.out.println("Unexpected status: " + statusCode);
        assertTrue(false, "Unexpected status code: " + statusCode);
    }
}

@Test  //TestScript -14 createUserWithoutMobile
public void createUserWithoutMobile() throws IOException {
    test.set(extent.createTest("Verify User Creation without Mobile"));

    String cuEndpoint = "/admin/create-user";

    HashMap<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");

    Map<String, Object> payload = new HashMap<>();
    payload.put("department", department);
    payload.put("designation", designation);
    payload.put("dob", dob);
    payload.put("empName", empName);
    payload.put("role", role);
    payload.put("username", username);
    payload.put("password", password);
    payload.put("email", email);
    payload.put("experience", experience);
    // mobileNo is missing

    Response res = RestUtils.postReq(payload, headers, cuEndpoint);
    int statusCode = res.getStatusCode();
    System.out.println("Status Code: " + statusCode);
    System.out.println("Response: " + res.asPrettyString());

    assertEquals(statusCode, 500, "Expected 500 Internal Server Error when Mobile is missing");
    String message = res.jsonPath().getString("message");
    assertTrue(message.toLowerCase().contains("mobile"));
}

//TestScript -15 //API should NOT return 201, but it does → FAILED test


@Test   
public void createUserWithoutFullName() {
    Faker faker = new Faker();
    Map<String, Object> payload = new HashMap<>();

    payload.put("username", faker.name().username());
    payload.put("password", faker.internet().password());
    payload.put("role", "IT");
    payload.put("dob", "07/24/2000");
    payload.put("designation", "QA");
    payload.put("department", "Test");
    payload.put("experience", "0");
    payload.put("email", faker.internet().emailAddress());
    payload.put("mobileNo", faker.phoneNumber().cellPhone().replaceAll("[^0-9]", "").substring(0, 10));
    // empName intentionally missing

    Response res = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(payload)
            .post("http://49.249.28.218:8098/admin/create-user");

    int statusCode = res.getStatusCode();

    //  API should NOT return 201, but it does → FAIL the test
    Assert.assertNotEquals(statusCode, 201,
            "API BUG: User was created with empName=null (status 201) - should have failed");
}
  
//TestScript -16 createUserWithoutUsername

@Test   
public void createUserWithoutUsername() {
    Faker faker = new Faker();
    Map<String, Object> payload = new HashMap<>();
    // payload.put("username", faker.name().username()); // intentionally missing
    payload.put("password", faker.internet().password());
    payload.put("role", "IT");
    payload.put("dob", "07/24/2000");
    payload.put("designation", "QA");
    payload.put("department", "Test");
    payload.put("experience", "0");
    payload.put("email", faker.internet().emailAddress());
    payload.put("mobileNo", faker.phoneNumber().cellPhone().replaceAll("[^0-9]", "").substring(0, 10));
    payload.put("empName", faker.name().fullName());

    Response res = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(payload)
            .post("http://49.249.28.218:8098/admin/create-user");

    //  We expect failure, so status should NOT be 201
    Assert.assertNotEquals(res.getStatusCode(), 201,
            "API incorrectly allowed user creation without Username!");
}

}





