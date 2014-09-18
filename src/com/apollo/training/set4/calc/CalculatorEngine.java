package com.apollo.training.set4.calc;

import java.math.BigDecimal;

public class CalculatorEngine {
	private BigDecimal currentValue = new BigDecimal(0);	// initialize currentValue = 0
	private BigDecimal currentDisplay;
	
	public BigDecimal getDisplay(BigDecimal input) {
		return input;
	}

	public void updateDisplay() {
		currentDisplay = currentValue;
		System.out.println("Display: " + currentDisplay);
	}
}
