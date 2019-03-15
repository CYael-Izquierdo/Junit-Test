package com.prueba.maven;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;


public class ApiTest {
	
	// El método @BeforeAll debe ser estático, si no, el codigo no compilará
	@BeforeAll
	static void setup() {
	    System.out.println("@BeforeAll - executes once before all test methods in this class");
	}
	
	@BeforeEach
	void init() {
	    System.out.println("@BeforeEach - executes before each test method in this class");
	}	 
	
	@DisplayName("Single test successful")
	@Test
	void testSingleSuccessTest() {
	    assertEquals("hola", "hola");
	}

	@DisplayName("Single test failed")
	@Test
	void testSingleFailTest() {
	    assertEquals("hola", "hola");
	}
	
	@AfterEach
	void tearDown() {
	    System.out.println("@AfterEach - executed after each test method.");
	}
	 
	@AfterAll
	static void done() {
	    System.out.println("@AfterAll - executed after all test methods.");
	}
}
