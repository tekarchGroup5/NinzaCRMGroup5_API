package api_tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import api_POJOS.CreateUser_POJO;
import constants.FileConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.RestUtils;

public class CreateUserTest extends api_BaseTest{
	
	String department;
	String designation;
	String  dob;
	String  email;
	String  empName;
	String  experience;
	String  mobileNo;
	String role;
	String username;
	String password ;
	String empId;
	
	String deluserid;
	
	@BeforeMethod
	public void setupdata() {
		
		department = prop.getProperty("department");
		designation = prop.getProperty("designation");
		dob = prop.getProperty("dob");
		email = randomDataGenerator.CreateUser.getEmail();
		empName = randomDataGenerator.CreateUser.getEmpName();
		experience = prop.getProperty("experience");
		mobileNo = randomDataGenerator.CreateUser.getMobileNumber();
		role = prop.getProperty("role");
		username = randomDataGenerator.CreateUser.getUsername();
		password = randomDataGenerator.CreateUser.getPassword();
		
		
	}
	
	
	
	@Test(priority = 1,enabled=true)  //Pass
	public void createUserWithAllFields() throws IOException {
	    test.set(extent.createTest("Verify User Creation with All Fields"));
	      
	    String cuendpoint = "/admin/create-user";
	    String guendpoint = "/admin/users";
	    
	    HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		
		CreateUser_POJO cuP = new CreateUser_POJO(department, designation, dob, email, empName, experience,
													mobileNo, role, username, password);
		
		Response createuserRes =  RestUtils.postReq(headers, cuP, cuendpoint);
		
		empId = createuserRes.jsonPath().getString("empId");
		
		System.out.println("Response Body: " + createuserRes.getBody().asPrettyString());
		
		System.out.println("Emp Id is: " +empId);
		RestUtils.validateSchema(createuserRes, FileConstants.CreateUser_Schema_PATH);
	    
		Response getusers = RestUtils.getReq(headers, guendpoint);
		
		List<String> allEmpIds = getusers.jsonPath().getList("empId");

//		System.out.println("All Employee IDs: " + allEmpIds);

		// Assert that the empId is in the list
		assertTrue(allEmpIds.contains(empId), "New User with empId " + empId + " is created");
		    	    
	}
	
	
	@Test(priority = 1,enabled=true) //Pass
	public void createUserWithonlyMandatoryFields() throws IOException {
	    test.set(extent.createTest("Verify User Creation with only Mandatory Fields"));
	      
	    String cuendpoint = "/admin/create-user";
	    String guendpoint = "/admin/users";
	    
	    HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		
		Map<String, Object> payload = new HashMap<>();
		payload.put("email", email);
		payload.put("empName", empName);
		payload.put("mobileNo", mobileNo);
		payload.put("password", password);
		payload.put("experience", experience);
		payload.put("role", role);
		payload.put("username", username);
				
		Response createuserRes =  RestUtils.postReq(payload,headers,cuendpoint);
		
		empId = createuserRes.jsonPath().getString("empId");
		
		System.out.println("Response Body: " + createuserRes.getBody().asPrettyString());
		
		System.out.println("Emp Id is: " +empId);
		RestUtils.validateSchema(createuserRes, FileConstants.CreateUser_Schema_PATH);
	    
		Response getusers = RestUtils.getReq(headers, guendpoint);
		
		List<String> allEmpIds = getusers.jsonPath().getList("empId");

//		System.out.println("All Employee IDs: " + allEmpIds);

		// Assert that the empId is in the list
		assertTrue(allEmpIds.contains(empId), "New User with empId " + empId + " is created");
		    	    
	}
	@Test(enabled=true)	//Failing: getting 500 when mandatory field is missing in a payload.
	public void createUserWithoutPassword() throws IOException {
	    test.set(extent.createTest("Verify User Creation withour password"));
	      
	    String cuendpoint = "/admin/create-user";
	     
	    HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
			
				
		Map<String, Object> payload = new HashMap<>();
		payload.put("department", department);
		payload.put("designation", designation);
		payload.put("dob", dob);
		payload.put("email", email);
		payload.put("empName", empName);
		payload.put("mobileNo", mobileNo);
		payload.put("role", role);
		payload.put("username", username);
		payload.put("experience", experience);
				
		Response createuserRes =  RestUtils.postReq(payload,headers,cuendpoint);
		
		createuserRes.then().statusCode(400); 
		String message = createuserRes.jsonPath().getString("message");
		assertTrue(message.contains("password"));	    	    
	}
	
