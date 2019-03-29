package com.prueba.maven;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.maven.junit_extension.RegisterTagExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@ExtendWith(RegisterTagExtension.class)
public class ApiTest {

	// El método @BeforeAll debe ser estático, si no, el codigo no compilará
	@BeforeAll
	static void setup() {
	    System.out.println("@BeforeAll - executes once before all test methods in this class");
	}
	
	@BeforeEach
	void init(TestInfo info) {
	    System.out.println("@BeforeEach - executes before each test method in this class");
	    System.out.println(info.getTags());

	}	 
	
	@AfterEach
	void tearDown() {
	    System.out.println("@AfterEach - executed after each test method.");
	}
	 
	@AfterAll
	static void done() {
	    System.out.println("@AfterAll - executed after all test methods.");
	}
	
	
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<TESTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	
	@Test
	void asd(){
		assertEquals("as", "");
	}
	
	@Tag("Api")
	@Test
	void testRequest() throws IOException {			  
		URL url = new URL("http://192.168.64.2/projects.json");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("X-Redmine-API-Key", "c278b5e3cca993d48a760aae98c8c81e108bb64f");
		System.out.println("/////////////////////" + con.getResponseCode() + "/////////////////////");
		
		// GET RESPONSE DATA AND CONVERt TO MAP
		
		Map<String, Object> map = getResponseAsMap(con);
		
		System.out.println(map);
		ArrayList<Map<String, Object>> projects = (ArrayList<Map<String, Object>>) map.get("projects");
		
		System.out.println(projects.get(0).get("id"));
		System.out.println(projects.get(0).get("name"));
		System.out.println(projects.get(0).get("identifier"));
		System.out.println(projects.get(0).get("description"));
		System.out.println(projects.get(0).get("created_on"));
	  
	}
	
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<HELPERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	private Map<String, Object> getResponseAsMap(HttpURLConnection con) throws IOException{
		InputStream in = con.getInputStream();
		BufferedReader r = new BufferedReader(new InputStreamReader(in));
		StringBuffer buf = new StringBuffer();
		String line;
		while ((line = r.readLine())!=null) {
		  buf.append(line);
		} 

		Map<String,Object> map = convertJsonToMap(buf.toString());
		
		return map;
	}
	
	private Map<String, Object> convertJsonToMap(String json) {

        Map<String, Object> map = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try {

            // convert JSON string to Map
            map = mapper.readValue(json,new TypeReference<Map<String, Object>>() {});

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
