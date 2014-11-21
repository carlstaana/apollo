package com.apollo.training.games.cardgames;

import static org.junit.Assert.*;

import org.junit.Test;

import com.apollo.training.games.cardgames.Card.Suit;

@SuppressWarnings("unused")
public class UnicodeTests {
	@Test
	public void test() {
		Card card = new Card();
		card.setSuit(Suit.SPADES);
		card.setValue(1);
		
		System.out.println(card.toString());
		
		Card card2 = new Card();
		card2.setSuit(Suit.HEARTS);
		card2.setValue(13);
		
		System.out.println(card2.toString());
	}

}
