package com.apollo.training.book.chapter7;

import static org.junit.Assert.*;

import org.junit.Test;

public class PurseTests {

	@Test
	public void testAddCoin() {
		Purse purse = new Purse();
		
		purse.addCoin("Dime");
		purse.addCoin("Dime");
		purse.addCoin("Nickel");
		System.out.println(purse.toString());
	}
	
	@Test
	public void testReverse() throws Exception {
		Purse purse = new Purse();
		
		purse.addCoin("Dime");
		purse.addCoin("Dime");
		purse.addCoin("Nickel");
		System.out.println(purse.toString());
		
		purse.reverse();
		System.out.println(purse.toString());
	}
	
	@Test
	public void testTransfer() throws Exception {
		Purse purse = new Purse();
		
		purse.addCoin("Dime");
		purse.addCoin("Dime");
		purse.addCoin("Nickel");
		System.out.println(purse.toString());
		
		Purse otherPurse = new Purse();
		
		otherPurse.addCoin("Penny");
		otherPurse.addCoin("Quarter");
		otherPurse.addCoin("Dime");
		
		purse.transfer(otherPurse);
		System.out.println(purse.toString());
	}
	
	@Test
	public void testSameContents() throws Exception {
		Purse purse = new Purse();

		purse.addCoin("Dime");
		purse.addCoin("Dime");
		purse.addCoin("Nickel");
		System.out.println(purse.toString());

		Purse otherPurse = new Purse();

		otherPurse.addCoin("Penny");
		otherPurse.addCoin("Quarter");
		otherPurse.addCoin("Dime");

		assertFalse(purse.sameContents(otherPurse));
		
		Purse anotherPurse = purse;
		assertTrue(purse.sameContents(anotherPurse));
	}
	
	@Test
	public void testSameCoins() throws Exception {
		Purse purse = new Purse();
		purse.addCoin("Dime");
		purse.addCoin("Dime");
		purse.addCoin("Nickel");
		System.out.println(purse.toString());

		Purse otherPurse = new Purse();
		otherPurse.addCoin("Penny");
		otherPurse.addCoin("Quarter");
		otherPurse.addCoin("Dime");
		
		assertFalse(purse.sameCoins(otherPurse));
		
		Purse anotherPurse = new Purse();
		anotherPurse.addCoin("Dime");
		anotherPurse.addCoin("Nickel");
		anotherPurse.addCoin("Dime");
		
		assertTrue(purse.sameCoins(anotherPurse));
	}
}
