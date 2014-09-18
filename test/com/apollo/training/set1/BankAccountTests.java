package com.apollo.training.set1;

import static org.junit.Assert.*;

import org.junit.Test;

public class BankAccountTests {

	@Test
	public void test() {
		double fee = 0;
		double amount = 0;
		double initialBalance = 100;
		double actual = 0;
		double expected = 0;
		
		BankAccount bankAccount = new BankAccount(initialBalance);
		
		fee = 10;
		bankAccount.setFee(fee);
		
		amount = 20;
		bankAccount.deposit(amount); //120
		
		actual = bankAccount.getBalance();
		expected = 120;
		assertEquals(expected, actual, 0);
		
		amount = 50;
		bankAccount.withdraw(amount); //70
		
		actual = bankAccount.getBalance();
		expected = 70;
		assertEquals(expected, actual, 0);
		
		amount = 30;
		bankAccount.deposit(amount); //100, deduct = 10
		
		actual = bankAccount.getBalance();
		expected = 100;
		assertEquals(expected, actual, 0);
		
		amount = 10;
		bankAccount.withdraw(amount); //90, deduct = 20
		
		actual = bankAccount.getBalance();
		expected = 90;
		assertEquals(expected, actual, 0);
		
		bankAccount.deductMonthlyCharge(); //for 1st month: balance = 70; total deduction = 20, refresh transaction count
		
		actual = bankAccount.getBalance();
		expected = 70;
		assertEquals(expected, actual, 0);
		
		amount = 50;
		bankAccount.deposit(amount); //120
		
		actual = bankAccount.getBalance();
		expected = 120;
		assertEquals(expected, actual, 0);
		
		amount = 200;
		bankAccount.deposit(amount); //320
		
		actual = bankAccount.getBalance();
		expected = 320;
		assertEquals(expected, actual, 0);
		
		bankAccount.deductMonthlyCharge(); //for 2nd month, total of 2 transactions; no deduction; balance = 320, refresh transaction count
		
		actual = bankAccount.getBalance();
		expected = 320;
		assertEquals(expected, actual, 0);
	}

}
