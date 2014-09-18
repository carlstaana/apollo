package com.apollo.training.set3;

import static org.junit.Assert.*;

import org.junit.Test;

import com.apollo.training.set3.calc1.CalculatorEngineImpl;

public class Calc1Tests {

	@Test
	public void testAdd() {
		// producing a good test
		CalculatorEngineImpl calc = new CalculatorEngineImpl();
		double currentTotal = 5;
		double input = 5;
		currentTotal = calc.add(currentTotal, calc.getDisplay(input)); // add the currentTotal and the output of the method getDisplay();
		double expected = 10;
		assertEquals(expected, currentTotal, 0);
		// producing a bad test
		input = 10;
		currentTotal = calc.add(currentTotal, calc.getDisplay(input));
		double badExpected = 30;
		assertNotEquals(badExpected, currentTotal);
	}
	
	@Test
	public void testSubtract() throws Exception {
		// good test
		CalculatorEngineImpl calc = new CalculatorEngineImpl();
		double currentTotal = 10;
		double input = 3;
		currentTotal = calc.subtract(currentTotal, calc.getDisplay(input));
		double expected = 7;
		assertEquals(expected, currentTotal, 0);
		// bad test
		input = 10;
		currentTotal = calc.subtract(currentTotal, calc.getDisplay(input));
		double badExpected = 3;
		assertNotEquals(badExpected, currentTotal);
	}
	
	
	@Test
	public void testMultiply() throws Exception {
		// good test
		CalculatorEngineImpl calc = new CalculatorEngineImpl();
		double currentTotal = 10;
		double input = 3;
		currentTotal = calc.multiply(currentTotal, calc.getDisplay(input));
		double expected = 30;
		assertEquals(expected, currentTotal, 0);
		// bad test
		input = 10;
		currentTotal = calc.multiply(currentTotal, calc.getDisplay(input));
		double badExpected = 40;
		assertNotEquals(badExpected, currentTotal);
	}
	
	@Test
	public void testDivide() throws Exception {
		// good test
		CalculatorEngineImpl calc = new CalculatorEngineImpl();
		double currentTotal = 100;
		double input = 2;
		currentTotal = calc.divide(currentTotal, calc.getDisplay(input));
		double expected = 50;
		assertEquals(expected, currentTotal, 0);
		// bad test
		input = 2;
		currentTotal = calc.divide(currentTotal, calc.getDisplay(input));
		double badExpected = 100;
		assertNotEquals(badExpected, currentTotal);
	}
	
	
	@Test
	public void testSquareRoot() throws Exception {
		// good test
		CalculatorEngineImpl calc = new CalculatorEngineImpl();
		double input = 100;
		double currentTotal = calc.sqRoot(calc.getDisplay(input));
		double expected = 10;
		assertEquals(expected, currentTotal, 0);
		// bad test
		currentTotal = calc.sqRoot(currentTotal);
		double badExpected = 2;
		assertNotEquals(badExpected, currentTotal);
	}
	
	@Test
	public void testPercent() throws Exception {
		// good test
		CalculatorEngineImpl calc = new CalculatorEngineImpl();
		double input = 50;
		double currentTotal = calc.percent(input);
		double expected = 0.5;
		assertEquals(expected, currentTotal, 0);
		// bad test
		currentTotal = calc.percent(currentTotal);
		double badExpected = 0.05;
		assertNotEquals(badExpected, currentTotal);
	}
	
	@Test
	public void testFraction() throws Exception {
		// good test
		CalculatorEngineImpl calc = new CalculatorEngineImpl();
		double input = 2;
		double currentTotal = calc.fraction(input);
		double expected = 0.5;
		assertEquals(expected, currentTotal, 0);
		// bad test
		currentTotal = calc.fraction(currentTotal);
		double badExpected = 1;
		assertNotEquals(badExpected, currentTotal);
	}
}
