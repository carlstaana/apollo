package com.apollo.training.set1;

public class BankAccount {
	private double balance = 0;
	private double fee = 0;
	public int transCount = 0;
	public int freeTransCount = 2;
	public double totalDeduct = 0;

	public BankAccount(double initialBalance) {
		balance = initialBalance;
	}
	
	public void setFee(double fee) {
		this.fee = fee;
	}
	
	public void deposit(double amount) {
		balance += amount;
		transCount++;
	}

	public void withdraw(double amount) {
		balance -= amount;
		transCount++;
	}
	
	public void deductMonthlyCharge() {
		if(Math.max(freeTransCount, transCount) > freeTransCount){
			totalDeduct = (transCount - freeTransCount) * fee;
			balance -= totalDeduct;	// deduction
		}
		transCount = 0; // reset transaction count
	}

	public double getBalance() {
		System.out.println("Balance = " + balance);
		return this.balance;
	}
	
	
}
