package com.apollo.training.games.cardgames.pairs;

import com.apollo.training.games.cardgames.Card;
import com.apollo.training.games.cardgames.Player;

public class PairsPlayer extends Player {
	
	private int score;
	
	private boolean win;
	
	
	
	public PairsPlayer() {
		super();
	}
	
	public PairsPlayer(String name) {
		super(name);
	}

	
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	
	
	public void getCardFromOtherPlayer(PairsPlayer otherPlayer, int targetIndex) {
		Card selectedCard = otherPlayer.getHand().get(targetIndex);
		if (this.isAi() && otherPlayer.isAi()) {
			System.out.println(this.getName() + " got a card from " + otherPlayer.getName());
		} else {
			System.out.println(this.getName() + " got " + selectedCard.toString() + " from " + otherPlayer.getName());
		}
		this.addCardToHand(selectedCard);
		otherPlayer.getHand().remove(targetIndex);
		Pairs.delay(1500);
	}

	@Override
	public String showHand() {
		String output = super.getName() + "'s Hand:\n";
		
		for (Card card : super.getHand()) {
			output += "* " + card.toString() + "\n";
		}
		
		return output;
	}
}
