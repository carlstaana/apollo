package com.apollo.training.set4.calc;

import java.math.BigDecimal;

public class Percent extends CalculatorEngine implements Operation {

	@Override
	public BigDecimal compute(BigDecimal currentValue, BigDecimal value) throws OperationException {
		value = value.divide(new BigDecimal(100));	// means: value = value / 100;
		return value;
	}

}
