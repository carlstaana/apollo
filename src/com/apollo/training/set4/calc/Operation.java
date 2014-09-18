package com.apollo.training.set4.calc;

import java.math.BigDecimal;

public interface Operation {
	public BigDecimal compute(BigDecimal currentValue, BigDecimal value) throws OperationException;
}
