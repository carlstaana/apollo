package com.apollo.training.finals;

import java.math.BigDecimal;
import java.util.Scanner;

public class CreditComputation {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String input;
		BigDecimal balance = null;
		BigDecimal monthlyInterest = null;
		BigDecimal monthlyPayment = null;
		double parser;
		boolean valid = false;
		
		while (!valid) {
			System.out.println("Enter the starting balance:");
			input = in.next();
			try {
				parser = Double.parseDouble(input);
				balance = BigDecimal.valueOf(parser);
			} catch (NumberFormatException e) {
				continue;
			}
			valid = true;
		}
		
		valid = false;
		
		while (!valid) {
			System.out.println("Enter the monthly interest:");
			input = in.next();
			try {
				parser = Double.parseDouble(input);
				monthlyInterest = BigDecimal.valueOf(parser);
			} catch (NumberFormatException e) {
				continue;
			}
			valid = true;
		}
		
		valid = false;
		
		while (!valid) {
			System.out.println("Enter the monthly payment:");
			input = in.next();
			try {
				parser = Double.parseDouble(input);
				monthlyPayment = BigDecimal.valueOf(parser);
			} catch (NumberFormatException e) {
				continue;
			}
			valid = true;
		}
		
		CreditAccount creditAcct = new CreditAccount(balance, monthlyInterest);
		int month = 0;
		while (creditAcct.getBalance().doubleValue() >= 0) {
			if (creditAcct.getBalance().compareTo(monthlyPayment) == 1) {
				creditAcct.recordMonthlyPayment(monthlyPayment);
				month++;
				System.out.println("Month: " + month + " balance: " + creditAcct.getBalance().floatValue() + "total payments: " + creditAcct.getTotalPayments());
			} else {
				creditAcct.recordMonthlyPayment(monthlyPayment);
				System.out.println("Final payment: " + creditAcct.getBalance());
				break;
			}
			
		}
		in.close();
	}

}
