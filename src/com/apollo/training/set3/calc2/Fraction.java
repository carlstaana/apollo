package com.apollo.training.set3.calc2;

import java.math.BigDecimal;

public class Fraction extends CalculatorEngine implements Operation {

	@Override
	public BigDecimal compute(BigDecimal currentValue, BigDecimal value) {
		value = BigDecimal.valueOf(1/value.doubleValue());	// means: value = 1 / value;
		return value;
	}

}
