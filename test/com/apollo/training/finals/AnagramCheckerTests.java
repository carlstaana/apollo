package com.apollo.training.finals;

import static org.junit.Assert.*;

import org.junit.Test;

public class AnagramCheckerTests {

	@Test
	public void test() {
		AnagramChecker anagram = new AnagramChecker();
		String input1 = "Desperation";
		String input2 = "A Rope Ends It";
		assertTrue(anagram.check(input1, input2));
		
		input1 = "A gentleman";
		input2 = "Elegant man";
		assertTrue(anagram.check(input1, input2));
		
		input1 = "Clint Eastwood";
		input2 = "Old west action";
		assertTrue(anagram.check(input1, input2));
	}

}
