package com.apollo.fmfi;

import static org.junit.Assert.*;

import org.junit.Test;

public class OverGuaranteeTests {

	@Test
	public void test() {
		Double amount = (double) 4000;
		Loan loan = new Loan(amount);
		loan.guarantee(new Guarantor(800));
		loan.guarantee(new Guarantor(400));
		loan.guarantee(new Guarantor(200));
		
		assertTrue(loan.getGuarantors().get(0).getPointsGuaranteed() == 300);
		assertTrue(loan.getGuarantors().get(0).getPointsReturned() == 500);
		
		assertTrue(loan.getGuarantors().get(1).getPointsGuaranteed() == 300);
		assertTrue(loan.getGuarantors().get(1).getPointsReturned() == 100);
		
		assertTrue(loan.getGuarantors().get(2).getPointsGuaranteed() == 200);
		assertTrue(loan.getGuarantors().get(2).getPointsReturned() == 0);
	}

}
