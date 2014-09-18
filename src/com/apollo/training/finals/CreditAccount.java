package com.apollo.training.finals;

import java.math.BigDecimal;

public class CreditAccount {
	private BigDecimal unpaidBalance;
	private BigDecimal interestRate;
	private BigDecimal totalPayment = new BigDecimal(0);
	
	public CreditAccount(BigDecimal unpaidBalance, BigDecimal interestRate) {
		this.unpaidBalance = unpaidBalance;
		this.interestRate = interestRate;
	}
	
	public void recordMonthlyPayment(BigDecimal payment) {
		if (payment.compareTo(unpaidBalance) == 1) {
			unpaidBalance = unpaidBalance.add(unpaidBalance.multiply(interestRate.movePointLeft(2)));
		} else {
			unpaidBalance = unpaidBalance.add(unpaidBalance.multiply(interestRate.movePointLeft(2)));
			unpaidBalance = unpaidBalance.subtract(payment);
			totalPayment = totalPayment.add(payment);
		}
		
	}
	
	public BigDecimal getBalance() {
		return unpaidBalance;
	}
	
	public BigDecimal getTotalPayments() {
		return totalPayment;
	}
}
