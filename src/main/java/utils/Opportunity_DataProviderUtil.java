package utils;

import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

import constants.FileConstants;

public class Opportunity_DataProviderUtil {
	static String excelPath = FileConstants.OPPORTUNITY_TEST_DATA_UPLOAD_PATH;
	static String sheetName = "Opportunities";

	@DataProvider(name = "tc1Data")
	public static Object[][] getTC1Data() {
		Map<String, String> data = ExcelUtils.getTestDataByRowIndex(excelPath, sheetName, 0);
		return new Object[][] { { data } };
	}

	@DataProvider(name = "tc2Data")
	public static Object[][] getTC2Data() {
		Map<String, String> data = ExcelUtils.getTestDataByRowIndex(excelPath, sheetName, 1);
		return new Object[][] { { data } };
	}

	@DataProvider(name = "allOpportunitiesData")
	public static Object[][] getAllOpportunityData() {
		List<Map<String, String>> dataList = ExcelUtils.getTestData(excelPath, sheetName);

		// Send the entire list as one object
		return new Object[][] { { dataList } };
	}

	@DataProvider(name = "opportunityData")
	public Object[][] getOpportunityData() {

		List<Map<String, String>> dataList = ExcelUtils.getTestData(excelPath, sheetName);

		Object[][] data = new Object[dataList.size()][1];
		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}
		return data;
	}

	@DataProvider(name = "tc3Data")
	public static Object[][] getTC3Data() {
		Map<String, String> data = ExcelUtils.getTestDataByRowIndex(excelPath, sheetName, 2);
		return new Object[][] { { data } };
	}

	@DataProvider(name = "tc4Data")
	public static Object[][] getTC4Data() {
		Map<String, String> data = ExcelUtils.getTestDataByRowIndex(excelPath, sheetName, 3);
		return new Object[][] { { data } };
	}

	@DataProvider(name = "tc5Data")
	public static Object[][] getTC5Data() {
		Map<String, String> data = ExcelUtils.getTestDataByRowIndex(excelPath, sheetName, 5);
		return new Object[][] { { data } };
	}

	@DataProvider(name = "tc6Data")
	public static Object[][] getTC6Data() {
		Map<String, String> data = ExcelUtils.getTestDataByRowIndex(excelPath, sheetName, 6);
		return new Object[][] { { data } };
	}

	@DataProvider(name = "tc7Data")
	public static Object[][] getTC7Data() {
		Map<String, String> data = ExcelUtils.getTestDataByRowIndex(excelPath, sheetName, 7);
		return new Object[][] { { data } };
	}

	@DataProvider(name = "tc8Data")
	public static Object[][] getTC8Data() {
		Map<String, String> data = ExcelUtils.getTestDataByRowIndex(excelPath, sheetName, 8);
		return new Object[][] { { data } };
	}

	@DataProvider(name = "tc9Data")
	public static Object[][] getTC9Data() {
		Map<String, String> data = ExcelUtils.getTestDataByRowIndex(excelPath, sheetName, 9);
		return new Object[][] { { data } };
	}

}
