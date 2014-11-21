package com.apollo.training;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("unused")
public class PrimeNumberFinderTests {

	@Test
	public void test() {
		PrimeNumberFinder prime = new PrimeNumberFinder();
		
		prime.findPrimes(1000);
	}

}
