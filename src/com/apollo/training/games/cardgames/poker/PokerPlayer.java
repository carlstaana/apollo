package com.apollo.training.games.cardgames.poker;


import com.apollo.training.games.cardgames.Card;
import com.apollo.training.games.cardgames.Player;
import com.apollo.training.games.cardgames.poker.PokerLogic.BestHand;

public class PokerPlayer extends Player {
	
	private int money = 1000;
	
	private int currentBet = 0;
	
	private boolean folded = false;
	
	private BestHand bestHand;
	
	private int highestRank;
	
	public PokerPlayer() {
		super();
	}
	
	public PokerPlayer(String name) {
		super(name);
	}

	
	
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getCurrentBet() {
		return currentBet;
	}

	public void setCurrentBet(int currentBet) {
		this.currentBet = currentBet;
	}
	
	
	public boolean isFolded() {
		return folded;
	}

	public void setFolded(boolean folded) {
		this.folded = folded;
	}

	public BestHand getBestHand() {
		return bestHand;
	}

	public void setBestHand(BestHand bestHand) {
		this.bestHand = bestHand;
	}

	public int getHighestRank() {
		return highestRank;
	}

	public void setHighestRank(int highestRank) {
		this.highestRank = highestRank;
	}

	@Override
	public String showHand() {
		String output = "\n" + getName() + "'s Hand:\n";
		for (Card card : getHand()) {
			output += card.toString();
			if (getHand().indexOf(card) < getHand().size() - 1) {
				output += " | ";
			}
		}
		output += "\n";
		System.out.println(output);
		
		return output;
	}

	public void sortHandDescending() {
		if (!getHand().isEmpty()) {
			Card firstCard = getHand().get(0);
			Card secondCard = getHand().get(1);
			
			if (secondCard.isAce() && !firstCard.isAce()) {
				getHand().remove(secondCard);
				getHand().add(0, secondCard);
			} else if (secondCard.getValue() > firstCard.getValue() && !firstCard.isAce()) {
				getHand().remove(secondCard);
				getHand().add(0, secondCard);
			}
		}
	}
}
