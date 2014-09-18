package com.apollo.training.set4.calc;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CalculatorEngineTests {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testAdd() throws Exception {
		// good test
		Operation op = new Add();	// make use of the method compute in Add class using Operation interface - Polymorphism
		BigDecimal actual = op.compute(new BigDecimal("5"), new BigDecimal("5"));
		BigDecimal expected = new BigDecimal("10");
		assertEquals(expected, actual);
		// bad test
		actual = op.compute(actual, new BigDecimal("7"));
		expected = new BigDecimal("20");
		assertNotEquals(expected, actual);
		// expected exception test
		exception.expect(OperationException.class);
		actual = op.compute(actual, null);
	}
	
	@Test
	public void testSubtract() throws Exception {
		// good test
		Operation op = new Subtract();
		BigDecimal actual = op.compute(new BigDecimal("15"), new BigDecimal("5"));
		BigDecimal expected = new BigDecimal("10");
		assertEquals(expected, actual);
		// bad test
		actual = op.compute(actual, new BigDecimal("-1"));
		expected = new BigDecimal("9");
		assertNotEquals(expected, actual);
		// expected exception test
		exception.expect(OperationException.class);
		actual = op.compute(actual, null);
	}
	
	@Test
	public void testMultiply() throws Exception {
		// good test
		Operation op = new Multiply();
		BigDecimal actual = op.compute(new BigDecimal(3), new BigDecimal(3));
		BigDecimal expected = new BigDecimal(9);
		assertEquals(expected, actual);
		// bad test
		actual = op.compute(actual, new BigDecimal(-1));
		expected = new BigDecimal(9);
		assertNotEquals(expected, actual);
		// expected exception test
		exception.expect(OperationException.class);
		actual = op.compute(actual, null);
	}
	
	@Test
	public void testDivide() throws Exception {
		// good test
		Operation op = new Divide();
		BigDecimal actual = op.compute(new BigDecimal("100"), new BigDecimal("2"));
		BigDecimal expected = new BigDecimal("50");
		assertEquals(expected, actual);
		// bad test
		actual = op.compute(actual, new BigDecimal("5"));
		expected = new BigDecimal("45");
		assertNotEquals(expected, actual);
		// suppose I wanted to divide by 0
		exception.expect(OperationException.class);
		actual = op.compute(actual, new BigDecimal("0"));
		// suppose I have entered NULL
		exception.expect(OperationException.class);
		actual = op.compute(actual, null);
	}
	
	@Test
	public void testSquareRoot() throws Exception {
		// good test
		Operation op = new SquareRoot();
		BigDecimal actual = op.compute(null, new BigDecimal("16"));
		BigDecimal expected = new BigDecimal("4.0");
		assertEquals(expected, actual);
		// bad test
		actual = op.compute(null, actual);
		expected = new BigDecimal("1");
		assertNotEquals(expected, actual);
		// suppose I have entered a negative value
		exception.expect(OperationException.class);
		actual = op.compute(null, new BigDecimal(-5));
	}

	@Test
	public void testPercent() throws Exception {
		// good test
		Operation op = new Percent();
		BigDecimal actual = op.compute(null, new BigDecimal("20"));
		BigDecimal expected = new BigDecimal("0.2");
		assertEquals(expected, actual);
		// bad test
		actual = op.compute(null, actual);
		expected = new BigDecimal("0.05");
		assertNotEquals(expected, actual);
	}
	
	@Test
	public void testFraction() throws Exception {
		// good test
		Operation op = new Fraction();
		BigDecimal actual = op.compute(null, new BigDecimal("2"));
		BigDecimal expected = new BigDecimal("0.5");
		assertEquals(expected, actual);
		// bad test
		actual = op.compute(null, actual);
		expected = new BigDecimal("1");
		assertNotEquals(expected, actual);
		// suppose I have entered zero
		exception.expect(OperationException.class);
		actual = op.compute(null, new BigDecimal("0"));
	}
}
