package com.apollo.training;

import static org.junit.Assert.*;

import org.junit.Test;

public class BankAccountTests {

	@Test
	public void testDeposit() {
		BankAccount carlsAccount = new BankAccount();
		assertEquals(0, carlsAccount.getBalance(), 0);
		carlsAccount.deposit(2000.20);
		double currentBalance = carlsAccount.getBalance();
		assertEquals(2000.20, currentBalance, 0);
	}
	
	@Test
	public void testWithdraw() throws Exception {
		BankAccount testAccount = new BankAccount();
		testAccount.deposit(5000);
		testAccount.withdraw(1500);
		double currentBalance = testAccount.getBalance();
		assertEquals(3500, currentBalance, 0);
	}
	
	@Test
	public void testInitial() throws Exception {
		BankAccount savings = new BankAccount(5000);
		assertEquals(5000, savings.getBalance(), 0);
	}
	
	@Test
	public void testAddInterest() throws Exception {
		BankAccount mySavings = new BankAccount(1500);
		mySavings.addInterest(10);
		assertTrue(mySavings.getBalance() > 1500);
	}
	
	@Test
	public void testTransfer() throws Exception {
		BankAccount myAccount = new BankAccount(2500);
		BankAccount newAccount = new BankAccount();
		myAccount.transfer(500, newAccount);
		
		assertTrue(myAccount.getBalance() < 2500 && newAccount.getBalance() > 0);
	}

}
