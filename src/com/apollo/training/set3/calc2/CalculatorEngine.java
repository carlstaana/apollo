package com.apollo.training.set3.calc2;

import java.math.BigDecimal;

public class CalculatorEngine {
	private BigDecimal currentValue = new BigDecimal(0);	// initialize currentValue = 0
	private BigDecimal currentDisplay;
	
	public void getDisplay() {
		System.out.println("Display: " + currentDisplay);
	}

	public void updateDisplay(BigDecimal currentValue) {
		currentDisplay = currentValue;
	}
}
