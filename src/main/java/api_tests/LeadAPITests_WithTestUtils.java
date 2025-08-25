package api_tests;

import java.io.IOException;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;

import api_POJOS.CreateLeadClassic_POJO;
import io.restassured.mapper.ObjectMapperType;
import utils.TestUtils_Lead;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LeadAPITests_WithTestUtils extends api_BaseTest {

    private ObjectMapper mapper = new ObjectMapper();

    // -------------------------------
    // Create Lead - Mandatory Fields
    // -------------------------------
    @Test(priority = 1, enabled = true)
    public void createLeadWithMandatoryFields() throws IOException {
        test.set(extent.createTest("Verify Lead Creation with Mandatory Fields"));

        String campaignId = prop.getProperty("campaignId");
        CreateLeadClassic_POJO leadPayload = TestUtils_Lead.getLeadPayload("leadMandatory");

        test.get().info("Request Payload: <pre>" +
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(leadPayload) + "</pre>");

        CreateLeadClassic_POJO createdLead = given()
                .queryParam("campaignId", campaignId)
                .body(leadPayload, ObjectMapperType.JACKSON_2)
            .when()
                .post("/lead")
            .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(CreateLeadClassic_POJO.class, ObjectMapperType.JACKSON_2);

        test.get().info("Response Payload: <pre>" +
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(createdLead) + "</pre>");

        // Assertions
        assert createdLead.getIndustry().equals(leadPayload.getIndustry()) : "Industry mismatch";
        assert createdLead.getLeadSource().equals(leadPayload.getLeadSource()) : "LeadSource mismatch";
        assert createdLead.getLeadStatus().equals(leadPayload.getLeadStatus()) : "LeadStatus mismatch";
        assert createdLead.getName().equals(leadPayload.getName()) : "Name mismatch";
        assert createdLead.getCompany().equals(leadPayload.getCompany()) : "Company mismatch";
        assert createdLead.getPhone().equals(leadPayload.getPhone()) : "Phone mismatch";
        assert createdLead.getLeadId() != null : "Lead ID should not be null";
        assert createdLead.getEmail() == null : "Email should be null";
        assert createdLead.getSecondaryEmail() == null : "Secondary Email should be null";
        assert createdLead.getWebsite() == null : "Website should be null";

        test.get().pass("Lead with mandatory fields created and validated successfully");
    }

    // -------------------------------
    // Create Lead - All Fields
    // -------------------------------
    @Test(priority = 2, enabled = true)
    public void createLeadWithAllFields() throws IOException {
        test.set(extent.createTest("Create Lead - All Fields"));

        String campaignId = prop.getProperty("campaignId");
        CreateLeadClassic_POJO leadPayload = TestUtils_Lead.getLeadPayload("leadAllFields");

        test.get().info("Request Payload: <pre>" +
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(leadPayload) + "</pre>");

        CreateLeadClassic_POJO createdLead = given()
                .queryParam("campaignId", campaignId)
                .body(leadPayload, ObjectMapperType.JACKSON_2)
            .when()
                .post("/lead")
            .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(CreateLeadClassic_POJO.class, ObjectMapperType.JACKSON_2);

        test.get().info("Response Payload: <pre>" +
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(createdLead) + "</pre>");

        assert createdLead.getEmail().equals(leadPayload.getEmail()) : "Email mismatch";
        assert createdLead.getWebsite().equals(leadPayload.getWebsite()) : "Website mismatch";
        assert createdLead.getNoOfEmployees() == leadPayload.getNoOfEmployees() : "NoOfEmployees mismatch";

        test.get().pass("Lead with all fields created and validated successfully");
    }

    // -------------------------------
    // Update Lead
    // -------------------------------
    @Test(priority = 3, enabled = true)
    public void updateLead() throws IOException {
        test.set(extent.createTest("Update Lead Test"));

        CreateLeadClassic_POJO lead = TestUtils_Lead.getLeadPayload("leadAllFields");
        String leadId = postLead(lead);

        CreateLeadClassic_POJO updatedFields = TestUtils_Lead.getLeadPayload("updatedLeadFields");
        lead.setCompany(updatedFields.getCompany());
        lead.setAnnualRevenue(updatedFields.getAnnualRevenue());
        lead.setPhone(updatedFields.getPhone());
        lead.setCity(updatedFields.getCity());

        test.get().info("Update Lead Request: <pre>" +
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lead) + "</pre>");

        CreateLeadClassic_POJO updatedLead = given()
                .queryParam("leadId", leadId)
                .queryParam("campaignId", prop.getProperty("campaignId"))
                .body(lead, ObjectMapperType.JACKSON_2)
            .when()
                .put("/lead")
            .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(CreateLeadClassic_POJO.class, ObjectMapperType.JACKSON_2);

        test.get().info("Update Lead Response: <pre>" +
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(updatedLead) + "</pre>");

        assert updatedLead.getLeadId().equals(leadId) : "Lead ID mismatch after update";
        assert updatedLead.getCompany().equals(updatedFields.getCompany()) : "Company not updated correctly";
        assert updatedLead.getAnnualRevenue() == updatedFields.getAnnualRevenue() : "Annual Revenue not updated correctly";
        assert updatedLead.getPhone().equals(updatedFields.getPhone()) : "Phone not updated correctly";
        assert updatedLead.getCity().equals(updatedFields.getCity()) : "City not updated correctly";

        test.get().pass("Lead updated successfully and validated");
    }

    // -------------------------------
    // Delete Lead
    // -------------------------------
    @Test(priority = 4, enabled = true)
    public void deleteLead() throws IOException {
        test.set(extent.createTest("Delete Lead Test"));

        CreateLeadClassic_POJO lead = TestUtils_Lead.getLeadPayload("leadAllFields");
        String leadId = postLead(lead);

        test.get().info("Delete Lead Request - leadId: " + leadId);

        given()
            .queryParam("leadId", leadId)
            .queryParam("campaignId", prop.getProperty("campaignId"))
        .when()
            .delete("/lead")
        .then()
            .log().all()
            .statusCode(204);

        test.get().pass("Lead deleted successfully");
    }

    // -------------------------------
    // Helper method to create lead and log request/response
    // -------------------------------
    private String postLead(CreateLeadClassic_POJO lead) {
        String campaignId = prop.getProperty("campaignId");

        try {
            test.get().info("POST Lead Request: <pre>" +
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lead) + "</pre>");
        } catch (Exception e) { e.printStackTrace(); }

        CreateLeadClassic_POJO createdLead = given()
                .queryParam("campaignId", campaignId)
                .body(lead, ObjectMapperType.JACKSON_2)
            .when()
                .post("/lead")
            .then()
                .statusCode(201)
                .extract()
                .as(CreateLeadClassic_POJO.class, ObjectMapperType.JACKSON_2);

        try {
            test.get().info("POST Lead Response: <pre>" +
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(createdLead) + "</pre>");
        } catch (Exception e) { e.printStackTrace(); }

        return createdLead.getLeadId();
    }

    // -------------------------------
    // Get All Leads
    // -------------------------------
    @Test(priority = 5, enabled = true)
    public void getAllLeads() {
        test.set(extent.createTest("Get All Leads"));

        Response response = given()
                .queryParam("page", 0)
                .queryParam("size", 20)
            .when()
                .get("/lead/all-leads")
            .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        test.get().info("Response Payload: <pre>" + response.asPrettyString() + "</pre>");
        test.get().pass("Get all leads API executed successfully");
    }

    // -------------------------------
    // Get Paginated Leads
    // -------------------------------
    @Test(priority = 6, enabled = true)
    public void getAllLeadsPaginated() {
        test.set(extent.createTest("Get All Leads - Paginated"));

        Response response = given()
                .queryParam("page", 0)
                .queryParam("size", 20)
            .when()
                .get("/lead/all")
            .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        test.get().info("Response Payload: <pre>" + response.asPrettyString() + "</pre>");
        test.get().pass("Paginated get all leads API executed successfully");
    }

    // -------------------------------
    // Get Lead Count
    // -------------------------------
    @Test(priority = 7, enabled = true)
    public void getLeadCount() {
        test.set(extent.createTest("Get Lead Count"));

        Response response = given()
            .when()
                .get("/lead/count")
            .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        test.get().info("Response Payload: <pre>" + response.asPrettyString() + "</pre>");
        test.get().pass("Lead count retrieved successfully: " + response.asString());
    }

    // -------------------------------
    // Create Lead - Mandatory Fields with Faker
    // -------------------------------
    @Test(priority = 8, enabled = true)
    public void createLeadWithMandatoryFieldsWithFaker() throws IOException {
        test.set(extent.createTest("Create Lead with Mandatory Fields using Faker"));

        String campaignId = prop.getProperty("campaignId");
        CreateLeadClassic_POJO leadPayload = randomDataGenerator.CreateLeadRandomTestData.generateRandomLead();
        leadPayload.setCampaign(null);
        leadPayload.setEmail(null);
        leadPayload.setSecondaryEmail(null);
        leadPayload.setWebsite(null);

        test.get().info("Request Payload: <pre>" +
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(leadPayload) + "</pre>");

        CreateLeadClassic_POJO createdLead = given()
                .queryParam("campaignId", campaignId)
                .body(leadPayload, ObjectMapperType.JACKSON_2)
            .when()
                .post("/lead")
            .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(CreateLeadClassic_POJO.class, ObjectMapperType.JACKSON_2);

        test.get().info("Response Payload: <pre>" +
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(createdLead) + "</pre>");

        test.get().pass("Lead with mandatory fields created and validated successfully using Faker");
    }
}