package com.apollo.training.games.cardgames.poker;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.apollo.training.games.cardgames.Card;
import com.apollo.training.games.cardgames.Card.Suit;
import com.apollo.training.games.cardgames.poker.PokerLogic.BestHand;

public class PokerLogicTests {

	@Test
	public void testIsOnePair() {
		PokerLogic pokerLogic = new PokerLogic();
		ArrayList<Card> testPairs = new ArrayList<Card>();
		testPairs.add(new Card(1, Suit.CLUBS));
		testPairs.add(new Card(2, Suit.SPADES));
		testPairs.add(new Card(3, Suit.DIAMONDS));
		testPairs.add(new Card(3, Suit.SPADES));
		testPairs.add(new Card(13, Suit.HEARTS));
		pokerLogic.setTotalHand(testPairs);
		pokerLogic.setPokerPlayer(new PokerPlayer("Carl"));
		
		assertTrue(pokerLogic.isOnePair());
	}
	
	@Test
	public void testIsTwoPair() throws Exception {
		PokerLogic pokerLogic = new PokerLogic();
		ArrayList<Card> testPairs = new ArrayList<Card>();
		testPairs.add(new Card(1, Suit.CLUBS));
		testPairs.add(new Card(2, Suit.SPADES));
		testPairs.add(new Card(3, Suit.DIAMONDS));
		testPairs.add(new Card(3, Suit.SPADES));
		testPairs.add(new Card(13, Suit.HEARTS));
		testPairs.add(new Card(12, Suit.SPADES));
		testPairs.add(new Card(12, Suit.DIAMONDS));
		pokerLogic.setTotalHand(testPairs);
		pokerLogic.setPokerPlayer(new PokerPlayer("Carl"));
		
		assertTrue(pokerLogic.isTwoPair());
	}
	
	@Test
	public void testIsThreeOfAKind() throws Exception {
		PokerLogic pokerLogic = new PokerLogic();
		ArrayList<Card> testPairs = new ArrayList<Card>();
		testPairs.add(new Card(1, Suit.CLUBS));
		testPairs.add(new Card(2, Suit.SPADES));
		testPairs.add(new Card(3, Suit.DIAMONDS));
		testPairs.add(new Card(3, Suit.SPADES));
		testPairs.add(new Card(13, Suit.HEARTS));
		testPairs.add(new Card(12, Suit.SPADES));
		testPairs.add(new Card(12, Suit.DIAMONDS));
		testPairs.add(new Card(3, Suit.HEARTS));
		pokerLogic.setTotalHand(testPairs);
		pokerLogic.setPokerPlayer(new PokerPlayer("Carl"));
		
		assertTrue(pokerLogic.isThreeOfAKind());
	}
	
	@Test
	public void testIsStraight() throws Exception {
		PokerLogic pokerLogic = new PokerLogic();
		ArrayList<Card> testPairs = new ArrayList<Card>();
		testPairs.add(new Card(1, Suit.CLUBS));
		testPairs.add(new Card(2, Suit.SPADES));
		testPairs.add(new Card(3, Suit.DIAMONDS));
		testPairs.add(new Card(3, Suit.SPADES));
		testPairs.add(new Card(13, Suit.HEARTS));
		testPairs.add(new Card(12, Suit.SPADES));
		testPairs.add(new Card(12, Suit.DIAMONDS));
		testPairs.add(new Card(3, Suit.HEARTS));
		testPairs.add(new Card(4, Suit.SPADES));
		testPairs.add(new Card(5, Suit.SPADES));
		testPairs.add(new Card(6, Suit.HEARTS));
		pokerLogic.setTotalHand(testPairs);
		pokerLogic.setPokerPlayer(new PokerPlayer("Carl"));
		
		assertTrue(pokerLogic.isStraight());
	}

	@Test
	public void testIsFlush() throws Exception {
		PokerLogic pokerLogic = new PokerLogic();
		ArrayList<Card> testPairs = new ArrayList<Card>();
		testPairs.add(new Card(1, Suit.CLUBS));
		testPairs.add(new Card(2, Suit.SPADES));
		testPairs.add(new Card(3, Suit.DIAMONDS));
		testPairs.add(new Card(3, Suit.SPADES));
		testPairs.add(new Card(13, Suit.HEARTS));
		testPairs.add(new Card(12, Suit.SPADES));
		testPairs.add(new Card(12, Suit.DIAMONDS));
		testPairs.add(new Card(3, Suit.HEARTS));
		testPairs.add(new Card(4, Suit.SPADES));
		testPairs.add(new Card(5, Suit.SPADES));
		testPairs.add(new Card(6, Suit.HEARTS));
		pokerLogic.setTotalHand(testPairs);
		pokerLogic.setPokerPlayer(new PokerPlayer("Carl"));
		
		assertTrue(pokerLogic.isFlush());
	}
	
	@Test
	public void testIsFullHouse() throws Exception {
		PokerLogic pokerLogic = new PokerLogic();
		ArrayList<Card> testPairs = new ArrayList<Card>();
		testPairs.add(new Card(1, Suit.CLUBS));
		testPairs.add(new Card(2, Suit.SPADES));
		testPairs.add(new Card(3, Suit.DIAMONDS));
		testPairs.add(new Card(3, Suit.SPADES));
		testPairs.add(new Card(13, Suit.HEARTS));
		testPairs.add(new Card(12, Suit.SPADES));
		testPairs.add(new Card(12, Suit.DIAMONDS));
		testPairs.add(new Card(3, Suit.HEARTS));
		testPairs.add(new Card(4, Suit.SPADES));
		testPairs.add(new Card(5, Suit.SPADES));
		testPairs.add(new Card(6, Suit.HEARTS));
		pokerLogic.setTotalHand(testPairs);
		pokerLogic.setPokerPlayer(new PokerPlayer("Carl"));
		
		assertTrue(pokerLogic.isFullHouse());
	}
	
