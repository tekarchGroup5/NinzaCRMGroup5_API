package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.math3.stat.inference.TestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import api_POJOS.CreateLeadClassic_POJO;

public class TestUtils_Lead {
	
	private static ObjectMapper mapper = new ObjectMapper();

    // Generic method to get a lead payload by key
    public static CreateLeadClassic_POJO getLeadPayload(String key) throws IOException {
        InputStream is = TestUtils_Lead.class.getClassLoader()
                .getResourceAsStream("api_testData/leadPayloads.json");

        if (is == null) throw new IOException("File not found: leadTestData.json");

        Map<String, Map<String, Object>> allData = mapper.readValue(is, Map.class);
        Map<String, Object> payloadMap = allData.get(key);

        return mapper.convertValue(payloadMap, CreateLeadClassic_POJO.class);
    }
}


