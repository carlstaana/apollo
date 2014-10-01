package com.apollo.training.games.cardgames.poker;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import com.apollo.training.book.chapter1.NamePrinter;
import com.apollo.training.games.GameFunction;
import com.apollo.training.games.cardgames.Card;
import com.apollo.training.games.cardgames.Deck;

public class Poker extends GameFunction {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String input;
		boolean valid = false;
		
		
		// START
		System.out.println("Welcome to Java Poker!");
		delay(1500);
		clearScreen();
		
		int numberOfPlayers = 0;
		while (!valid) {
			// ask for players
			System.out.print("How many players do you want to play? (8 Players MAX): ");
			
			try {
				input = in.nextLine();
				
				if (input == null || input.equalsIgnoreCase("")) {
					throw new NoSuchElementException("Error! Please enter a valid input.");
				}
				
				numberOfPlayers = Integer.parseInt(input);
				
				if (numberOfPlayers < 1 || numberOfPlayers > 8) {
					throw new NumberFormatException("Please enter a valid input and try again.");
				}
				
				break;
			} catch (NoSuchElementException | NumberFormatException e) {
				System.out.println(e.getMessage());
				delay(1000);
				clearScreen();
				continue;
			}
		}
		
		int numberOfCpus = 0;
		while (!valid) {
			// ask for CPUs
			System.out.print("Do you want to fill CPUs in the table? (Default = 1) [Y/n] ");
			
			try {
				input = in.nextLine();
				
				if (input ==  null || input.equalsIgnoreCase("")) {
					throw new NoSuchElementException("NULL. Please try again.");
				} else if (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
					throw new NoSuchElementException("Error! Please enter an appropriate input.");
				}
				
				if (input.equalsIgnoreCase("y")) {
					numberOfCpus = 8 - numberOfPlayers;
				} else if (input.equalsIgnoreCase("n") && numberOfPlayers == 1) {
					numberOfCpus = 1;
				}
				
				break;
			} catch (NoSuchElementException e) {
				System.out.println(e.getMessage());
				delay(1000);
				clearScreen();
				continue;
			}
		}
		
		PokerTable table = new PokerTable(numberOfPlayers, numberOfCpus);
		delay(1000);
		
		boolean isContinue = false;
		do {
			clearScreen();
			System.out.println("Shuffling cards...");
			delay(1000);
			clearScreen();
			System.out.println("Dealing cards...");
			delay(1000);
			clearScreen();
			
			do {
				newDeal(table);
				firstRound(table);
				if (!stillPlaying(table)) {
					table.getDefaultWinner();
					break;
				}
				table.revealInitialCards();
				succeedingRounds(table);
				if (!stillPlaying(table)) {
					table.getDefaultWinner();
					break;
				}
				table.addCard();
				succeedingRounds(table);
				if (!stillPlaying(table)) {
					table.getDefaultWinner();
					break;
				}
				table.addCard();
				succeedingRounds(table);
				if (!stillPlaying(table)) {
					table.getDefaultWinner();
					break;
				}

				clearScreen();
				NamePrinter np = new NamePrinter();
				np.print("SUMMARY");
				table.compute();
				table.getWinner();
				break;
			} while (stillPlaying(table));
			
			while (!valid) {
				System.out.print("Do you want to continue? [Y/n] ");
				
				try {
					input = in.nextLine();
					
					if (input == null || input.equalsIgnoreCase("")) {
						throw new NoSuchElementException("Your input is NULL. Please try again");
					} else if (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
						throw new NoSuchElementException("Your input is inappropriate. Please try again");
					} else if (input.equalsIgnoreCase("y")) {
						isContinue = true;		
					} else if (input.equalsIgnoreCase("n")) {
						isContinue = false;
					}
					
					break;
					
				} catch (NoSuchElementException e) {
					System.out.println(e.getMessage());
					delay(1000);
					clearScreen();
					continue;
				}
			}
			
			if (isContinue) {
				continue;
			}
		} while (isContinue && table.getPlayers().get(0).getMoney() > 0);
		
