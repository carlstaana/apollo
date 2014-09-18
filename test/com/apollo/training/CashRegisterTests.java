package com.apollo.training;

import static org.junit.Assert.*;

import org.junit.Test;

public class CashRegisterTests {

	@Test
	public void testWithoutTax() {
		CashRegister register = new CashRegister();
		register.recordPurchase(29.95);
		register.recordPurchase(9.95);
		register.enterPayment(50);
		double change = register.giveChange();
		assertEquals(50-29.95-9.95, change, 0);
	}
	
	@Test
	public void testWithTax() throws Exception {
		CashRegister register = new CashRegister();
		register.recordTaxablePurchase(50.00);
		register.recordTaxablePurchase(19.95);
		double tax = register.getTotalTax();
		register.enterPayment(100);
		double change = register.giveChange();
		assertEquals(100-(50+19.95+tax), change, 1);
	}

}