	@Test(enabled=true)	//Failing: getting 500 when mandatory field is missing in a payload.
	public void createUserWithoutUsername() throws IOException {
	    test.set(extent.createTest("Verify User Creation withour Username"));
	      
	    String cuendpoint = "/admin/create-user";
	     
	    HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
			
				
		Map<String, Object> payload = new HashMap<>();
		payload.put("department", department);
		payload.put("designation", designation);
		payload.put("dob", dob);
		payload.put("email", email);
		payload.put("empName", empName);
		payload.put("mobileNo", mobileNo);
		payload.put("role", role);
		payload.put("password", password);
		payload.put("experience", experience);
				
		Response createuserRes =  RestUtils.postReq(payload,headers,cuendpoint);
		
		createuserRes.then().statusCode(400); 
		String message = createuserRes.jsonPath().getString("message");
		assertTrue(message.contains("username"));	    	    
	}
	
	
	@Test(enabled=true)	//Failing: getting 500 when mandatory field is missing in a payload.
	public void createUserWithoutEmail() throws IOException {
	    test.set(extent.createTest("Verify User Creation withour Email"));
	      
	    String cuendpoint = "/admin/create-user";
	     
	    HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
			
				
		Map<String, Object> payload = new HashMap<>();
		payload.put("department", department);
		payload.put("designation", designation);
		payload.put("dob", dob);
		payload.put("username", username);
		payload.put("empName", empName);
		payload.put("mobileNo", mobileNo);
		payload.put("role", role);
		payload.put("password", password);
		payload.put("experience", experience);
				
		Response createuserRes =  RestUtils.postReq(payload,headers,cuendpoint);
		
		createuserRes.then().statusCode(400); 
		String message = createuserRes.jsonPath().getString("message");
		assertTrue(message.contains("email"));	    	    
	}
	
	@Test(enabled=true)	//Failing: getting 500 when mandatory field is missing in a payload.
	public void createUserWithoutMobile() throws IOException {
	    test.set(extent.createTest("Verify User Creation withour Mobile"));
	      
	    String cuendpoint = "/admin/create-user";
	     
	    HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
			
				
		Map<String, Object> payload = new HashMap<>();
		payload.put("department", department);
		payload.put("designation", designation);
		payload.put("dob", dob);
		payload.put("username", username);
		payload.put("empName", empName);
		payload.put("email", email);
		payload.put("role", role);
		payload.put("password", password);
		payload.put("experience", experience);
				
		Response createuserRes =  RestUtils.postReq(payload,headers,cuendpoint);
		
		createuserRes.then().statusCode(400); 
		String message = createuserRes.jsonPath().getString("message");
		assertTrue(message.contains("mobile"));	    	    
	}
	
	
	@Test(priority = 1,enabled=true)
	public void createUserWithCreatedUser() throws IOException {
	    test.set(extent.createTest("Verify User Creation with login using created user"));
	      
	    String cuendpoint = "/admin/create-user";
	   	    
	    HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		
		CreateUser_POJO cuP = new CreateUser_POJO(department, designation, dob, email, empName, experience,
													mobileNo, role, username, password);
		
		Response createuserRes =  RestUtils.postReq(headers, cuP, cuendpoint);
		
		 String baseUri = prop.getProperty("baseURL");
		 Response loginResponse = RestAssured
	                .given()
	                .auth().preemptive().basic(username, password)
	                .contentType(ContentType.JSON)
	                .when()
	                .post("/login");
	
	    System.out.println("Login successful :" +loginResponse.asPrettyString());
				    	    
	}
	
	@Test(priority = 1,enabled=true)  // pass
	public void deleteUser() throws IOException {
	    test.set(extent.createTest("Verify User deletion"));
	      
	    String cuendpoint = "/admin/create-user";
	    String delendpoint = "/admin/user";
	    String guendpoint = "/admin/users";
	    
	    HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		
		CreateUser_POJO cuP = new CreateUser_POJO(department, designation, dob, email, empName, experience,
													mobileNo, role, username, password);
		
		Response createuserRes =  RestUtils.postReq(headers, cuP, cuendpoint);
		
		empId = createuserRes.jsonPath().getString("empId");
		
		System.out.println("Employee id is: "+empId);
		
		Response getusers = RestUtils.getReq(headers, guendpoint);
		
		List<String> allEmpIds = getusers.jsonPath().getList("empId");

    	assertTrue(allEmpIds.contains(empId), "New User with empId " + empId + " is created");
    	
    	Map<String, String> queryParams = new HashMap<>();
    	queryParams.put("userId", empId);
    	
    	HashMap<String, String> headersd = new HashMap<>();
		
    	RestAssured.responseSpecification = null;
    	Response deleteuserRes = RestUtils.deleteReq(headersd, delendpoint, queryParams, 204);
    	
    	assertEquals("text/plain;charset=UTF-8", deleteuserRes.getHeader("Content-Type"));
    	
    	System.out.println("Response delete: " +deleteuserRes.asPrettyString() +deleteuserRes.getStatusCode());
    	Response getdelusers = RestUtils.getReq(headers, guendpoint);
		
		List<String> allEmpId = getdelusers.jsonPath().getList("empId");

    	assertFalse(allEmpIds.contains(empId), "New User with empId " + empId + " is created");
	}
	
