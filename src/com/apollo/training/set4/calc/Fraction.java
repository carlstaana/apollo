package com.apollo.training.set4.calc;

import java.math.BigDecimal;

public class Fraction extends CalculatorEngine implements Operation {

	@Override
	public BigDecimal compute(BigDecimal currentValue, BigDecimal value) throws OperationException {		
		if (value.equals(new BigDecimal("0"))) {
			throw new OperationException("Input Error:	You cannot have a divisor equal to zero");
		}
		else {
			value = BigDecimal.valueOf(1/value.doubleValue());	// means: value = 1 / value;
			return value;
		}
	}

}
