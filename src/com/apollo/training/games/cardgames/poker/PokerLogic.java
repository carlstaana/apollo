package com.apollo.training.games.cardgames.poker;

import java.util.ArrayList;

import com.apollo.training.games.cardgames.Card;
import com.apollo.training.games.cardgames.Card.Suit;

public class PokerLogic {
	
	private ArrayList<Card> totalHand = new ArrayList<Card>();
	
	private PokerPlayer pokerPlayer = null;
	
	public enum BestHand {
		STRAIGHT_FLUSH,
		FOUR_OF_A_KIND,
		FULL_HOUSE,
		FLUSH,
		STRAIGHT,
		THREE_OF_A_KIND,
		TWO_PAIR,
		ONE_PAIR,
		HIGH_CARD }

	public BestHand getBestHand(PokerPlayer pokerPlayer, ArrayList<Card> tableCards) {
		this.pokerPlayer = null;
		this.pokerPlayer = pokerPlayer;
		
		totalHand.clear();
		totalHand.addAll(pokerPlayer.getHand());
		totalHand.addAll(tableCards);
		
		if (isStraightFlush()) {
			pokerPlayer = this.pokerPlayer;
			return BestHand.STRAIGHT_FLUSH;
		} else if (isFourOfAKind()) {
			pokerPlayer = this.pokerPlayer;
			return BestHand.FOUR_OF_A_KIND;
		} else if (isFullHouse()) {
			pokerPlayer = this.pokerPlayer;
			return BestHand.FULL_HOUSE;
		} else if (isFlush()) {
			pokerPlayer = this.pokerPlayer;
			return BestHand.FLUSH;
		} else if (isStraight()) {
			pokerPlayer = this.pokerPlayer;
			return BestHand.STRAIGHT;
		} else if (isThreeOfAKind()) {
			pokerPlayer = this.pokerPlayer;
			return BestHand.THREE_OF_A_KIND;
		} else if (isTwoPair()) {
			pokerPlayer = this.pokerPlayer;
			return BestHand.TWO_PAIR;
		} else if (isOnePair()) {
			pokerPlayer = this.pokerPlayer;
			return BestHand.ONE_PAIR;
		} else if (isHighCard()) {
			pokerPlayer = this.pokerPlayer;
			return BestHand.HIGH_CARD;
		}
		
		return null;
	}



	public boolean isStraightFlush() {
		sortBySuit();
		
		int streak = 1;
		Card originCard = totalHand.get(0);
		for (int i = 0; i < totalHand.size()-1; i++) {
			if (totalHand.get(i).getSuit().equals(totalHand.get(i+1).getSuit())) {
				if (totalHand.get(i).getValue() - 1 == totalHand.get(i+1).getValue() || 
						(totalHand.get(i).getValue() == 1 && totalHand.get(i+1).getValue() == 13)) {
					streak++;
					
					if (streak >= 5) {
						pokerPlayer.setHighestRank(originCard.getValue());
						return true;
					}
				} else {
					streak = 1;
					originCard = totalHand.get(i+1);
				}
			} else {
				streak = 1;
				originCard = totalHand.get(i+1);
			}
		}
		
		return false;
	}



