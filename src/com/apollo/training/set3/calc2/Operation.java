package com.apollo.training.set3.calc2;

import java.math.BigDecimal;

public interface Operation {
	public BigDecimal compute(BigDecimal currentValue, BigDecimal value);
}