		delay(1000);
		clearScreen();
		System.out.println("Goodbye!");
		System.exit(1);
	}

	private static boolean stillPlaying(PokerTable table) {
		int count = 0;
		
		for (PokerPlayer pokerPlayer : table.getPlayers()) {
			if (!pokerPlayer.isFolded()) {
				count++;
			}
		}
		
		return count > 1;
	}

	private static void succeedingRounds(PokerTable table) {
		Scanner in = new Scanner(System.in);
		String input;
		boolean valid = false;

		do {
			for (PokerPlayer pokerPlayer : table.getPlayers()) {
				if (pokerPlayer.getMoney() >= table.getBASE_BET() && !pokerPlayer.isFolded() && (pokerPlayer.getCurrentBet() < table.getHighestBet() || pokerPlayer.getCurrentBet() <= 0)) {
					if (pokerPlayer.isAi()) {
						clearScreen();
					}
					System.out.println("--\n"
							+ pokerPlayer.getName() + "'s Turn");
					delay(1000);
					System.out.println("Pot Money: " + table.getPotMoney());
					System.out.println("Money: " + pokerPlayer.getMoney());
					System.out.println("Current bet: " + pokerPlayer.getCurrentBet());
					delay(1000);
					
					int choice = 0;

					if (!pokerPlayer.isAi()) {
						if (table.getHumanCountUnfolded() > 1) {
							System.out.println("Are you " + pokerPlayer.getName() + "?");
							pressAnyKeyToContinue();
							clearScreen();
						}
						
						while (!valid) {
							// show player's hand
							pokerPlayer.showHand();
							// show also player's highest attainment
							PokerLogic pk = new PokerLogic();
							System.out.println(pk.getBestHand(pokerPlayer, table.getTableCards()));
							
							if (pokerPlayer.getCurrentBet() <= 0 && table.getHighestBet() <= 0) {
								System.out.print("What do you want to do?\n"
										+ "[1] Check\n" 
										+ "[2] Raise (40)\n"
										+ "[3] Fold\n");
							} else {
								System.out.print("What do you want to do?\n"
									+ "[1] Call ("+table.getHighestBet()+")\n" 
									+ "[2] Raise ("+table.getHighestBet()+" + 40)\n"
									+ "[3] Fold\n");
							}

							try {
								input = in.nextLine();

								if (input == null || input.equalsIgnoreCase("")) {
									throw new NoSuchElementException("Null. "
											+ "Please enter an appropriate choice and try again.");
								}

								choice = Integer.parseInt(input);

								if (choice < 1 || choice > 3) {
									throw new NumberFormatException("Error. "
											+ "Please choose only from 1-3 and try again.");
								}

								break;
							} catch (NoSuchElementException | NumberFormatException e) {
								System.out.println(e.getMessage());
								delay(1000);
								clearScreen();
								continue;
							}
						}
					} else {
						if (table.getPlayers().get(0).isFolded()) {
							// show also player's highest attainment
							PokerLogic pk = new PokerLogic();
							System.out.println("["+pk.getBestHand(pokerPlayer, table.getTableCards())+"]");
							pokerPlayer.showHand();
							delay(1500);
						}
								
						// AI module for choosing
						Random random = new Random();
						double chance = random.nextDouble() * 100;
						PokerLogic pk = new PokerLogic();
						int tempOrdinal = pk.getBestHand(pokerPlayer, table.getTableCards()).ordinal();
						int callChance = 0;
						int raiseChance = 0;
						if (tempOrdinal == 8) {
							callChance = 40;
							raiseChance = 50;
						} else if (tempOrdinal < 8 && tempOrdinal >= 4) {
							callChance = 50;
							raiseChance = 80;
						} else if (tempOrdinal < 4) {
							callChance = 50;
							raiseChance = 95;
						}
						
						if (chance <= callChance) {
							choice = 1;
						} else if (chance > callChance && chance <= raiseChance) {
							choice = 2;
						} else if (chance > raiseChance) {
							choice = 3;
						}
					}

					switch (choice) {
					case 1:
						// CALL the Highest Bet
						if (pokerPlayer.getCurrentBet() > 0 || pokerPlayer.getCurrentBet() < table.getHighestBet()) {
							table.call(pokerPlayer);
						} else {
							NamePrinter printer = new NamePrinter();
							printer.print(pokerPlayer.getName() + " checked.");
						}
						break;
					case 2:
						// RAISE your current bet to 40
						table.raise(pokerPlayer);
						break;
					case 3:
						// FOLD
						table.fold(pokerPlayer);
						break;
					}
					delay(1200);
					clearScreen();
				} else if (pokerPlayer.getMoney() < table.getBASE_BET() && !pokerPlayer.isFolded()) {
					System.out.println(pokerPlayer.getName() + " is out of the game.");
					pokerPlayer.setFolded(true);
				}
			}
		} while (!table.hasEqualBets() && stillPlaying(table));
		
		for (PokerPlayer pokerPlayer : table.getPlayers()) {
			pokerPlayer.setCurrentBet(0);
		}
		table.setHighestBet(0);
	}

	private static void firstRound(PokerTable table) {
		Scanner in = new Scanner(System.in);
		String input;
		boolean valid = false;

		do {
			for (PokerPlayer pokerPlayer : table.getPlayers()) {
				if (pokerPlayer.getMoney() >= table.getBASE_BET() && !pokerPlayer.isFolded() && (pokerPlayer.getCurrentBet() < table.getHighestBet() || pokerPlayer.getCurrentBet() <= 0)) {
					if (pokerPlayer.isAi()) {
						clearScreen();
					}
					System.out.println("--\n"
							+ pokerPlayer.getName() + "'s Turn");
					delay(1000);
					System.out.println("Pot Money: " + table.getPotMoney());
					System.out.println("Money: " + pokerPlayer.getMoney());
					System.out.println("Current bet: " + pokerPlayer.getCurrentBet());
					delay(1000);
					
					int choice = 0;

					if (!pokerPlayer.isAi()) {
						if (table.getHumanCountUnfolded() > 1) {
							System.out.println("Are you " + pokerPlayer.getName() + "?");
							pressAnyKeyToContinue();
							clearScreen();
						}
						
						while (!valid) {
							pokerPlayer.showHand();
							if (pokerPlayer.getCurrentBet() <= 0 && table.getPotMoney() <= 0 && table.getHighestBet() <= 0) {
								System.out.print("What do you want to do?\n"
										+ "[1] Call (40)\n" 
										+ "[2] Raise (40 + 40)\n"
										+ "[3] Fold\n");
							} else {
								System.out.print("What do you want to do?\n"
									+ "[1] Call ("+table.getHighestBet()+")\n" 
									+ "[2] Raise ("+table.getHighestBet()+" + 40)\n"
									+ "[3] Fold\n");
							}
							
							try {
								input = in.nextLine();

								if (input == null || input.equalsIgnoreCase("")) {
									throw new NoSuchElementException("Null. "
											+ "Please enter an appropriate choice and try again.");
								}

								choice = Integer.parseInt(input);

								if (choice < 1 || choice > 3) {
									throw new NumberFormatException("Error. "
											+ "Please choose only from 1-3 and try again.");
								}

								break;
							} catch (NoSuchElementException | NumberFormatException e) {
								System.out.println(e.getMessage());
								delay(1000);
								clearScreen();
								continue;
							}
						}
					} else {
						if (table.getPlayers().get(0).isFolded()) {
							pokerPlayer.showHand();
							delay(1000);
						}
						
						// AI module for choosing
						Random random = new Random();
						double chance = random.nextDouble() * 100;
						PokerLogic pk = new PokerLogic();
						int tempOrdinal = pk.getBestHand(pokerPlayer, table.getTableCards()).ordinal();
						int callChance = 0;
						int raiseChance = 0;
						
						if (tempOrdinal == 8) {
							callChance = 60;
							raiseChance = 70;
						} else if (tempOrdinal < 8 && tempOrdinal >= 4) {
							callChance = 50;
							raiseChance = 80;
						} else if (tempOrdinal < 4) {
							callChance = 45;
							raiseChance = 90;
						}
						
						if (chance <= callChance) {
							choice = 1;
						} else if (chance > callChance && chance <= raiseChance) {
							choice = 2;
						} else if (chance > raiseChance) {
							choice = 3;
						}
					}

					switch (choice) {
					case 1:
						// CALL the Highest Bet
						table.call(pokerPlayer);
						break;
					case 2:
						// RAISE your current bet to 40
						table.raise(pokerPlayer);
						break;
					case 3:
						// FOLD
						table.fold(pokerPlayer);
						break;
					}
					delay(1200);
					clearScreen();
				} else if (pokerPlayer.getMoney() < table.getBASE_BET() && !pokerPlayer.isFolded()) {
					System.out.println(pokerPlayer.getName() + " is out of the game.");
					pokerPlayer.setFolded(true);
				}
			}
		} while (!table.hasEqualBets() && stillPlaying(table));
		
		for (PokerPlayer pokerPlayer : table.getPlayers()) {
			pokerPlayer.setCurrentBet(0);
		}
		table.setHighestBet(0);
	}

	private static void newDeal(PokerTable table) {
		table.setPotMoney(0);
		table.setDeck(new Deck());
		table.setHighestBet(0);
		table.setTableCards(new ArrayList<Card>());
		
		for (PokerPlayer pokerplayer : table.getPlayers()) {
			if (pokerplayer.getMoney() > table.getBASE_BET()) {
				pokerplayer.getHand().clear();
				pokerplayer.setHighestRank(0);
				pokerplayer.setFolded(false);
			}
		}
		
		int initialHand = 2;
		while (initialHand > 0) {
			for (PokerPlayer pokerPlayer : table.getPlayers()) {
				if (pokerPlayer.getMoney() > table.getBASE_BET()) {
					pokerPlayer.addCardToHand(table.getDeck().draw());
				}
			}
			
			initialHand--;
		}
	}

}
