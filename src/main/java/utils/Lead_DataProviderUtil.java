package utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

import constants.FileConstants;

public class Lead_DataProviderUtil {
	
	@DataProvider(name = "createLeadwithOptionalFeilds")
	public Object[][] getLeadData_optional() {
		String excelPath = "src/main/java/testData/LeadTestData.xlsx";
		String sheetName = "leadDataWithOptionalFields";

		List<Map<String, String>> dataList = ExcelUtils.getTestData(excelPath, sheetName);

		Object[][] data = new Object[dataList.size()][1];
		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}
		return data;
	}
	
	@DataProvider(name = "createLeadwithAllOptionalFeildsExceptEmail")
	public Object[][] getLeadData_noEmail() {
		String excelPath = "src/main/java/testData/LeadTestData.xlsx";
		String sheetName = "leadDataWithoutEmail";

		List<Map<String, String>> dataList = ExcelUtils.getTestData(excelPath, sheetName);

		Object[][] data = new Object[dataList.size()][1];
		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}
		return data;
	}
	
	@DataProvider(name = "createLeadwithAllOptionalFeildsExceptSecondaryEmail")
	public Object[][] getLeadData_noSecEmail() {
		String excelPath = "src/main/java/testData/LeadTestData.xlsx";
		String sheetName = "leadDataWithoutSecEmail";

		List<Map<String, String>> dataList = ExcelUtils.getTestData(excelPath, sheetName);

		Object[][] data = new Object[dataList.size()][1];
		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}
		return data;
	}
	@DataProvider(name = "createLeadwithNoAddressFeilds")
	public Object[][] getLeadData_noAddressFeilds() {
		String excelPath = "src/main/java/testData/LeadTestData.xlsx";
		String sheetName = "leadDatawithoutAddress";

		List<Map<String, String>> dataList = ExcelUtils.getTestData(excelPath, sheetName);

		Object[][] data = new Object[dataList.size()][1];
		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}
		return data;
	}
	@DataProvider(name = "createLeadwithNoWebSiteFeild")
	public Object[][] getLeadData_noWebsiteFeild() {
		String excelPath = "src/main/java/testData/LeadTestData.xlsx";
		String sheetName = "leaddataWithoutWebsite";

		List<Map<String, String>> dataList = ExcelUtils.getTestData(excelPath, sheetName);

		Object[][] data = new Object[dataList.size()][1];
		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}
		return data;
	}
	
	
	@DataProvider(name = "createLeadwithNoDescFeild")
	public Object[][] getLeadData_noDescFeild() {
		String excelPath = "src/main/java/testData/LeadTestData.xlsx";
		String sheetName = "leaddatawithoutDesc";

		List<Map<String, String>> dataList = ExcelUtils.getTestData(excelPath, sheetName);

		Object[][] data = new Object[dataList.size()][1];
		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}
		return data;
	}
	
	@DataProvider(name = "LeadMandatoryFeild")
	public Object[][] getLeadData() {
		String excelPath = "src/main/java/testData/LeadTestData.xlsx";
		String sheetName = "LeadMandatoryFeilds";

		List<Map<String, String>> dataList = ExcelUtils.getTestData(excelPath, sheetName);

		Object[][] data = new Object[dataList.size()][1];
		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}
		return data;
	}
}