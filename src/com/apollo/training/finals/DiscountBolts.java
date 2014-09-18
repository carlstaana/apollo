package com.apollo.training.finals;

import java.util.Scanner;

public class DiscountBolts {

	private static Scanner in;

	public static void main(String[] args) {
		int bolts = 0, nuts = 0, washers = 0;
		String input;
		in = new Scanner(System.in);
		boolean valid = false;

		while (!valid) {
			try {
				System.out.print("Number of bolts: ");
				input = in.next();
				bolts = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				continue;
			}

			valid = true;
		}
		
		valid = false;
		
		while (!valid) {
			try {
				System.out.print("Number of nuts: ");
				input = in.next();
				nuts = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				continue;
			}
			valid = true;
		}
		
		valid = false;
		while (!valid) {
			try {
				System.out.print("Number of washers: ");
				input = in.next();
				washers = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				continue;
			}
			valid = true;	
		}
		
		Invoice invoice = new Invoice(bolts, nuts, washers);
		if (!invoice.hasEnoughNuts() || !invoice.hasEnoughWashers()) {
			if (!invoice.hasEnoughNuts()) {
				System.out.println("Check the order: too few nuts");
			}
			if (!invoice.hasEnoughWashers()) {
				System.out.println("Check the order: too few washers");
			}	
		} else {
			System.out.println("Invoice is OK.");
		}
		
		int total = invoice.getTotalCost();
		
		System.out.println("Total cost: " + total);
		
	}

}
