package com.apollo.training.games.cardgames;

import java.util.ArrayList;
import java.util.Scanner;


public class Player {
	
	private String name = null;
	
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	private boolean ai;
	
	

	public Player() {
		Scanner in = new Scanner(System.in);
		boolean valid = false;
		ai = false;
		
		while (!valid) {
			System.out.print("Please enter a name: ");
			try {
				name = in.nextLine();
				if (name.isEmpty()) {
					throw new NullPointerException();
				}
				break;
			} catch (NullPointerException e) {
				System.out.println("Null. Try Again.");
				continue;
			}
			
		}
		
		hand.clear();
		in.close();
	}
	
	public Player(String name) {
		this.name = name;
		ai = true;
		
		hand.clear();
	}


	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public boolean isAi() {
		return ai;
	}

	public void setAi(boolean ai) {
		this.ai = ai;
	}
	
	
	
	public void addCardToHand(Card card) {
		if (card != null) {
			hand.add(card);
		}
	}

	public String showHand() {
		String output = "";
		if (hand.size() > 0) {
			for (int i = 0; i < hand.size(); i++) {
				output += "["+(i+1)+"] " + hand.get(i).toString() + "\n";
			}
		} else {
			output = "There are no cards left";
		}
		
		return output;
	}
	
	public boolean hasThisCard(Card compareCard) {
		for (Card card : hand) {
			if (card.equals(compareCard)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj != null) {
			Player other = (Player) obj;
			if (name.equalsIgnoreCase(other.name)) {
				return true;
			}
		}
		
		return false;
	}
}
