package com.apollo.training.set3.calc2;

import java.math.BigDecimal;

public class SquareRoot extends CalculatorEngine implements Operation{

	@Override
	public BigDecimal compute(BigDecimal currentValue, BigDecimal value) {
		value = BigDecimal.valueOf(StrictMath.sqrt(value.doubleValue()));	// means: value = square Root of value OR value = Math.sqrt(value);
		return value;
	}

}
