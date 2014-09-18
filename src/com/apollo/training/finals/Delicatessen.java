package com.apollo.training.finals;

import java.math.BigDecimal;
import java.util.Scanner;

public class Delicatessen {
	private static Scanner in;

	public static void main(String[] args) {
		in = new Scanner(System.in);
		String input;
		String item;
		BigDecimal price = null;
		int delivery;
		boolean overnightdelivery = false;
		boolean valid = false;

		System.out.print("Enter the item: ");
		item = in.nextLine();
		
		while (!valid) {
			System.out.print("Enter the price: ");
			input = in.next();
			try {
				price = BigDecimal.valueOf(Double.parseDouble(input));
			} catch (NumberFormatException e) {
				continue;
			}
			valid = true;
		}
		
		valid = false;
		
		while (!valid) {
			System.out.print("Overnight delivery (0==no, 1==yes): ");
			input = in.next();
			try {
				delivery = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				continue;
			}
			
			if (delivery == 0) {
				overnightdelivery = false;
			} else if (delivery == 1) {
				overnightdelivery = true;
			} else {
				continue;
			}
			
			valid = true;
		}
		
		Order order = new Order(item, price, overnightdelivery);
		System.out.println("Order:");
		System.out.printf(" %s\t%.2f\n", item, price.doubleValue());
		System.out.printf(" shipping\t%.2f\n", order.getShippingCost().doubleValue());
		System.out.printf(" total\t\t%.2f\n", order.getTotal().doubleValue());

	}

}
