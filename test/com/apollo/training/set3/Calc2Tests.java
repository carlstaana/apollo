package com.apollo.training.set3;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.apollo.training.set3.calc2.Add;
import com.apollo.training.set3.calc2.CalculatorEngine;
import com.apollo.training.set3.calc2.Divide;
import com.apollo.training.set3.calc2.Fraction;
import com.apollo.training.set3.calc2.Multiply;
import com.apollo.training.set3.calc2.Operation;
import com.apollo.training.set3.calc2.Percent;
import com.apollo.training.set3.calc2.SquareRoot;
import com.apollo.training.set3.calc2.Subtract;

public class Calc2Tests {

	@Test
	public void testAdd() {
		// good test
		System.out.println("Addition Test:");
		new CalculatorEngine();
		//Add add = new Add();
		Operation op = new Add(); // example of Polymorphism
		BigDecimal currentTotal = new BigDecimal(5);
		BigDecimal input = new BigDecimal(4);
		currentTotal = op.compute(currentTotal, input);	// use the implementation of compute inside Add class
		BigDecimal expected = new BigDecimal(9);
		assertEquals(expected, currentTotal);
		// bad test
		input = new BigDecimal(5);
		currentTotal = op.compute(currentTotal, input);
		BigDecimal badExpected = new BigDecimal(15);
		assertNotEquals(badExpected, currentTotal);
	}
	
	@Test
	public void testSubtract() throws Exception {
		// good test
		System.out.println("Subtraction Test:");
		new CalculatorEngine();
		Subtract sub = new Subtract();
		BigDecimal currentTotal = new BigDecimal(5);
		BigDecimal input = new BigDecimal(4);
		currentTotal = sub.compute(currentTotal, input);
		BigDecimal expected = new BigDecimal(1);
		assertEquals(expected, currentTotal);
		// bad test
		input = new BigDecimal(5);
		currentTotal = sub.compute(currentTotal, input);
		BigDecimal badExpected = new BigDecimal(-3);
		assertNotEquals(badExpected, currentTotal);
	}
	
	@Test
	public void testMultiply() throws Exception {
		// good test
		System.out.println("Multiplication Test:");
		new CalculatorEngine();
		Multiply mul = new Multiply();
		BigDecimal currentTotal = new BigDecimal(5);
		BigDecimal input = new BigDecimal(4);
		currentTotal = mul.compute(currentTotal, input);
		BigDecimal expected = new BigDecimal(20);
		assertEquals(expected, currentTotal);
		// bad test
		input = new BigDecimal(5);
		currentTotal = mul.compute(currentTotal, input);
		BigDecimal badExpected = new BigDecimal(25);
		assertNotEquals(badExpected, currentTotal);
	}
	
	@Test
	public void testDivide() throws Exception {
		// good test
		System.out.println("Division Test:");
		new CalculatorEngine();
		Divide div = new Divide();
		BigDecimal currentTotal = new BigDecimal(40);
		BigDecimal input = new BigDecimal(4);
		currentTotal = div.compute(currentTotal, input);
		BigDecimal expected = new BigDecimal(10);
		assertEquals(expected, currentTotal);
		// bad test
		input = new BigDecimal(5);
		currentTotal = div.compute(currentTotal, input);
		BigDecimal badExpected = new BigDecimal(5);
		assertNotEquals(badExpected, currentTotal);
	}
	
	@Test
	public void testSquareRoot() throws Exception {
		// good test
		System.out.println("Square Root Test:");
		new CalculatorEngine();
		SquareRoot sqroot = new SquareRoot();
		BigDecimal input = new BigDecimal(16);
		BigDecimal currentTotal = sqroot.compute(null, input);
		BigDecimal expected = new BigDecimal("4.0");
		assertEquals(expected, currentTotal);
		// bad test
		currentTotal = sqroot.compute(null, currentTotal);
		BigDecimal badExpected = new BigDecimal("1.0");
		assertNotEquals(badExpected, currentTotal);
	}

	@Test
	public void testPercent() throws Exception {
		// good test
		System.out.println("Percentage Test:");
		new CalculatorEngine();
		Percent perc = new Percent();
		BigDecimal input = new BigDecimal(50);
		BigDecimal currentTotal = perc.compute(null, input);
		BigDecimal expected = new BigDecimal(0.5);
		assertEquals(expected, currentTotal);
		// bad test
		currentTotal = perc.compute(null, currentTotal);
		BigDecimal badExpected = new BigDecimal(0.05);
		assertNotEquals(badExpected, currentTotal);
	}
	
	@Test
	public void testFraction() throws Exception {
		// good test
		System.out.println("Fraction Test:");
		new CalculatorEngine();
		Fraction frac = new Fraction();
		BigDecimal input = new BigDecimal(2);
		BigDecimal currentTotal = frac.compute(null, input);
		BigDecimal expected = new BigDecimal(0.5);
		assertEquals(expected, currentTotal);
		// bad test
		currentTotal = frac.compute(null, currentTotal);
		BigDecimal badExpected = new BigDecimal(1);
		assertNotEquals(badExpected, currentTotal);
	}
}
