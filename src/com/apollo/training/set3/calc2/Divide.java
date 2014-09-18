package com.apollo.training.set3.calc2;

import java.math.BigDecimal;

public class Divide extends CalculatorEngine implements Operation {

	@Override
	public BigDecimal compute(BigDecimal currentValue, BigDecimal value) {
		currentValue = currentValue.divide(value);	// means: currentValue = currentValue / value;
		return currentValue;
	}

}
