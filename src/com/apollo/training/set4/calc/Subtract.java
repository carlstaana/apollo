package com.apollo.training.set4.calc;

import java.math.BigDecimal;

public class Subtract extends CalculatorEngine implements Operation {

	@Override
	public BigDecimal compute(BigDecimal currentValue, BigDecimal value) throws OperationException {
		try {
			currentValue = currentValue.subtract(value); 
		} catch (NullPointerException e) {
			throw new OperationException("[Error] Input is NULL");
		}
		return currentValue;
	}

}
