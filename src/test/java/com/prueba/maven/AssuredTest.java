package com.prueba.maven;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.prueba.maven.junit_extension.RegisterTagExtension;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;


@ExtendWith(RegisterTagExtension.class)
@DisplayName("Redmine project api test")
public class AssuredTest {
	
	Header contentType = new Header("Content-Type", "application/json");
	Header redmineApiKey = new Header("X-Redmine-API-Key", "c278b5e3cca993d48a760aae98c8c81e108bb64f");
	Headers headers = new Headers(contentType, redmineApiKey);
	String urlBase = "http://192.168.64.2";
	String endPoint = "/projects";
	String dataType = ".json";
	
	@Test
	@Tag("Working")
	public void getProjectList(){
		given().
			headers(headers).
		when().
			get(urlBase + endPoint + dataType).
		then().
			statusCode(200).
			contentType(ContentType.JSON).
			body("limit", equalTo(25));
	}
	
	@ParameterizedTest
	@CsvSource({
        "20, Angela Gonzalez MD, angela-gonzalez-md, 1",
        "19, test proyect, test-proyect, 1"
	})
	@Tag("Working")
	public void getExistingProjectByID(int id, String name, String identifier, int status){
		given().
			headers(headers).
		when().
			get(urlBase + endPoint+ "/"+ id + dataType).
		then().
			statusCode(200).
			contentType(ContentType.JSON).
			assertThat().
				body("project.name", equalTo(name)).
				body("project.identifier", equalTo(identifier)).
				body("project.status", equalTo(status));
	}

	@ParameterizedTest
	@CsvSource({
        "2",
        "941"
	})
	@Tag("NotImplementedYet")
	public void getNonExistingProjectByID(int id){
		given().
			headers(headers).
		when().
			get(urlBase + endPoint+ "/"+ id + dataType).
		then().
			statusCode(404).
			contentType(ContentType.JSON);
	}
	
	@ParameterizedTest
	@CsvSource({
        "Test Project, test_project_1, this is my description"
	})
	@Tag("NotImplementedYet")
	@Tag("Working")
	public void createExistedProject(String name, String identifier, String description){
		given().
			headers(headers).
		when().
			param("project.name", name).
			param("project.identifier", identifier).
			param("project.description", description).
			post(urlBase + endPoint + dataType).
		then().
			statusCode(400);
	}
	
}
