package com.apollo.training.games.cardgames.pairs;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import com.apollo.training.games.GameFunction;
import com.apollo.training.games.cardgames.Card;
import com.apollo.training.games.cardgames.Card.Suit;
import com.apollo.training.games.cardgames.Deck;

public class Pairs extends GameFunction {

	public static void main(String[] args) {
		Random random = new Random();
		Scanner in = new Scanner(System.in);
		String input;
		boolean valid = false;
		
		// START
		System.out.println("Welcome to the Game of Pairs!");
		delay(2000);
		clearScreen();
		
		// the deck
		Deck deck = new Deck();
		
		// add new players
		ArrayList<PairsPlayer> players = new ArrayList<PairsPlayer>();
		for (int i = 0; i < 4; i++) {
			/*if (i <= 0) {
				players.add(new PairsPlayer());
			} else {
				players.add(new PairsPlayer("CPU " + i));
			}*/
			
			// ALL AI
			players.add(new PairsPlayer("CPU " + i));
		}
		
		// remove a card from the deck
		Card drawnCard = deck.draw();
		
		// distribute cards to players
		// the first card will be given to a random player
		players.get(random.nextInt(players.size())).addCardToHand(deck.draw());
		
		// then distribute the cards evenly
		System.out.println("Dealing cards...");
		delay(1000);
		clearScreen();
		while (!deck.isEmpty()) {
			for (PairsPlayer pairsPlayer : players) {
				pairsPlayer.addCardToHand(deck.draw());
			}
		}
		
		// now remove pairs in hand per player
		System.out.println("Removing pairs...");
		delay(1000);
		clearScreen();
		removePairs(players);
		
		while (stillPlaying(players)) {
			for (int i = 0; i < players.size(); i++) {
				PairsPlayer selectedPlayer = players.get(i);

				if (!selectedPlayer.getHand().isEmpty() && !selectedPlayer.isWin()) {
					int indexOfNextPlayer = i + 1;
					if (indexOfNextPlayer >= players.size()) {
						indexOfNextPlayer = 0;
					}
					
					while (players.get(indexOfNextPlayer).getHand().isEmpty() || indexOfNextPlayer == i) {
						indexOfNextPlayer++;
						
						if (indexOfNextPlayer >= players.size()) {
							indexOfNextPlayer = 0;
						}
					}
					
					clearScreen();
					System.out.println("---\n" + selectedPlayer.getName() + "'s Turn");
					if (!selectedPlayer.isAi()) {
						int selectedCard = 0;
						
						while (!valid) {
							System.out.println(selectedPlayer.showHand());
							System.out.println("--\n");
							showNextPlayersHand(players.get(indexOfNextPlayer));
							System.out.print("\nPick one of the cards on the opponent's hand. "
									+ "Type the number of the card: ");
							try {
								input = in.nextLine();
								if (input == null || input.equalsIgnoreCase("")) {
									throw new NoSuchElementException("You did not type anything. "
											+ "Please try again.");
								}
								
								selectedCard = Integer.parseInt(input);
								if (selectedCard < 1 || selectedCard > players.get(indexOfNextPlayer).getHand().size()) {
									throw new NumberFormatException("You have typed a non-existing number. "
											+ "Please try again.");
								}
								
								break;
							} catch (NoSuchElementException | NumberFormatException e) {
								System.out.println(e.getMessage());
								delay(1000);
								continue;
							}
						}
						
						selectedPlayer.getCardFromOtherPlayer(players.get(indexOfNextPlayer), selectedCard-1);
						removePairs(players);
					} else {
						if (players.get(0).isWin()) {
							System.out.println(selectedPlayer.showHand());
						}
						
						selectedPlayer.getCardFromOtherPlayer(players.get(indexOfNextPlayer), random.nextInt(players.get(indexOfNextPlayer).getHand().size()));
						removePairs(players);
					}
					
					if (selectedPlayer.getHand().isEmpty() && !players.get(indexOfNextPlayer).getHand().isEmpty()) {
						System.out.println("\n" + selectedPlayer.getName() + " has no cards left.\n"
								+ selectedPlayer.getName() + " wins!");
						selectedPlayer.setWin(true);
						delay(2000);
					}
				} else if (selectedPlayer.getHand().isEmpty() && !selectedPlayer.isWin()) {
					System.out.println(selectedPlayer.getName() + " has no cards left.\n"
							+ selectedPlayer.getName() + " wins!");
					selectedPlayer.setWin(true);
					delay(2000);
				}
				
				if (!stillPlaying(players)) {
					break;
				}
			}
		}
		
		for (PairsPlayer pairsPlayer : players) {
			if (!pairsPlayer.isWin()) {
				System.out.println(pairsPlayer.showHand());
				System.out.println("\nThe hidden card is: " + drawnCard.toString());
				System.out.println(pairsPlayer.getName() + " lost...");
				in.close();
				System.exit(1);
			}
		}
	}

	
	
	private static void showNextPlayersHand(PairsPlayer pairsPlayer) {
		System.out.println(pairsPlayer.getName() + "'s Hand:");
		for (int i = 0; i < pairsPlayer.getHand().size(); i++) {
			System.out.println("["+(i+1)+"] " + "\uD83C\uDCCF");
		}
	}

	private static boolean stillPlaying(ArrayList<PairsPlayer> players) {
		int count = 0;
		
		for (PairsPlayer pairsPlayer : players) {
			if (!pairsPlayer.getHand().isEmpty()) {
				count++;
			}
		}
		
		return count > 1;
	}

	private static void removePairs(ArrayList<PairsPlayer> players) {
		for (PairsPlayer pairsPlayer : players) {
			if (!pairsPlayer.isAi()) {
				sortHand(pairsPlayer);
			}
			
			ArrayList<Card> pairCards = new ArrayList<Card>();
			for (Card card : pairsPlayer.getHand()) {
				for (Card otherCard : pairsPlayer.getHand()) {
					if (!card.equals(otherCard) && card.getValue() == otherCard.getValue() && !pairCards.contains(card) && !pairCards.contains(otherCard)) {
						System.out.println("["+pairsPlayer.getName()+"] " + card.toString() + " and " + otherCard.toString() + " are pairs.\n"
								+ "Removing...");
						delay(500);
						pairCards.add(card);
						pairCards.add(otherCard);
					}
				}
			}
			
			for (Card card : pairCards) {
				pairsPlayer.getHand().remove(card);
			}
			
			pairCards.clear();
		}
	}

	private static void sortHand(PairsPlayer pairsPlayer) {
		ArrayList<Card> newHand = new ArrayList<Card>();
		
		for (int i = 1; i <= 13; i++) {
			for (Suit suit : Suit.values()) {
				Card compareCard = new Card();
				compareCard.setSuit(suit);
				compareCard.setValue(i);

				if (pairsPlayer.hasThisCard(compareCard)) {
					newHand.add(compareCard);
				}
			}
		}
		
		pairsPlayer.getHand().clear();
		pairsPlayer.getHand().addAll(newHand);
	}
}
