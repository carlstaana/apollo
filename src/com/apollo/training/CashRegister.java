package com.apollo.training;

public class CashRegister {
	private static final double VAT = 12;
	private double amount;
	private double payment;
	private double totalTax;
	
	public CashRegister() {
		amount = 0;
		payment = 0;
		totalTax = 0;
	}
	
	public void recordPurchase(double amount) {
		this.amount += amount;
	}


	public void enterPayment(double payment) {
		if (payment > amount) {
			this.payment = payment;
		} else {
			System.out.println("You have insufficient funds.");
		}
	}


	public double giveChange() {
		if (payment > 0 && amount > 0) {
			return payment-amount;
		} else if (payment <= 0) {
			System.out.println("You did not pay yet.");
		} else if (amount <= 0) {
			System.out.println("You did not bought anything yet.");
		}
		
		return 0;
	}

	public void recordTaxablePurchase(double taxableAmount) {
		double tax = taxableAmount * (VAT / 100);
		amount += taxableAmount + tax;
		totalTax += tax;
	}

	public double getTotalTax() {
		return totalTax;
	}

}
