package com.apollo.training.set4.calc;

import java.math.BigDecimal;

public class Add extends CalculatorEngine implements Operation {

	@Override
	public BigDecimal compute(BigDecimal currentValue, BigDecimal value) throws OperationException {
		// TODO check first if the parameters are numbers
		try {
			currentValue = currentValue.add(value); 
		} catch (NullPointerException e) {
			throw new OperationException("[Error] Input is NULL");
		}
		return currentValue;
	}
	
}
