package constants;

import utils.CommonUtils;

public class FileConstants {

	public static String ROOT_PATH = System.getProperty("user.dir");

	public static final String LOGIN_TEST_DATA_FILE_PATH = ROOT_PATH
			+ "/src/main/java/testData/crm_logintestdata.properties";
	public static final String HOME_TEST_DATA_FILE_PATH = ROOT_PATH + "/src/main/java/testData/hometestdata.properties";
	public static final String LEADTESTDATA_FILE_UPLOAD_PATH = ROOT_PATH + "/src/main/java/testData/LeadTestData.xlsx";
	public static final String OPPORTUNITY_TEST_DATA_UPLOAD_PATH = ROOT_PATH
			+ "/src/main/java/testData/OpportunityTestData.xlsx";
	public static final String TEST_PHOTO_UPLOAD_PATH = ROOT_PATH + "/src/main/resources/css.png";
	public static final String SCREENSHOTS_FOLDER_PATH = ROOT_PATH + "/test-output/html-report/"
			+ CommonUtils.getTimeStamp() + "_NinzaCRM.PNG";
	public static final String REPORTS_FILE_PATH = ROOT_PATH + "/test-output/html-report/" + CommonUtils.getTimeStamp()
			+ "_NinzaCRM_API.html";
	public static final String CRM_CAMPAIGN_TESTDATA_FILE_PATH = ROOT_PATH
			+ "/src/main/java/testData/crm_campaign_testdata.properties";
	public static final String OPPORTUNITY_PROPERTIES_FILE_PATH = ROOT_PATH
			+ "/src/main/java/testData/Opportunities.properties";
//	public static final String TEST_DATA_FILE_PATH = ROOT_PATH + "/src/main/java/testData/testData.json";
	//public static final String LOGIN_SCHEMA_FILE_PATH = ROOT_PATH + "/src/main/java/schemaValidations/loginResponseSchema.json";
	public static final String OPP_API_TESTDATA_FILE_PATH = ROOT_PATH

			+ "/src/main/java/api_testData/OpportunityTestData.json";
	public static final String TEST_DATA_FILE_PATH = System.getProperty("user.dir")+"/src/main/resources/api_testData/CampaignAPItestData.json";
	public static final String LOGIN_SCHEMA_FILE_PATH = System.getProperty("user.dir") + "/src/main/java/schemaValidations/campaignResponseSchema.json";
	public static final String CreateUser_Schema_PATH = ROOT_PATH + "/src/main/java/schemaValidations/CreateUser_Schema.json";


	
}
