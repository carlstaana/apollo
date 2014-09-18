package com.apollo.training.set4.calc;

import java.math.BigDecimal;

public class Divide extends CalculatorEngine implements Operation {

	@Override
	public BigDecimal compute(BigDecimal currentValue, BigDecimal value) throws OperationException {
		try {
			currentValue = currentValue.divide(value);
		} catch (ArithmeticException e) {
			throw new OperationException("Input Error:	You cannot have a divisor equal to zero");
		} catch (NullPointerException e) {
			throw new OperationException("Input Error:	Input is NULL");
		}
		return currentValue;
	}

}
