package com.apollo.training;

import java.util.Calendar;
import java.util.Scanner;

public class WeekendSelector {
	
	@SuppressWarnings({ "resource", "unused" })
	public static void main(String[] args) {
		int year = 0;
		Calendar cal = Calendar.getInstance();
		Scanner in = new Scanner(System.in);
		String input;
		
		
		
		boolean valid = false;
		while (!valid) {
			System.out.print("Enter year: ");
			input = in.nextLine();
			
			try {
				year = Integer.parseInt(input);
				valid = true;
			} catch (NumberFormatException e) {
				valid = false;
			}
		}
	}
}
