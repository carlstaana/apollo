package com.apollo.training.finals;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ExerciseRecommender {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String input;
		int temperature = 0;
		boolean valid = false;
		
		while (!valid) {
			try {
				System.out.print("Enter a temperature: ");
				input = in.next();
				temperature = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				continue;
			}
			
			valid = true;
		}
		
		Activities active = new Activities();
		try {
			active.recommend(temperature);
		} catch (InputMismatchException e) {
			System.out.println("Invalid temperature:" + temperature);
		}
		in.close();
	}
}
