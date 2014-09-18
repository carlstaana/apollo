package com.apollo.training.set1;

import java.util.Random;

public class Dice {
	int sides;

	public Dice(int n) {
		sides = n;
	}

	public int roll() {
		int output = 0;
		
		Random random = new Random();
		output = random.nextInt(sides) + 1;
		System.out.print(output);
		
		return output;
	}

}
