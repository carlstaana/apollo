package com.apollo.training;

import static org.junit.Assert.*;

import org.junit.Test;

public class OddSummerTests {

	@Test
	public void test() {
		OddSummer odd = new OddSummer();
		
		long result = odd.getSumOfOdds(1, 5);
		
		assertEquals(9, result);
	}

}
