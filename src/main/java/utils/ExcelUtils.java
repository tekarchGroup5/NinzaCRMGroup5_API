package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	private Workbook workbook;

	public static List<Map<String, String>> getTestData(String filePath, String sheetName) {
		List<Map<String, String>> allData = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(filePath);
				Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			Row headerRow = sheet.getRow(0);
			int rowCount = sheet.getPhysicalNumberOfRows();
			int colCount = headerRow.getPhysicalNumberOfCells();

			for (int i = 1; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				Map<String, String> rowData = new LinkedHashMap<>();
				for (int j = 0; j < colCount; j++) {
					Cell headerCell = headerRow.getCell(j);
					Cell valueCell = row.getCell(j);

					String key = headerCell.getStringCellValue();
					String value = getCellValueAsString(valueCell);
					rowData.put(key, value);
				}
				allData.add(rowData);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return allData;
	}

	private static String getCellValueAsString(Cell cell) {
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString(); // Customize date format if needed
			} else {
				double value = cell.getNumericCellValue();
				return String.valueOf(value % 1 == 0 ? (int) value : value);
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}


	public ExcelUtils(String excelPath) throws IOException {
		FileInputStream fis = new FileInputStream(excelPath);
		workbook = new XSSFWorkbook(fis);
	}

	public String getCellData(String sheetName, int rowNum, int colNum) {
		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rowNum);
		Cell cell = row.getCell(colNum);
		return cell.toString();
	}

	public int getRowCount(String sheetName) {
		Sheet sheet = workbook.getSheet(sheetName);
		return sheet.getPhysicalNumberOfRows();
	}

	public int getColumnCount(String sheetName) {
		Sheet sheet = workbook.getSheet(sheetName);
		return sheet.getRow(0).getPhysicalNumberOfCells();
	}

	public void close() throws IOException {
		workbook.close();
	}

	/**
	 * Retrieves a cell value by column name for a specific row
	 */
	public String getCellValueByColumnName(String sheetName, int rowNum, String columnName) {
		Sheet sheet = workbook.getSheet(sheetName);
		Row headerRow = sheet.getRow(0);
		int colNum = -1;
		for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
			if (headerRow.getCell(i).toString().trim().equalsIgnoreCase(columnName.trim())) {
				colNum = i;
				break;
			}
		}
		if (colNum == -1) {
			throw new IllegalArgumentException("Column '" + columnName + "' not found in sheet '" + sheetName + "'");
		}
		Row row = sheet.getRow(rowNum);
		Cell cell = row.getCell(colNum);
		return cell != null ? cell.toString() : "";
	}

	/**
	 * Returns all rows as a List<Map<String, String>>
	 */
	public List<Map<String, String>> getAllRowsAsMap(String sheetName) {
		Sheet sheet = workbook.getSheet(sheetName);
		Row headerRow = sheet.getRow(0);
		int totalRows = sheet.getPhysicalNumberOfRows();
		int totalCols = headerRow.getPhysicalNumberOfCells();

		List<Map<String, String>> allRows = new ArrayList<>();

		for (int i = 1; i < totalRows; i++) {
			Row row = sheet.getRow(i);
			if (row == null)
				continue;

			Map<String, String> rowData = new HashMap<>();
			for (int j = 0; j < totalCols; j++) {
				String key = headerRow.getCell(j).toString().trim();
				String value = row.getCell(j) != null ? row.getCell(j).toString().trim() : "";
				rowData.put(key, value);
			}
			allRows.add(rowData);
		}
		return allRows;
	}

	/**
	 * Returns rows filtered by a specific scenario in the "TestScenario" column
	 */
	public List<Map<String, String>> getRowsByScenario(String sheetName, String scenario) {
		Sheet sheet = workbook.getSheet(sheetName);
		Row headerRow = sheet.getRow(0);
		int totalRows = sheet.getPhysicalNumberOfRows();
		int totalCols = headerRow.getPhysicalNumberOfCells();

		List<Map<String, String>> filteredRows = new ArrayList<>();

		int scenarioColIndex = -1;
		for (int i = 0; i < totalCols; i++) {
			if (headerRow.getCell(i).toString().trim().equalsIgnoreCase("TestScenario")) {
				scenarioColIndex = i;
				break;
			}
		}
		if (scenarioColIndex == -1) {
			throw new RuntimeException("TestScenario column not found in sheet: " + sheetName);
		}

		for (int i = 1; i < totalRows; i++) {
			Row row = sheet.getRow(i);
			if (row == null)
				continue;

			Cell scenarioCell = row.getCell(scenarioColIndex);
			if (scenarioCell != null && scenario.equalsIgnoreCase(scenarioCell.toString().trim())) {
				Map<String, String> rowData = new HashMap<>();
				for (int j = 0; j < totalCols; j++) {
					String key = headerRow.getCell(j).toString().trim();
					String value = row.getCell(j) != null ? row.getCell(j).toString().trim() : "";
					rowData.put(key, value);
				}
				filteredRows.add(rowData);
			}
		}
		return filteredRows;
	}
	public static Map<String, String> getTestDataByRowIndex(String filePath, String sheetName, int rowIndex) {
	    List<Map<String, String>> allData = getTestData(filePath, sheetName);
	    if (rowIndex < allData.size()) {
	        return allData.get(rowIndex);
	    } else {
	        throw new RuntimeException("Invalid row index: " + rowIndex);
	    }
	}

}