	@Test(enabled=true)  
	public void deletedeletedUser() throws IOException {
	    test.set(extent.createTest("Verify deletion of deleted user"));
	      
	    String cuendpoint = "/admin/create-user";
	    String delendpoint = "/admin/user";
	    String guendpoint = "/admin/users";
	    
	    HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		
		CreateUser_POJO cuP = new CreateUser_POJO(department, designation, dob, email, empName, experience,
													mobileNo, role, username, password);
		
		Response createuserRes =  RestUtils.postReq(headers, cuP, cuendpoint);
		
		empId = createuserRes.jsonPath().getString("empId");
		
		System.out.println("Employee id is: "+empId);
		
		Response getusers = RestUtils.getReq(headers, guendpoint);
		
		List<String> allEmpIds = getusers.jsonPath().getList("empId");

    	assertTrue(allEmpIds.contains(empId), "New User with empId " + empId + " is created");
    	
    	Map<String, String> queryParams = new HashMap<>();
    	queryParams.put("userId", empId);
    	
    	HashMap<String, String> headersd = new HashMap<>();
		
    	RestAssured.responseSpecification = null;
    	Response deleteuserRes = RestUtils.deleteReq(headersd, delendpoint, queryParams, 204);
    	
    	assertEquals("text/plain;charset=UTF-8", deleteuserRes.getHeader("Content-Type"));
    	
       	Response getdelusers = RestUtils.getReq(headers, guendpoint);
		
		List<String> allEmpId = getdelusers.jsonPath().getList("empId");

    	assertFalse(allEmpId.contains(empId), "New User with empId " + empId + " is deleted");
    	
    	Response deletedeleteduserRes = RestUtils.deleteReq(headersd, delendpoint, queryParams, 400);
    	
       	String errorMessage = deletedeleteduserRes.getBody().asString();
       	assertEquals("User Not Found", errorMessage);
    	System.out.println("Status code as expected");
    	
	}
	
	
	@Test(enabled=true)  // pass
	public void deleteuser_widoutuserid() throws IOException {
	    test.set(extent.createTest("Verify deletion of user without providing userid"));
	      
	    String cuendpoint = "/admin/create-user";
	    String delendpoint = "/admin/user";
	    String guendpoint = "/admin/users";
	    
	    HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		
		CreateUser_POJO cuP = new CreateUser_POJO(department, designation, dob, email, empName, experience,
													mobileNo, role, username, password);
		
		Response createuserRes =  RestUtils.postReq(headers, cuP, cuendpoint);
		
		empId = createuserRes.jsonPath().getString("empId");
		
		System.out.println("Employee id is: "+empId);
		
		Response getusers = RestUtils.getReq(headers, guendpoint);
		
		List<String> allEmpIds = getusers.jsonPath().getList("empId");

    	assertTrue(allEmpIds.contains(empId), "New User with empId " + empId + " is created");
    	
    	Map<String, String> queryParams = new HashMap<>();
    	
    	HashMap<String, String> headersd = new HashMap<>();
		
    	Response deleteuserRes = RestUtils.deleteReq(headersd, delendpoint, queryParams, 500);
    	RestUtils.validateSchema(deleteuserRes, FileConstants.DeleteUser_Schema_PATH);
	      	   
	}
	
	@Test(enabled=true)  // pass
	public void getallUsers() throws IOException {
	    test.set(extent.createTest("Verify all users are displayed"));
	    
	    String guendpoint = "/admin/users";
	    
	    HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");	
		Response getusers = RestUtils.getReq(headers, guendpoint);
//		RestUtils.validateSchema(getusers, FileConstants.GetUser_Schema_PATH);
	      	   
	}
	
	@Test(enabled=true)  // pass
	public void getallUserCount() throws IOException {
	    test.set(extent.createTest("Verify all user count is displayed"));
	    
	    String guendpoint = "/admin/users-count";
	    
	    HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");	
		Response getusers = RestUtils.getReq(headers, guendpoint);
		RestUtils.validateSchema(getusers, FileConstants.GetUser_Count_Schema_PATH);
	      	   
	}
	
	@Test(enabled=true)  // failing
	public void getUser_WrongEndpoint() throws IOException {
	    test.set(extent.createTest("Verify wrong endpoint"));
	    
	    String guendpoint = "/admin/users-counts";
	    
	    HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");	
		RestAssured.responseSpecification = null;
		Response getusers = RestUtils.getReq(headers, guendpoint, 404);
//		RestUtils.validateSchema(getusers, FileConstants.Schema_PATH); // add proper schema path
	      	   
	}
	
	@Test(enabled=true)  // pass
	public void pagination() throws IOException {
	    test.set(extent.createTest("Verify pagination"));
	    
	    String gupendpoint = "/admin/users-paginated";
	    
	    HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");	
		Response getuserspage = RestUtils.getReq(headers, gupendpoint);
		RestUtils.validateSchema(getuserspage, FileConstants.GetUser_Pagination_Schema_PATH);
	      	   
	}
	
}
