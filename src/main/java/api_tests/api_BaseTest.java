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
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>(); // ✅ fixed

    @BeforeClass
    public void configSetup() throws IOException {
        // Load ExtentReports singleton
        extent = ReportManager.getInstance();
        
        // Create ExtentTest for this class
        ExtentTest extentTest = extent.createTest(getClass().getSimpleName());
        test.set(extentTest);

        // Load properties
        prop = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("api_testData/api_config.properties")) {
            if (input == null) throw new RuntimeException("api_config.properties not found");
            prop.load(input);
        }

        String baseUri = prop.getProperty("baseURL");
        String username = prop.getProperty("username");
        String password = prop.getProperty("password");

        // RestAssured setup
        RestAssured.config = RestAssured.config()
            .objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.JACKSON_2));

        RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(baseUri)
            .setAuth(RestAssured.preemptive().basic(username, password))
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addFilter(new RequestLoggingFilter())
            .addFilter(new ResponseLoggingFilter())
            .build();

        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .expectHeader("Content-Type", "application/json")
            .build();

        RestAssured.requestSpecification = requestSpecification;
        RestAssured.responseSpecification = responseSpecification;
    }

    @AfterSuite
    public void tearDownReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}