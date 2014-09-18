package com.apollo.training.book.chapter3;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoachSimulation {

	@Test
	public void test() {
		RoachPopulation rp = new RoachPopulation(10);
		
		rp.breed();	// 20
		rp.spray(); // 18
		int numOfRoaches = rp.getRoaches(); // 18
		
		assertEquals(18, numOfRoaches);
		
		rp.breed(); // 36
		rp.spray(); // 33
		numOfRoaches = rp.getRoaches(); // 33
		
		assertEquals(33, numOfRoaches);
		
		rp.breed(); // 66
		rp.spray(); // 60
		numOfRoaches = rp.getRoaches(); // 60
		
		assertEquals(60, numOfRoaches);
	}

}
