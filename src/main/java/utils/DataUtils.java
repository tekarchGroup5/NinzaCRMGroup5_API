package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import api_POJOS.OpportunitiesPojo;
import constants.FileConstants;


public class DataUtils {
	
	private static JsonNode root;

	 // 🔹 Read entire file as String (useful for raw payloads)
	public static String readJsonFileToString(String path) throws IOException {
		byte[] data = Files.readAllBytes(Paths.get(path));
		return new String(data);
	}

	public static Object getTestData(String jsonPath) throws IOException {
		String testData = DataUtils.readJsonFileToString(FileConstants.TEST_DATA_FILE_PATH);
		return JsonPath.read(testData, jsonPath);
	}
	

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            root = mapper.readTree(new File(FileConstants.OPP_API_TESTDATA_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data", e);
        }
    }
    
    // 🔹 Get value using Jayway JSONPath
    public static Object getByJsonPath(String jsonPath) throws IOException {
        String testData = readJsonFileToString(FileConstants.OPP_API_TESTDATA_FILE_PATH);
        return JsonPath.read(testData, jsonPath);
    }


    public static JsonNode getHeaders() {
        return root.get("headers");
    }

    public static String getEndpoint(String key) {
        return root.get("endpoints").get(key).asText();
    }

    public static JsonNode getTestCase(String testCaseId) {
        return root.get("testCases").get(testCaseId);
    }
    public static OpportunitiesPojo getTestCaseAsPojo(String testCaseId) {// CONVERT TEST CASE JSON -> POJO				
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.treeToValue(getTestCase(testCaseId), OpportunitiesPojo.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert test case to POJO: " + testCaseId, e);
        }
    }
}


