package com.apollo.training.book.chapter4;

import static org.junit.Assert.*;

import org.junit.Test;

public class DigitExtractorTests {

	@Test
	public void test() {
		DigitExtractor digitExtractor = new DigitExtractor(16384);
		
		int digit = digitExtractor.nextDigit();
		
		assertEquals(4, digit);
		
		digit = digitExtractor.nextDigit();
		digit = digitExtractor.nextDigit();
		digit = digitExtractor.nextDigit();
		digit = digitExtractor.nextDigit();
		
		assertEquals(1, digit);
	}

}
