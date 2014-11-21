package com.apollo.training.set3.calc2;

import java.math.BigDecimal;

public class Add extends CalculatorEngine implements Operation {

	@Override
	public BigDecimal compute(BigDecimal currentValue, BigDecimal value) {
		currentValue = currentValue.add(value);		// this line simply means: currentValue = currentValue + value
		return currentValue;
	}
	
}
