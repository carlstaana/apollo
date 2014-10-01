package com.apollo.training.games.cardgames.freecell;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import com.apollo.training.book.chapter1.NamePrinter;
import com.apollo.training.games.GameFunction;
import com.apollo.training.games.cardgames.Card;
import com.apollo.training.games.cardgames.Deck;
import com.apollo.training.games.cardgames.Player;
import com.apollo.training.games.cardgames.Card.Suit;

public class FreeCell extends GameFunction {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String input = null;
		boolean valid = false;
		NamePrinter np = new NamePrinter();
		
		// players
		Deck deck = new Deck();
		System.out.println("Welcome to FreeCell! Let's play!");
		ArrayList<FreeCellPlayer> players = new ArrayList<FreeCellPlayer>();

		int numOfPlayers = 0;
		int numOfCpus;
		while (!valid) {
			System.out.print("Enter number of players (4 MAX): ");
			try {
				input = in.nextLine();
				numOfPlayers = Integer.parseInt(input);
				if (numOfPlayers < 0 || numOfPlayers > 4) {
					throw new NumberFormatException();
				}
				break;
			} catch (NoSuchElementException e) {
				System.out.println("Null, try again.");
				continue;
			} catch (NumberFormatException e) {
				System.out.println("Please enter a number from 1 to 4");
				continue;
			}
		}
		
		if (numOfPlayers == 0) {
			// an all-AI match
			np.print("All-AI Match!");
			delay(1500);
			for (int i = 0; i < 4; i++) {
				players.add(new FreeCellPlayer("CPU " + (i+1)));
			}
		} else {
			for (int i = 0; i < numOfPlayers; i++) {
				System.out.println("Player " + (i+1));
				FreeCellPlayer player = new FreeCellPlayer();
				players.add(player);
			}

			if (numOfPlayers < 4) {
				while (!valid) {
					System.out.print("Do you want to fill CPUs to make 4 players? [Y/n] ");
					try {
						input = in.next();
						if (input == null) {
							throw new NoSuchElementException();
						} else if (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
							throw new NoSuchElementException();
						} else if (input.equalsIgnoreCase("y")) {
							numOfCpus = 4 - numOfPlayers;
							for (int i = 0; i < numOfCpus; i++) {
								FreeCellPlayer cpu = new FreeCellPlayer("CPU " + (i+1));
								players.add(cpu);
							}

							break;
						} else if (input.equalsIgnoreCase("n") && numOfPlayers == 1) {
							System.out.println("The game will automatically add a CPU since you cannot play alone");
							players.add(new FreeCellPlayer("CPU"));
							input = null;

							break;
						}
					} catch (NoSuchElementException e) {
						System.out.println("Error, try again.");
						continue;
					}
				}
			}
		}
		
		distributeToPlayers(deck, players, 5);
		System.out.println("--START--");
		
		// get the base card
		Card baseCard = deck.draw();
		
		
		while (stillPlaying(players)) {
			System.out.println("\nBase Card: " + baseCard.toString());
			delay(1000);
			
			for (FreeCellPlayer player : players) {
				if (!player.isOriginOfBaseCard()) {
					player.setThrownCard(null);
				}
			}
			
			for (FreeCellPlayer player : players) {
				if (!player.isWin()) {
					if (!player.isOriginOfBaseCard()) {
						System.out.println("\n--");
						System.out.println(player.getName() + "'s Turn");
						if (!player.isAi()) {
							if (countValidPlayers(players) > 1) {
								System.out.println("Are you " + player.getName() + "?");
								pressAnyKeyToContinue();
							}
							
							int selectedCard;
							sortHand(player);
							
							if (checkIfHandsHasSameSuit(player, baseCard)) {
								while (!valid) {
									System.out.println(player.showHand());
									System.out.print("Pick a card with the suit of " + baseCard.getSuit() + ": ");
									
									try {
										input = in.nextLine();
										if (input == null || input.equalsIgnoreCase("")) {
											throw new NullPointerException();
										}
										
										selectedCard = Integer.parseInt(input);
										if (selectedCard < 1 || selectedCard > player.getHand().size()) {
											throw new NumberFormatException();
										} else {
											if (!hasSameSuit(player.getHand().get(selectedCard-1), baseCard)) {
												throw new NumberFormatException();
											} else {
												useCard(player, selectedCard, deck);
												checkIfWinner(player);
											}
										}
										
										break;
									} catch (NullPointerException | NumberFormatException e) {
										System.out.println("Try again");
										clearScreen();
										continue;
									}
								}
							} else {
								if (deck.isEmpty()) {
									addCardPile(player, players, deck);
									break;
								} else {
									drawUntilYouGotIt(players, deck, player, baseCard);
									if (deck.isEmpty()) {
										break;
									}
								}
							}
							
							// Press Any Key to Continue...
							if (countValidPlayers(players) > 1) {
								System.out.println("--END TURN--");
								pressAnyKeyToContinue();
								clearScreen();
							}
						} else {
							if (checkIfHandsHasSameSuit(player, baseCard)) {
								aiDropCard(player, baseCard, deck);
								checkIfWinner(player);
							} else {
								if (deck.isEmpty()) {
									addCardPile(player, players, deck);
									break;
								} else {
									drawUntilYouGotIt(players, deck, player, baseCard);
									if (deck.isEmpty()) {
										break;
									}
								}
							}
						}
					}
				}	
			}
			
			if (!stillPlaying(players)) {
				break;
			}
			
			winnerForThisRound(players);
			deck.getCardPile().clear();
			
			for (FreeCellPlayer player : players) {
				if (player.isOriginOfBaseCard() && !player.isWin()) {
					System.out.println("\n" + player.getName() + " wins this round!");
					delay(1000);
					
					if (!player.isAi()) {
						System.out.println("Are you " + player.getName() + "?");
						pressAnyKeyToContinue();
						
						sortHand(player);
						System.out.println(player.showHand());
						int selectedCard = 0;
						
						while (!valid) {
							System.out.println("Pick a new base card:");
						
							try {
								input = in.nextLine();
								
								if (input.equalsIgnoreCase("")) {
									throw new NullPointerException();
								}
								
								selectedCard = Integer.parseInt(input);
								
								if (selectedCard < 1 || selectedCard > player.getHand().size()) {
									throw new NumberFormatException();
								}
								
								break;
							} catch (NullPointerException e) {
								System.out.println("Try again.");
								continue;
							} catch (NumberFormatException e) {
								System.out.println("Try again.");
								continue;
							}
						}
						
						useCard(player, selectedCard, deck);
						baseCard = player.getThrownCard();
						checkIfWinner(player);
					} else {
						int selectedCard = -1;
						Random random = new Random();

						while (selectedCard < 0) {
							selectedCard = random.nextInt(player.getHand().size());
						}
						
						if (!player.isWin()) {
							useCard(player, selectedCard + 1, deck);
							baseCard = player.getThrownCard();
							checkIfWinner(player);
						}
					}
					break;
				}
			}
			
			// sort players
			sortPlayers(players);
		}
		
