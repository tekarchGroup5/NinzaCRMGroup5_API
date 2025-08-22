package api_tests;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

public class CreateUserTest extends api_BaseTest{
	
	@Test(priority = 1,enabled=true)//Test is working
	public void createLeadWithMandatoryFields() throws IOException {
	    test.set(extent.createTest("Verify User Creation with Mandatory Fields"));
	    
	    HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
	    
	    
	}
}
