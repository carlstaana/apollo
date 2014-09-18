package com.apollo.training.set3.calc2;

import java.math.BigDecimal;

import com.apollo.training.set4.calc.OperationException;

public interface Operation {
	public BigDecimal compute(BigDecimal currentValue, BigDecimal value);
}
