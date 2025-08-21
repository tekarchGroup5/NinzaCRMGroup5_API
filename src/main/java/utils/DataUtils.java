package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.jayway.jsonpath.JsonPath;

import constants.FileConstants;

public class DataUtils {
	public static String readJsonFileToString(String path) throws IOException {
		byte[] data = Files.readAllBytes(Paths.get(path));
		return new String(data);
	}

	public static Object getTestData(String jsonPath) throws IOException {
		String testData = DataUtils.readJsonFileToString(FileConstants.TEST_DATA_FILE_PATH);
		return JsonPath.read(testData, jsonPath);
	}
}