	public boolean isFourOfAKind() {
		sortByRank(false);
		
		for (Card card : totalHand) {
			for (Card secondCard : totalHand) {
				if (secondCard.equals(card)) {
					continue;
				}
				for (Card thirdCard : totalHand) {
					if (thirdCard.equals(secondCard) || thirdCard.equals(card)) {
						continue;
					}
					for (Card fourthCard : totalHand) {
						if (fourthCard.equals(thirdCard) || fourthCard.equals(secondCard) || fourthCard.equals(card)) {
							continue;
						}
						
						if (fourthCard.getValue() == thirdCard.getValue() && fourthCard.getValue() == secondCard.getValue() && fourthCard.getValue() == card.getValue()) {
							pokerPlayer.setHighestRank(card.getValue());
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}



	public boolean isFullHouse() {
		sortByRank(false);
		
		ArrayList<Card> threeOfAKind = new ArrayList<Card>();
		ArrayList<Card> pair = new ArrayList<Card>();
		
		if (isThreeOfAKind()) {
			for (Card card : totalHand) {
				for (Card otherCard : totalHand) {
					if (otherCard.equals(card)) {
						continue;
					}
					for (Card anotherCard : totalHand) {
						if (anotherCard.equals(card) || anotherCard.equals(otherCard)) {
							continue;
						}
						
						if (anotherCard.getValue() == card.getValue() && anotherCard.getValue() == otherCard.getValue()) {
							threeOfAKind.add(card);
							threeOfAKind.add(otherCard);
							threeOfAKind.add(anotherCard);
							break;
						}
					}
					if (threeOfAKind.size() > 0) {
						break;
					}
				}
				if (threeOfAKind.size() > 0) {
					break;
				}
			}
			
			if (isOnePair()) {
				for (Card card : totalHand) {
					for (Card otherCard : totalHand) {
						if (otherCard.equals(card)) {
							continue;
						}
						
						if (otherCard.getValue() == card.getValue() && !threeOfAKind.contains(card) && !threeOfAKind.contains(otherCard)) {
							pair.add(card);
							pair.add(otherCard);
						}
					}
				}
			}
		}
		
		if (threeOfAKind.size() > 0 && pair.size() > 0) {
			pokerPlayer.setHighestRank(threeOfAKind.get(0).getValue());
			return true;
		}
		
		return false;
	}



	public boolean isFlush() {
		sortBySuit();
		
		int streak = 1;
		Card originCard = totalHand.get(0);
		for (int i = 0; i < totalHand.size()-1; i++) {
			if (totalHand.get(i).getSuit().equals(totalHand.get(i+1).getSuit())) {
				streak++;
				
				if (streak >= 5) {
					pokerPlayer.setHighestRank(originCard.getValue());
					return true;
				}
			} else {
				streak = 1;
				originCard = totalHand.get(i+1);
			}
		}
		
		
		return false;
	}



	public boolean isStraight() {
		sortByRank(false);
		
		ArrayList<Card> distinctCards = new ArrayList<Card>(totalHand);
		
		for (int i = 0; i < totalHand.size() - 1; i++) {
			if (totalHand.get(i).getValue() == totalHand.get(i+1).getValue()) {
				distinctCards.remove(totalHand.get(i));
			}
		}
		
		int streak = 1;
		Card originCard = distinctCards.get(0);
		for (int i = 0; i < distinctCards.size() - 1; i++) {
			if (distinctCards.get(i).getValue() - 1 == distinctCards.get(i+1).getValue() || (distinctCards.get(i).getValue() == 1 && distinctCards.get(i+1).getValue() == 13)) {
				streak++;
				if (streak >= 5) {
					pokerPlayer.setHighestRank(originCard.getValue());
					return true;
				}
			} else {
				streak = 1;
				originCard = distinctCards.get(i+1);
			}
		}
		
		return false;
	}



	public boolean isThreeOfAKind() {
		sortByRank(false);
		
		for (Card card : totalHand) {
			for (Card otherCard : totalHand) {
				if (otherCard.equals(card)) {
					continue;
				}
				for (Card anotherCard : totalHand) {
					if (anotherCard.equals(card) || anotherCard.equals(otherCard)) {
						continue;
					}
					
					if (card.getValue() == otherCard.getValue() && card.getValue() == anotherCard.getValue()) {
						pokerPlayer.setHighestRank(card.getValue());
						return true;
					}
				}
			}
		}
		
		return false;
	}



	public boolean isTwoPair() {
		sortByRank(false);
		
		ArrayList<Card> pairs = new ArrayList<Card>();
		
		for (Card card : totalHand) {
			for (Card otherCard : totalHand) {
				if (card.getValue() == otherCard.getValue() && !card.equals(otherCard) && !pairs.contains(card) && !pairs.contains(otherCard)) {
					pokerPlayer.setHighestRank(card.getValue());
					pairs.add(card);
					pairs.add(otherCard);
				}
			}
		}
		
		if (pairs.size() >= 4) {
			return true;
		}
		
		return false;
	}



	public boolean isOnePair() {
		sortByRank(false);
		
		for (Card card : totalHand) {
			for (Card otherCard : totalHand) {
				if (card.getValue() == otherCard.getValue() && !card.equals(otherCard)) {
					pokerPlayer.setHighestRank(card.getValue());
					return true;
				}
			}
		}
		
		return false;
	}



	public boolean isHighCard() {
		sortByRank(false);
		pokerPlayer.setHighestRank(totalHand.get(0).getValue());
		
		return true;
	}



	public void sortBySuit() {
		ArrayList<Card> newTotalHand = new ArrayList<Card>();
		
		int count = 1;
		for (Suit suit : Suit.values()) {
			do {
				Card compareCard = new Card(count, suit);
				
				if (totalHand.contains(compareCard)) {
					newTotalHand.add(compareCard);
				}
				
				count--;
				
				if (count <= 0) {
					count = 13;
				}
			} while (count != 1);
		}
		
		totalHand.clear();
		totalHand.addAll(newTotalHand);
	}



	public void sortByRank(boolean isAscending) {
		ArrayList<Card> newTotalHand = new ArrayList<Card>();
		
		if (isAscending) {
			for (int i = 1; i <= 13; i++) {
				for (Suit suit : Suit.values()) {
					Card compareCard = new Card(i, suit);
					
					if (totalHand.contains(compareCard)) {
						newTotalHand.add(compareCard);
					}
				}
			}
			
			while (newTotalHand.get(0).getValue() == 1) {
				Card ace = newTotalHand.get(0);
				newTotalHand.remove(ace);
				newTotalHand.add(ace);
			}
		} else {
			for (int i = 13; i >= 1; i--) {
				for (Suit suit : Suit.values()) {
					Card compareCard = new Card(i, suit);

					if (totalHand.contains(compareCard)) {
						newTotalHand.add(compareCard);
					}
				}
			}
			
			while (newTotalHand.get(newTotalHand.size() - 1).getValue() == 1) {
				Card ace = newTotalHand.get(newTotalHand.size() - 1);
				newTotalHand.remove(ace);
				newTotalHand.add(0, ace);
			}
		}
		
		totalHand.clear();
		totalHand.addAll(newTotalHand);
	}



	public ArrayList<Card> getTotalHand() {
		return totalHand;
	}



	public void setTotalHand(ArrayList<Card> totalHand) {
		this.totalHand = totalHand;
	}



	public PokerPlayer getPokerPlayer() {
		return pokerPlayer;
	}



	public void setPokerPlayer(PokerPlayer pokerPlayer) {
		this.pokerPlayer = pokerPlayer;
	} 
}
