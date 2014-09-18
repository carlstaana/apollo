package com.apollo.training.games.cardgames.luckynine;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.apollo.training.games.GameFunction;
import com.apollo.training.games.cardgames.Card;
import com.apollo.training.games.cardgames.Deck;
import com.apollo.training.games.cardgames.Player;

public class LuckyNine extends GameFunction {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String input = null;
		boolean valid = false;
		boolean again = false;
		int difficulty = 0;
		int raceTo = 0;
		
		System.out.println("Hello! Let's play Lucky Nine!");
		// add 4 players
		ArrayList<LuckyNinePlayer> players =  new ArrayList<LuckyNinePlayer>();
		for (int i = 0; i < 4; i++) {
			LuckyNinePlayer player;
			
			if (i == 0) {
				player = new LuckyNinePlayer();
			} else {
				player = new LuckyNinePlayer("CPU" + i);
			}
			
			players.add(player);
		}
		
		
	
		while (!valid) {
			System.out.println("\nSelect Difficulty:\n"
					+ "[1] Easy\n"
					+ "[2] Medium\n"
					+ "[3] Hard");
			
			try {
				input = in.nextLine();
				difficulty = Integer.parseInt(input);
				
				if (difficulty < 1 || difficulty > 3) {
					throw new NullPointerException();
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
		
		while (!valid) {
			System.out.println("Race to: ");
			
			try {
				input = in.nextLine();
				raceTo = Integer.parseInt(input);
				
				if (raceTo <= 0) {
					throw new NumberFormatException();
				}
				
				break;
			} catch (NullPointerException e) {
				System.out.println("Try again.");
				continue;
			} catch (NumberFormatException e) {
				System.out.println("Error input. Try again.");
				continue;
			}
		}
		
		while (!again) {
			Deck deck = new Deck();
			distributeToPlayers(deck, players, 2);
			computeHandTotal(players);
			while (!valid) {
				System.out.println(players.get(0).showHand());
/*				System.out.println("(Secret)\n" + players.get(1).showHand());
				System.out.println("(Secret)\n" + players.get(2).showHand());
				System.out.println("(Secret)\n" + players.get(3).showHand());*/
				System.out.print("Do you want to add another card? [y/n] ");
				try {
					input = in.nextLine();
					if (input.equalsIgnoreCase("y")) {
						players.get(0).getHand().add(deck.draw());						
						System.out.println(players.get(0).showHand());
						break;
					} else if (input.equalsIgnoreCase("n")) {
						break;
					} else {
						throw new NullPointerException();
					}
				} catch (NullPointerException e) {
					System.out.println("Try again.");
					continue;
				}
			}
			
			// CPUs move
			// if < 4 draw again, else don't draw anymore
			for (int i = 1; i < players.size(); i++) {
				LuckyNinePlayer cpu = players.get(i);
				double decisionLevel = 0;
				
				switch (difficulty) {
				case 1:
					decisionLevel = 0;
					break;
				case 2:
					decisionLevel = 50;
					break;
				case 3:
					decisionLevel = 100;
					break;
				}
				
				if (cpu.getHandTotal() < 4 && chanceActivated(decisionLevel)) {
					System.out.println(cpu.getName() + " has drawn another card.");
					
					delay(700);
					
					cpu.getHand().add(deck.draw());
				} else {
					System.out.println(cpu.getName() + " did not draw again.");
					
					delay(700);
				}
			}
			
			System.out.println();
			computeHandTotal(players);
			computeHighest(players);
			
			// display scores
			System.out.println("\n--Scores--");
			for (LuckyNinePlayer player2 : players) {
				System.out.println(player2.getName() + ": " + player2.getScore());
			}
			
			if (getHighestScore(players) < raceTo) {
				while (!valid) {
					System.out.print("\nDo you want to try again? [y/n] ");
					
					try {
						input = in.nextLine();
						
						if (input.equalsIgnoreCase("y")) {
							for (Player player2 : players) {
								player2.setHand(new ArrayList<Card>());
							}
							break;
						} else if (input.equalsIgnoreCase("n")) {
							System.out.println("Goodbye!");
							System.exit(1);
						} else {
							throw new NullPointerException();
						}
					} catch (NullPointerException e) {
						System.out.println("Error input, try again.");
						continue;
					}
				}
			} else {
				System.out.println("\n--END--\nUltimate Winner: " + getWinner(players));
				break;
			}
		}
	}

	private static void distributeToPlayers(Deck deck,
			ArrayList<LuckyNinePlayer> players, int numOfCards) {
		int count = numOfCards;
		while (count > 0) {
			for (LuckyNinePlayer luckyNinePlayer : players) {
				luckyNinePlayer.addCardToHand(deck.draw());
			}
			count--;
		}
	}

	private static String getWinner(ArrayList<LuckyNinePlayer> players) {
		int score = getHighestScore(players);
		for (LuckyNinePlayer player : players) {
			if (player.getScore() == score) {
				return player.getName();
			}
		}
		return null;
	}

	private static int getHighestScore(ArrayList<LuckyNinePlayer> players) {
		int highscore = players.get(0).getScore();
		for (int i = 1; i < players.size(); i++) {
			if (players.get(i).getScore() > highscore) {
				highscore = players.get(i).getScore();
			}
		}
		return highscore;
	}

	private static boolean chanceActivated(double chance) {
		Random random = new Random();
		double output = random.nextDouble() * 100;
		if (output <= chance) {
			return true;
		} else {
			return false;
		}
		
	}

	private static void computeHighest(ArrayList<LuckyNinePlayer> players) {
		int playerIndex = 0;
		boolean tie = false;
		displayNameAndScore(players.get(0));
		for (int i = 1; i < players.size(); i++) {
			displayNameAndScore(players.get(i));
			if (players.get(i).getHandTotal() > players.get(playerIndex).getHandTotal()) {
				playerIndex = i;
			}
		}
		
		for (int i = 0; i < players.size(); i++) {
			if (i != playerIndex && players.get(i).getHandTotal() == players.get(playerIndex).getHandTotal()) {
				tie = true;
			}
		}
		
		delay(1500);
		
		if (tie) {
			System.out.println("\nIt's a TIE! No scores added");
		} else {
			System.out.println("\nWinner: " + players.get(playerIndex).getName());
			players.get(playerIndex).setScore(players.get(playerIndex).getScore() + 1);
		}
		
	}

	private static void displayNameAndScore(LuckyNinePlayer player) {
		System.out.println(player.getName() + ": " + player.getHandTotal());
	}

	private static void computeHandTotal(ArrayList<LuckyNinePlayer> players) {
		for (LuckyNinePlayer player : players) {
			player.setHandTotal(0);
			for (Card card : player.getHand()) {
				if (card.getValue() > 9) {
					player.setHandTotal(player.getHandTotal() + 10);
				} else {
					player.setHandTotal(player.getHandTotal() + card.getValue());
				}
			}
			if (player.getHandTotal() > 9) {
				player.setHandTotal(player.getHandTotal() % 10);
			}
		}
	}
}
