package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import constants.FileConstants;

public class FileUtils {

	public static String readLoginPropertiesFile(String key) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(new FileReader(FileConstants.LOGIN_TEST_DATA_FILE_PATH));
		return p.getProperty(key);
	}

	public static String readOpportunitiesPropertiesFile(String key) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(new FileReader(FileConstants.OPPORTUNITY_PROPERTIES_FILE_PATH));
		return p.getProperty(key);
	}

	public static String readHomePropertiesFile(String key) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(new FileReader(FileConstants.HOME_TEST_DATA_FILE_PATH));
		return p.getProperty(key);
	}	
	
	public static String readCampaignPropertiesFile(String key) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(new FileReader(FileConstants.CRM_CAMPAIGN_TESTDATA_FILE_PATH));
		return p.getProperty(key);
	}

	}

