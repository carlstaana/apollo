package com.apollo.training.set1;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

public class BirthdayCalculatorTests {

	@Test
	public void testGood() {
		String actual;
		String expected = "Monday";
		int input = 21;
		GregorianCalendar cal = new GregorianCalendar(1993, Calendar.MAY, 19);
		
		BirthdayCalculator birthCalc = new BirthdayCalculator(cal);
		actual = birthCalc.getWeekDay(input);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBad() throws Exception {
		GregorianCalendar cal = new GregorianCalendar(1993, Calendar.MAY, 19); // Set birthdate
		BirthdayCalculator birth = new BirthdayCalculator(cal);
		
		int input = 21;
		String actual = birth.getWeekDay(input);
		String expected = "Thursday";
		assertNotEquals(expected, actual);
	}

}
