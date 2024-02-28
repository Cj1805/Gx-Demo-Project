package com.wellness.service;


import org.mockito.Mockito;
import com.wellness.serviceImpl.DemoClass;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestDemoClass {

	DemoClass demo = Mockito.mock(DemoClass.class);
	
	@BeforeAll
	public static void testReady() {
		System.out.println("Test has been started");
		
	}
	
	@AfterAll
	public static void testExecuted() {
		System.out.println("Test has been completed");
	}

	@Test
	public void testGreet() {

		Mockito.mockStatic(DemoClass.class);
		when(DemoClass.greet()).thenReturn("Hello World!");

		String result = DemoClass.greet();

		assertEquals("Hello World!", result);
	}

	@Test
	public void testCalculate() {

		when(DemoClass.calculate(2, 3)).thenReturn(6);
		int result = DemoClass.calculate(2, 3);

		assertEquals(6, result);
	}

	@Test
	public void testDisplay() {
		
		when(demo.display()).thenReturn("Welcome to Galaxe");

		String display = demo.display();
		assertEquals("Welcome to Galaxe", display);
	}
	
	@Test
	public void testCarStart() {
		when(demo.carStart()).thenReturn("Car Started");
		
		for(int i=0;i<3;i++) {
			String result = demo.carStart();
			assertEquals("Car Started", result);
		}
		
		verify(demo, times(3)).carStart();
	}

}
