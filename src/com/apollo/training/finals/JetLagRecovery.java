package com.apollo.training.finals;

import java.util.Scanner;

public class JetLagRecovery {
	public static void main(String[] args) {
		int travel;
		int timeZones;
		int departureTime;
		int arrivalTime;
		double recovery;
		Scanner in = new Scanner(System.in);
		String input;
		boolean valid = false;

		while (!valid) {
			System.out.print("Enter number of hours of travel: ");
			input = in.next();
			try {
				travel = Integer.parseInt(input);
				break;
			} catch (NumberFormatException e) {
				continue;
			}
		}
		
		while (!valid) {
			System.out.println("Enter number of time zones crossed: ");
			input = in.next();
			try {
				timeZones = Integer.parseInt(input);
				break;
			} catch (NumberFormatException e) {
				continue;
			}
		}
		
		while (!valid) {
			System.out.println("Enter the departure time: ");
			input = in.next();
			try {
				departureTime = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				continue;
			}
		}

	}
}
