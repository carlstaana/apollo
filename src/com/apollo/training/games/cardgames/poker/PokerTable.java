package com.apollo.training.games.cardgames.poker;

import java.util.ArrayList;

import com.apollo.training.book.chapter1.NamePrinter;
import com.apollo.training.games.cardgames.Card;
import com.apollo.training.games.cardgames.Deck;

public class PokerTable {
	
	private final int BASE_BET = 40;
	
	private ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();
	
	private int potMoney = 0;
	
	private Deck deck = new Deck();
	
	private ArrayList<Card> tableCards = new ArrayList<Card>();
	
	private int highestBet = 0;
	
	private PokerLogic pokerLogic = new PokerLogic();
	
	
	
	public PokerTable() {
	}

	public PokerTable(int numberOfPlayers, int numberOfCpus) {
		for (int i = 0; i < numberOfPlayers; i++) {
			System.out.println("Player " + (i+1));
			players.add(new PokerPlayer());
		}
		
		for (int i = 0; i < numberOfCpus; i++) {
			System.out.println("CPU " + (i+1) + " added.");
			players.add(new PokerPlayer("CPU " + (i+1)));
		}
	}



	public ArrayList<PokerPlayer> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<PokerPlayer> players) {
		this.players = players;
	}

	public int getPotMoney() {
		return potMoney;
	}

	public void setPotMoney(int potMoney) {
		this.potMoney = potMoney;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public ArrayList<Card> getTableCards() {
		return tableCards;
	}

	public void setTableCards(ArrayList<Card> tableCards) {
		this.tableCards = tableCards;
	}

	public int getHighestBet() {
		highestBet = 0;
		
		for (PokerPlayer pokerPlayer : players) {
			if (pokerPlayer.getCurrentBet() > highestBet) {
				highestBet = pokerPlayer.getCurrentBet();
			}
		}
		
		return highestBet;
	}

	public void setHighestBet(int highestBet) {
		this.highestBet = highestBet;
	}

	public int getBASE_BET() {
		return BASE_BET;
	}

	public boolean hasEqualBets() {
		getHighestBet();
		for (PokerPlayer pokerPlayer : players) {
			if (!pokerPlayer.isFolded() && pokerPlayer.getCurrentBet() != highestBet) {
				return false;
			}
		}
		
		return true;
	}

	public void call(PokerPlayer pokerPlayer) {
		NamePrinter printer = new NamePrinter();
		if (potMoney <= 0 && pokerPlayer.getCurrentBet() <= 0) {
			potMoney += BASE_BET;
			pokerPlayer.setCurrentBet(BASE_BET);
			pokerPlayer.setMoney(pokerPlayer.getMoney() - BASE_BET);
		} else if (pokerPlayer.getCurrentBet() < getHighestBet()) {
			// if money is sufficient
			int currentBet = pokerPlayer.getCurrentBet();
			if (pokerPlayer.getMoney() >= getHighestBet()) {
				potMoney += getHighestBet() - currentBet;
				pokerPlayer.setCurrentBet(getHighestBet());
				pokerPlayer.setMoney(pokerPlayer.getMoney() - (getHighestBet() - currentBet));
			} else {
				// TODO: make the player to go ALL-IN
			}
		}
		
		printer.print(pokerPlayer.getName() + " called " + pokerPlayer.getCurrentBet());
	}

	public void raise(PokerPlayer pokerPlayer) {
		NamePrinter printer = new NamePrinter();
		if (potMoney <= 0 && pokerPlayer.getCurrentBet() <= 0) {
			pokerPlayer.setCurrentBet(BASE_BET + BASE_BET);
			pokerPlayer.setMoney(pokerPlayer.getMoney() - pokerPlayer.getCurrentBet());
			potMoney += pokerPlayer.getCurrentBet();
		} else if (pokerPlayer.getCurrentBet() < getHighestBet()) {
			int previousBet = pokerPlayer.getCurrentBet();
			int raisedBet = getHighestBet() + BASE_BET;
			pokerPlayer.setCurrentBet(raisedBet);
			pokerPlayer.setMoney(pokerPlayer.getMoney() - (raisedBet - previousBet));
			potMoney += raisedBet - previousBet;
		} else if (pokerPlayer.getCurrentBet() <= 0 && getHighestBet() <= 0) {
			pokerPlayer.setCurrentBet(BASE_BET);
			pokerPlayer.setMoney(pokerPlayer.getMoney() - pokerPlayer.getCurrentBet());
			potMoney += pokerPlayer.getCurrentBet();
		}
		
		printer.print(pokerPlayer.getName() + " raised by 40.\n"
				+ "Total bet: " + pokerPlayer.getCurrentBet());
	}

	public void fold(PokerPlayer pokerPlayer) {
		NamePrinter printer = new NamePrinter();		
		pokerPlayer.setFolded(true);
		
		printer.print(pokerPlayer.getName() + " has folded.");
	}

	public void revealInitialCards() {
		for (int i = 0; i < 3; i++) {
			tableCards.add(deck.draw());
		}
		
		showTable();
	}

	public void showTable() {
		NamePrinter np = new NamePrinter();
		np.print("Table");
		for (Card card : tableCards) {
			System.out.print(card.toString());
			if (tableCards.indexOf(card) != tableCards.size() - 1) {
				System.out.print(" | ");
			}
		}
		System.out.println();
	}

	public void addCard() {
		tableCards.add(deck.draw());
		
		showTable();
	}

	public void getDefaultWinner() {
		PokerPlayer winnerPlayer = null;
		
		for (PokerPlayer pokerPlayer : players) {
			if (!pokerPlayer.isFolded()) {
				winnerPlayer = pokerPlayer;
				pokerPlayer.setMoney(pokerPlayer.getMoney() + potMoney);
				break;
			}
		}
		
		System.out.println("The winner is " + winnerPlayer.getName() + "\n"
				+ "Prize: " + potMoney);
		System.out.println(winnerPlayer.getName() + ": " + winnerPlayer.getMoney());
	}

	public void getWinner() {
		PokerPlayer winnerPlayer = null;
		showTable();
		System.out.println();
		
		int highestHand = 0;
		for (PokerPlayer pokerPlayer : players) {
			if (!pokerPlayer.isFolded()) {
				System.out.println("["+pokerPlayer.getBestHand()+"]");
				pokerPlayer.showHand();
				
				if (highestHand == 0) {
					highestHand = pokerPlayer.getBestHand().ordinal();
					winnerPlayer = pokerPlayer;
					continue;
				}
				
				if (pokerPlayer.getBestHand().ordinal() < highestHand) {
					highestHand = pokerPlayer.getBestHand().ordinal();
					winnerPlayer = pokerPlayer;
				} else if (pokerPlayer.getBestHand().ordinal() == highestHand) {
					if (pokerPlayer.getHighestRank() == 1 && winnerPlayer.getHighestRank() != 1) {
						highestHand = pokerPlayer.getBestHand().ordinal();
						winnerPlayer = pokerPlayer;
					} else if (pokerPlayer.getHighestRank() != 1 && winnerPlayer.getHighestRank() != 1) {
						if (pokerPlayer.getHighestRank() > winnerPlayer.getHighestRank()) {
							highestHand = pokerPlayer.getBestHand().ordinal();
							winnerPlayer = pokerPlayer;
						}
					} else if (pokerPlayer.getHighestRank() == winnerPlayer.getHighestRank()) {
						winnerPlayer = tieBreaker(winnerPlayer, pokerPlayer);
					}
				} 
			}
		}
		
		for (PokerPlayer pokerPlayer : players) {
			if (pokerPlayer.getName().equalsIgnoreCase(winnerPlayer.getName())) {
				pokerPlayer = winnerPlayer;
				pokerPlayer.setMoney(pokerPlayer.getMoney() + potMoney);
				break;
			}
		}
		
		
		System.out.println(winnerPlayer.getName() + " got a " + winnerPlayer.getBestHand());
		System.out.println("The winner is " + winnerPlayer.getName() + "\n"
				+ "Prize: " + potMoney);
		System.out.println(winnerPlayer.getName() + ": " + winnerPlayer.getMoney());
	}

	private PokerPlayer tieBreaker(PokerPlayer winnerPlayer,
			PokerPlayer pokerPlayer) {
		
		winnerPlayer.sortHandDescending();
		pokerPlayer.sortHandDescending();
		
		// battle for the first card
		Card firstWinnerCard = winnerPlayer.getHand().get(0);
		Card firstPlayerCard = pokerPlayer.getHand().get(0);
		
		int verdict = firstWinnerCard.compareRank(firstPlayerCard, true);
		
		if (verdict > 0) {
			return winnerPlayer;
		} else if (verdict < 0) {
			return pokerPlayer;
		}
		
		// battle for the second card
		Card secondWinnerCard = winnerPlayer.getHand().get(1);
		Card secondPlayerCard = pokerPlayer.getHand().get(1);
		
		verdict = secondWinnerCard.compareRank(secondPlayerCard, true);
		
		if (verdict > 0) {
			return winnerPlayer;
		} else if (verdict < 0) {
			return pokerPlayer;
		}
		
		return winnerPlayer;
	}

	public void compute() {
		for (PokerPlayer pokerPlayer : players) {
			pokerLogic = new PokerLogic();
			if (!pokerPlayer.isFolded()) {
				pokerPlayer.setBestHand(pokerLogic.getBestHand(pokerPlayer, tableCards));
			}
		}
	}
}
