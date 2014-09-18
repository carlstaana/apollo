package com.apollo.training.set4.calc;

import java.math.BigDecimal;

public class SquareRoot extends CalculatorEngine implements Operation{

	public final int LESS_THAN_ZERO = -1;
	
	@Override
	public BigDecimal compute(BigDecimal currentValue, BigDecimal value) throws OperationException {
		// check input if it is greater than or equal to zero
		if (value.compareTo(new BigDecimal("0")) == LESS_THAN_ZERO) {
			throw new OperationException("Input Error:	Number inside a square root must be a positive number");
		}
		else {
			value = BigDecimal.valueOf(StrictMath.sqrt(value.doubleValue()));	// means: value = square Root of value OR value = Math.sqrt(value);
			return value;
		}
	}

}
