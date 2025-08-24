package api_tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.ReportManager;

public class api_BaseTest {

	protected Properties prop;
	protected ExtentReports extent;
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<>(); // 


    @BeforeClass//Method runs once before any test methods in the current class.
    public void configSetup() throws IOException {
        // Load ExtentReports singleton
        extent = ReportManager.getInstance();//Get the singleton ExtentReports instance.
        ExtentTest extentTest = extent.createTest(getClass().getSimpleName());//Create a new test report entry using the class name as the test name.
        test.set(extentTest);//Store the test instance in the ThreadLocal variable.

        // Load properties
        prop = new Properties();//Stores key-value pairs from the config file (api_config.properties).
        try (InputStream input = getClass().getClassLoader()//Reads the properties file from the classpath.
                .getResourceAsStream("api_config.properties")) {
            if (input == null) throw new RuntimeException("api_config.properties not found");//Stores key-value pairs from the config file (api_config.properties).
            prop.load(input);
        }


		String baseUri = prop.getProperty("baseURL");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");

		// RestAssured setup
		RestAssured.config = RestAssured.config()
				.objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.JACKSON_2));


        RequestSpecification requestSpecification = new RequestSpecBuilder()//All Request will be using these settings
            .setBaseUri(baseUri)//
            .setAuth(RestAssured.preemptive().basic(username, password))//
            .addHeader("Content-Type", "application/json")//
          //  .addHeader("Accept", "application/json")//Adds Content-Type: application/json header.
            .addFilter(new RequestLoggingFilter())//Adds filters to log requests and responses to console/output.
            .addFilter(new ResponseLoggingFilter())//Adds filters to log requests and responses to console/output.
            .build();

        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .expectHeader("Content-Type", "application/json")
            .build();
//Sets these as the global default for all API requests and responses in tests.
        RestAssured.requestSpecification = requestSpecification;//Ensures all responses must have Content-Type: application/json.
        RestAssured.responseSpecification = responseSpecification;
    }
//After the whole test suite completes, this writes all logs to the ExtentReports file (flushes buffered data).
    @AfterSuite
    public void tearDownReport() {
        if (extent != null) {
            extent.flush();//writes everything to the report  
        }
    }

}