		for (Player player : players) {
			if (player.getHand().size() > 0) {
				System.out.println("\n--END--\n"
						+ player.getName() + " lost...");
			}
		}
		
		in.close();
	}

	private static int countValidPlayers(ArrayList<FreeCellPlayer> players) {
		int count = 0;
		for (FreeCellPlayer freeCellPlayer : players) {
			if (!freeCellPlayer.isAi() && !freeCellPlayer.isWin()) {
				count++;
			}
		}
		return count;
	}

	private static void distributeToPlayers(Deck deck,
			ArrayList<FreeCellPlayer> players, int numOfCards) {
		while (numOfCards > 0) {
			for (FreeCellPlayer freeCellPlayer : players) {
				freeCellPlayer.getHand().add(deck.draw());
			}
			numOfCards--;
		}
	}

	private static void addCardPile(FreeCellPlayer player, ArrayList<FreeCellPlayer> players, Deck deck) {
		System.out.println(player.getName() + " will get the card/s from the card pile");
		delay(1500);
		
		for (Card cardInPile : deck.getCardPile()) {
			player.getHand().add(cardInPile);
		}
		
		player.setThrownCard(null);
	}

	private static void sortPlayers(ArrayList<FreeCellPlayer> players) {
		int playerIndex = 0;
		ArrayList<FreeCellPlayer> sortedPlayers = new ArrayList<FreeCellPlayer>();
		int count = 0;
		
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isOriginOfBaseCard()) {
				playerIndex = i;
				break;
			}
		}
		
		count = playerIndex;
		do {
			sortedPlayers.add(players.get(count));
			count++;
			if (count > players.size()-1) {
				count = 0;
			}
		} while (count != playerIndex);
		
		players.clear();
		players.addAll(sortedPlayers);
	}

	private static void sortHand(Player player) {
		// sorts the cards by rank A to K and by suit hearts, spades, clubs, diamonds
		ArrayList<Card> newHand = new ArrayList<Card>();
		
		// suitnumbers 0 - hearts, 1 - spades, 2 - clubs, 3 - diamonds
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 13; i++) {
				Card compareCard = new Card();
				compareCard.setValue(i+1);
				
				switch (j) {
				case 0:
					compareCard.setSuit(Suit.HEARTS);
					break;
				case 1:
					compareCard.setSuit(Suit.SPADES);
					break;
				case 2:
					compareCard.setSuit(Suit.CLUBS);
					break;
				case 3:
					compareCard.setSuit(Suit.DIAMONDS);
					break;
				}
				
				if (player.hasThisCard(compareCard)) {
					newHand.add(compareCard);
				}
			}
		}
		
		
		player.getHand().clear();
		player.getHand().addAll(newHand);
	}

	private static void checkIfWinner(FreeCellPlayer player) {
		if (player.getHand().size() <= 0 && !player.isWin()) {
			System.out.println(player.getName() + " has no cards left.\n" + player.getName() + " wins.");
			player.setWin(true);
			player.setOriginOfBaseCard(false);
			//player.setThrownCard(null);
			delay(1000);
		}
	}

	private static boolean stillPlaying(ArrayList<FreeCellPlayer> players) {
		int count = 0;
		
		for (FreeCellPlayer player : players) {
			if (player.getHand().size() > 0) {
				count++;
			}
		}
		
		if (count > 1) {
			return true;
		} else {
			return false;
		}
	}

	private static void winnerForThisRound(ArrayList<FreeCellPlayer> players) {
		// temporary clear screen
		clearScreen();
		// tally throws
		System.out.println("\n[Summary]");
		for (FreeCellPlayer player : players) {
			if (!player.isWin()) {
				if (player.getThrownCard() != null) {
					System.out.println(player.getName() + ": " + player.getThrownCard().toString());
				} else {
					System.out.println(player.getName() + ": NONE");
				}
			}
		}
		delay(2000);
		
		Card highestCard = getHighestCard(players);
		
		for (FreeCellPlayer player : players) {
			if (!player.isWin()) {
				player.setOriginOfBaseCard(false);
				
				if (highestCard == null && player.getThrownCard() == null) {
					player.setOriginOfBaseCard(true);
					break;
				} else {
					if (highestCard.equals(player.getThrownCard())) {
						player.setOriginOfBaseCard(true);
					}
				}
				
				ArrayList<Card> newHand = new ArrayList<Card>();
				for (Card card : player.getHand()) {
					if (card != null) {
						newHand.add(card);
					}
				}
				player.getHand().clear();
				player.getHand().addAll(newHand);
			}/* else {
				if (player.equals(players.get(0)) && players.get(0).isOriginOfBaseCard()) {
					players.get(1).setOriginOfBaseCard(true);
					break;
				}
			}*/	
		}
	}

	private static Card getHighestCard(ArrayList<FreeCellPlayer> players) {
		Card output = null;
		
		for (FreeCellPlayer player : players) {
			if (!player.isWin()) {
				if (output == null) {
					output = player.getThrownCard();
					continue;
				}
				
				if (player.getThrownCard() != null) {
					if (output.isAce()) {
						return output;
					} else if (!output.isAce() && player.getThrownCard().isAce()) {
						output = player.getThrownCard();
						return output;
					} else if (!output.isAce() && !player.getThrownCard().isAce() && output.getValue() > player.getThrownCard().getValue()) {
						// do nothing
					} else if (!output.isAce() && !player.getThrownCard().isAce() && output.getValue() < player.getThrownCard().getValue()) {
						output = player.getThrownCard();
					}
				}
			}
		}
		
		return output;
	}

	private static void aiDropCard(FreeCellPlayer player, Card baseCard, Deck deck) {
		for (int i = 0; i < player.getHand().size(); i++) {
			if (player.getHand().get(i).getSuit().equals(baseCard.getSuit())) {
				useCard(player, i+1, deck);
				break;
			}
		}
	}

	private static void drawUntilYouGotIt(ArrayList<FreeCellPlayer> players, Deck deck, FreeCellPlayer player,
			Card baseCard) {
		boolean deckDepleted = false;
		
		while (!checkIfHandsHasSameSuit(player, baseCard)) {
			System.out.println(player.getName() + " has no " + baseCard.getSuit() + " in his/her hand. " + player.getName() + " will draw.");
			delay(1000);
			if (deck.getDeck().size() > 0) {
				player.getHand().add(deck.draw());
				if (!player.isAi()) {
					System.out.println(player.getName() + " got " + player.getHand().get(player.getHand().size() - 1).toString());
				}
			} else {
				System.out.println("No cards anymore in the deck.");
				addCardPile(player, players, deck);
				deckDepleted = true;
				break;
			}
		}
		
		if (!deckDepleted) {
			useCard(player, player.getHand().indexOf(player.getHand().get(player.getHand().size() - 1)) + 1, deck);
		}
		
	}

	private static void useCard(FreeCellPlayer player, int selectedCard, Deck deck) throws NullPointerException {
		NamePrinter np = new NamePrinter();
		player.setThrownCard(player.getHand().get(selectedCard-1));
		player.getHand().remove(selectedCard-1);
		np.print(player.getName() + " uses " + player.getThrownCard().toString());
		deck.getCardPile().add(player.getThrownCard());
		delay(1000);
	}

	private static boolean checkIfHandsHasSameSuit(Player player,
			Card baseCard) {
		for (int i = 0; i < player.getHand().size(); i++) {
			Card card = new Card();
			card = player.getHand().get(i);
			//if (card != null) {
				if (hasSameSuit(card, baseCard)) {
					return true;
				}
			//}
		}
		return false;
	}

	private static boolean hasSameSuit(Card card, Card baseCard) {
		if (card.getSuit().toString().equalsIgnoreCase(baseCard.getSuit().toString())) {
			return true;
		} else {
			return false;
		}
	}
}
