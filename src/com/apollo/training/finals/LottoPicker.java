package com.apollo.training.finals;

import java.util.ArrayList;
import java.util.Random;

public class LottoPicker {
	@SuppressWarnings("unused")
	private long seed;
	Random random = new Random();
	public LottoPicker() {
	}
	
	public LottoPicker(long seed) {
		this.seed = seed;
	}
	
	public int[] pick(int count, int max) {
		int[] computer = new int[count];
		
		for (int i = 0; i < computer.length; i++) {
			computer[i] = random.nextInt(max) + 1;
		}
		
		return computer;
	}
	
	public int checkMatch(int[] picks, int[] playerPicks) {
		int matchCount = 0;
		ArrayList<Integer> matcher = new ArrayList<Integer>();
		for (int i = 0; i < picks.length; i++) {
			matcher.add(picks[i]);
		}
		
		for (int i = 0; i < playerPicks.length; i++) {
			if (matcher.contains(playerPicks[i])) {
				matchCount++;
			}
		}
		return matchCount;
	}
	
	
	
}