	@Test
	public void testIsFourOfAKind() throws Exception {
		PokerLogic pokerLogic = new PokerLogic();
		ArrayList<Card> testPairs = new ArrayList<Card>();
		testPairs.add(new Card(1, Suit.CLUBS));
		testPairs.add(new Card(2, Suit.SPADES));
		testPairs.add(new Card(3, Suit.DIAMONDS));
		testPairs.add(new Card(3, Suit.SPADES));
		testPairs.add(new Card(13, Suit.HEARTS));
		testPairs.add(new Card(12, Suit.SPADES));
		testPairs.add(new Card(12, Suit.DIAMONDS));
		testPairs.add(new Card(3, Suit.HEARTS));
		testPairs.add(new Card(4, Suit.SPADES));
		testPairs.add(new Card(5, Suit.SPADES));
		testPairs.add(new Card(6, Suit.HEARTS));
		testPairs.add(new Card(3, Suit.CLUBS));
		pokerLogic.setTotalHand(testPairs);
		pokerLogic.setPokerPlayer(new PokerPlayer("Carl"));
		
		assertTrue(pokerLogic.isFourOfAKind());
	}
	
	@Test
	public void testIsStraightFlush() throws Exception {
		PokerLogic pokerLogic = new PokerLogic();
		ArrayList<Card> testPairs = new ArrayList<Card>();
		testPairs.add(new Card(1, Suit.CLUBS));
		testPairs.add(new Card(2, Suit.SPADES));
		testPairs.add(new Card(3, Suit.DIAMONDS));
		testPairs.add(new Card(3, Suit.SPADES));
		testPairs.add(new Card(13, Suit.HEARTS));
		testPairs.add(new Card(12, Suit.SPADES));
		testPairs.add(new Card(12, Suit.DIAMONDS));
		testPairs.add(new Card(3, Suit.HEARTS));
		testPairs.add(new Card(4, Suit.SPADES));
		testPairs.add(new Card(5, Suit.SPADES));
		testPairs.add(new Card(6, Suit.HEARTS));
		testPairs.add(new Card(3, Suit.CLUBS));
		testPairs.add(new Card(6, Suit.SPADES));
		testPairs.add(new Card(3, Suit.SPADES));
		pokerLogic.setTotalHand(testPairs);
		pokerLogic.setPokerPlayer(new PokerPlayer("Carl"));
		
		assertTrue(pokerLogic.isStraightFlush());
	}
	
	@Test
	public void testWithPlayerStraightFlush() throws Exception {
		PokerLogic pl = new PokerLogic();
		ArrayList<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card(1, Suit.HEARTS));
		tableCards.add(new Card(13, Suit.HEARTS));
		tableCards.add(new Card(12, Suit.HEARTS));
		tableCards.add(new Card(1, Suit.CLUBS));
		tableCards.add(new Card(7, Suit.SPADES));
		ArrayList<Card> playersHand = new ArrayList<Card>();
		playersHand.add(new Card(11, Suit.HEARTS));
		playersHand.add(new Card(10, Suit.HEARTS));
		PokerPlayer player = new PokerPlayer("Carl");
		player.setHand(playersHand);
		
		BestHand actual = pl.getBestHand(player, tableCards);
		
		assertEquals(BestHand.STRAIGHT_FLUSH, actual);
	}
	
	@Test
	public void testWithPlayerFullHouse() throws Exception {
		PokerLogic pl = new PokerLogic();
		ArrayList<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card(1, Suit.HEARTS));
		tableCards.add(new Card(13, Suit.HEARTS));
		tableCards.add(new Card(12, Suit.HEARTS));
		tableCards.add(new Card(1, Suit.CLUBS));
		tableCards.add(new Card(1, Suit.SPADES));
		ArrayList<Card> playersHand = new ArrayList<Card>();
		playersHand.add(new Card(12, Suit.CLUBS));
		playersHand.add(new Card(10, Suit.HEARTS));
		PokerPlayer player = new PokerPlayer("Carl");
		player.setHand(playersHand);
		
		BestHand actual = pl.getBestHand(player, tableCards);
		
		assertEquals(BestHand.FULL_HOUSE, actual);
	}
	
	@Test
	public void testWithMultiplePlayers() throws Exception {
		PokerLogic pl = new PokerLogic();
		PokerLogic pl2 = new PokerLogic();
		ArrayList<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card(1, Suit.DIAMONDS));
		tableCards.add(new Card(7, Suit.SPADES));
		tableCards.add(new Card(9, Suit.DIAMONDS));
		tableCards.add(new Card(4, Suit.SPADES));
		tableCards.add(new Card(11, Suit.SPADES));
		ArrayList<Card> playersHand = new ArrayList<Card>();
		playersHand.add(new Card(2, Suit.DIAMONDS));
		playersHand.add(new Card(7, Suit.DIAMONDS));
		PokerPlayer player = new PokerPlayer("Carl");
		player.setHand(playersHand);
		
		BestHand actual = pl.getBestHand(player, tableCards);
		System.out.println(actual);
		//assertEquals(BestHand.FULL_HOUSE, actual);
		
		ArrayList<Card> cpuHand = new ArrayList<Card>();
		cpuHand.add(new Card(1, Suit.HEARTS));
		cpuHand.add(new Card(7, Suit.CLUBS));
		PokerPlayer cpu = new PokerPlayer("CPU");
		cpu.setHand(cpuHand);
		BestHand cpuActual = pl2.getBestHand(cpu, tableCards);
		System.out.println(cpuActual);
	}
}
