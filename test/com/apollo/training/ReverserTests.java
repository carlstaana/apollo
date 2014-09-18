package com.apollo.training;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReverserTests {

	@Test
	public void test() {
		String input = "This is a hat";
		String expected = "hat a is This";
		
		Reverser reverser = new Reverser();
		
		String actual = reverser.process(input);
		assertEquals(expected, actual);
		
		input = "The quick brown fox";
		expected = "fox brown quick The";
		
		actual = reverser.process(input);
		assertEquals(expected, actual);
	}

}
