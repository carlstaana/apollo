package com.apollo.training.games.cardgames.luckynine;

import com.apollo.training.games.cardgames.Player;

public class LuckyNinePlayer extends Player {
	
	private int handTotal = 0;
	
	private int score = 0;
	
	
	
	public LuckyNinePlayer() {
		super();
	}
	
	public LuckyNinePlayer(String name) {
		super(name);
	}

	
	
	public int getHandTotal() {
		return handTotal;
	}

	public void setHandTotal(int handTotal) {
		this.handTotal = handTotal;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
}
