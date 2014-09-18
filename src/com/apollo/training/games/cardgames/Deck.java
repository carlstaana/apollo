package com.apollo.training.games.cardgames;

import java.util.ArrayList;
import java.util.Random;

import com.apollo.training.games.cardgames.Card.Suit;

public class Deck {
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Card> cardPile = new ArrayList<Card>();
	
	public Deck() {
		deck.clear();
		addperSuit(Suit.HEARTS);
		addperSuit(Suit.SPADES);
		addperSuit(Suit.CLUBS);
		addperSuit(Suit.DIAMONDS);
	}

	private void addperSuit(Suit suit) {
		for (int i = 1; i <= 13; i++) {
			Card card = new Card(i, suit);
			
			deck.add(card);
		}
	}
	
	public Card draw() {
		if (deck.size() > 0) {
			Random random = new Random();
			int output = random.nextInt(deck.size());
			Card drawnCard = deck.get(output);
			deck.remove(output);
			
			return drawnCard;
		} else {
			return null;
		}
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}

	public ArrayList<Card> getCardPile() {
		return cardPile;
	}

	public void setCardPile(ArrayList<Card> cardPile) {
		this.cardPile = cardPile;
	}

	public boolean isEmpty() {
		if (deck.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
