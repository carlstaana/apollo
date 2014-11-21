package com.apollo.training;

public class BankAccount {
	private int accountNumber;
	private double balance = 0;
	private double fee = 0;
	private static int lastAssignedNumber = 1000;
	
	// CONSTRUCTORS
	public BankAccount() {
		setAccountNumber(lastAssignedNumber++);
	}
	
	public BankAccount(double initialBalance) {
		balance = initialBalance;
		setAccountNumber(lastAssignedNumber++);
	}

	
	
	

	// METHODS
	public void deposit(double depositAmount) {
		if (depositAmount < 0) {
			System.out.println("Please enter a positive amount.");
		} else {
			balance += depositAmount - fee;
		}
		
	}

	public void withdraw(double withdrawAmount) {
		if (withdrawAmount > balance) {
			System.out.println("You have insufficient funds on your balance to withdraw.");
		} else if (withdrawAmount < 0) {
			System.out.println("Please enter a positive amoount.");
		} else {
			balance -= withdrawAmount - fee;
		}
	}

	public void addInterest(double interestRate) {
		if (balance <= 0) {
			System.out.println("Error! You cannot add interest is your balance is 0.");
		} else {
			balance += (balance * interestRate) / 100;
		}
	}
	
	public void transfer(double transferAmount, BankAccount otherAccount) {
		if (transferAmount < 0) {
			System.out.println("Please enter a positive amount.");
		} else if (transferAmount > balance) {
			System.out.println("You do not have sufficiant balance for the transaction.");
		} else {
			withdraw(transferAmount);
			otherAccount.deposit(transferAmount);
		}
	}
	
	
	
	
	
	// GETTERS/SETTERS
	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	
	
	
}
