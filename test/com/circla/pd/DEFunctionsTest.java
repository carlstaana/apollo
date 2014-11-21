package com.circla.pd;

import static org.junit.Assert.*;

import org.junit.Test;

public class DEFunctionsTest {

	@Test
	public void testDesErr() {
		DEFunctions deFunc = new DEFunctions();
		double target = 100;
		double computed = 50;
		double result = deFunc.computeDesErr(target, computed);
		assertEquals(50, result, 0);
	}

	@Test
	public void testGetCommercialValue() throws Exception {
		DEFunctions defunc = new DEFunctions();
		int target = 390;
		double sample = 375;
		int result = defunc.getCommercialValueR(sample);
		assertEquals(result, target);
	}
	
	@Test
	public void testMicroFaradSign() throws Exception {
		DEFunctions defunc = new DEFunctions();
		System.out.println(defunc.microfaradSign());
	}
}
