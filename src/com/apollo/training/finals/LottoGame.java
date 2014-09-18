package com.apollo.training.finals;

import java.util.Scanner;

public class LottoGame {

	private static Scanner in;

	public static void main(String[] args) {
		in = new Scanner(System.in);
		String input;
		int[] playerPicks = new int[6];
		int count = 0;
		boolean valid = false;

		System.out.println("Picking 6 random numbers between 1 .. 42");
		for (int i = 0; i < playerPicks.length; i++) {
			valid = false;
			while (!valid) {
				System.out.print("Please enter your [" + (i + 1) + "] number: ");
				input = in.next();
				try {
					playerPicks[count] = Integer.parseInt(input);
					count++;
				} catch (NumberFormatException e) {
					System.out.println("Error. please try again");
					continue;
				}
				valid = true;
			}
		}
		
		LottoPicker lotto = new LottoPicker();
		int[] computerPicks = lotto.pick(6, 42);
		int matches = lotto.checkMatch(computerPicks, playerPicks);
		System.out.print("You have won: ");
		switch (matches) {
		case 3:
			System.out.println("20");
			break;
		case 4:
			System.out.println("500");
			break;
		case 5:
			System.out.println("20,000");
			break;
		case 6:
			System.out.println("Jackpot!!!");
			break;
		default:
			System.out.println("None.");
			break;
		}
	}

}
