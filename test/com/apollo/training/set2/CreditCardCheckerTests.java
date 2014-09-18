package com.apollo.training.set2;

import static org.junit.Assert.*;

import org.junit.Test;

public class CreditCardCheckerTests {

	// LAST DIGIT of CreditCard Number is the CHECK DIGIT
	// input a credit card number = 4358 9795
	// add the odd indexed numbers, right-to-left = 5 + 7 + 8 + 3 = 23
	// double the digits of the remaining numbers (right to left); 18 18 10 8
	// then add the digits(left to right) = 1+8+1+8+1+0+8 = 27
	// add the sums of the preceding steps 23 + 27 = 50
	// if the last digit of the sum is 0, the number is valid
	
	@Test
	public void test1() {
		boolean isValid;
		String creditCardnumber;
		
		CreditCardChecker ccChecker = new CreditCardChecker();
		creditCardnumber = "43589795";
		isValid = ccChecker.check(creditCardnumber);
		
		assertTrue(isValid);
	}
	
	@Test
	public void test2() throws Exception {
		boolean isValid;
		String creditCardnumber;
		
		CreditCardChecker ccChecker = new CreditCardChecker();
		creditCardnumber = "88452157";
		isValid = ccChecker.check(creditCardnumber);
		
		assertFalse(isValid);
	}

}
