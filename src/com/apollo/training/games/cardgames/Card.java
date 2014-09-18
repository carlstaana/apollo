package com.apollo.training.games.cardgames;

public class Card {
	public enum Suit { HEARTS, SPADES, CLUBS, DIAMONDS }
	private int rank;
	private Suit suit;
	
	public Card() {
	}
			
	public Card(int rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	public int getValue() {
		return rank;
	}
	public void setValue(int value) {
		this.rank = value;
	}
	public Suit getSuit() {
		return suit;
	}
	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	
	@Override
	public boolean equals(Object obj) {
		Card otherCard = (Card) obj;
		
		if (otherCard == null || this == null) {
			return false;
		} else if (rank == otherCard.getValue() && suit.equals(otherCard.getSuit())) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		String output = toUnicode() + " ";
		
		if (rank == 1) {
			output += "A";
		} else if (rank > 1 && rank < 11) {
			output += rank;
		} else if (rank == 11) {
			output += "J";
		} else if (rank == 12) {
			output += "Q";
		} else if (rank == 13) {
			output += "K";
		}
		
		output += " of " + suit;
		
		return output;
	}
	
	public String toUnicode() {
		String output = null;
		
		switch (suit) {
		case HEARTS:
			switch (rank) {
			case 1:
				output = "\uD83C\uDCB1";
				break;
			case 2:
				output = "\uD83C\uDCB2";
				break;
			case 3:
				output = "\uD83C\uDCB3";
				break;
			case 4:
				output = "\uD83C\uDCB4";
				break;
			case 5:
				output = "\uD83C\uDCB5";
				break;
			case 6:
				output = "\uD83C\uDCB6";
				break;
			case 7:
				output = "\uD83C\uDCB7";
				break;
			case 8:
				output = "\uD83C\uDCB8";
				break;
			case 9:
				output = "\uD83C\uDCB9";
				break;
			case 10:
				output = "\uD83C\uDCBA";
				break;
			case 11:
				output = "\uD83C\uDCBB";
				break;
			case 12:
				output = "\uD83C\uDCBD";
				break;
			case 13:
				output = "\uD83C\uDCBE";
				break;
			}
			break;
		case SPADES:
			switch (rank) {
			case 1:
				output = "\uD83C\uDCA1";
				break;
			case 2:
				output = "\uD83C\uDCA2";
				break;
			case 3:
				output = "\uD83C\uDCA3";
				break;
			case 4:
				output = "\uD83C\uDCA4";
				break;
			case 5:
				output = "\uD83C\uDCA5";
				break;
			case 6:
				output = "\uD83C\uDCA6";
				break;
			case 7:
				output = "\uD83C\uDCA7";
				break;
			case 8:
				output = "\uD83C\uDCA8";
				break;
			case 9:
				output = "\uD83C\uDCA9";
				break;
			case 10:
				output = "\uD83C\uDCAA";
				break;
			case 11:
				output = "\uD83C\uDCAB";
				break;
			case 12:
				output = "\uD83C\uDCAD";
				break;
			case 13:
				output = "\uD83C\uDCAE";
				break;
			}
			break;
		case CLUBS:
			switch (rank) {
			case 1:
				output = "\uD83C\uDCD1";
				break;
			case 2:
				output = "\uD83C\uDCD2";
				break;
			case 3:
				output = "\uD83C\uDCD3";
				break;
			case 4:
				output = "\uD83C\uDCD4";
				break;
			case 5:
				output = "\uD83C\uDCD5";
				break;
			case 6:
				output = "\uD83C\uDCD6";
				break;
			case 7:
				output = "\uD83C\uDCD7";
				break;
			case 8:
				output = "\uD83C\uDCD8";
				break;
			case 9:
				output = "\uD83C\uDCD9";
				break;
			case 10:
				output = "\uD83C\uDCDA";
				break;
			case 11:
				output = "\uD83C\uDCDB";
				break;
			case 12:
				output = "\uD83C\uDCDD";
				break;
			case 13:
				output = "\uD83C\uDCDE";
				break;
			}
			break;
		case DIAMONDS:
			switch (rank) {
			case 1:
				output = "\uD83C\uDCC1";
				break;
			case 2:
				output = "\uD83C\uDCC2";
				break;
			case 3:
				output = "\uD83C\uDCC3";
				break;
			case 4:
				output = "\uD83C\uDCC4";
				break;
			case 5:
				output = "\uD83C\uDCC5";
				break;
			case 6:
				output = "\uD83C\uDCC6";
				break;
			case 7:
				output = "\uD83C\uDCC7";
				break;
			case 8:
				output = "\uD83C\uDCC8";
				break;
			case 9:
				output = "\uD83C\uDCC9";
				break;
			case 10:
				output = "\uD83C\uDCCA";
				break;
			case 11:
				output = "\uD83C\uDCCB";
				break;
			case 12:
				output = "\uD83C\uDCCD";
				break;
			case 13:
				output = "\uD83C\uDCCE";
				break;
			}
			break;
		}

		return output;
	}

	public boolean isAce() {
		if (rank == 1) {
			return true;
		} else {
			return false;
		}
	}

	public int compareRank(Card otherCard, boolean aceIsHighest) {
		
		if (aceIsHighest) {
			if (this.isAce() && !otherCard.isAce()) {
				return 1;
			} else if (!this.isAce() && otherCard.isAce()) {
				return -1;
			} else if (this.isAce() && otherCard.isAce()) {
				return 0;
			} else if (!this.isAce() && !otherCard.isAce()) {
				if (this.rank > otherCard.rank) {
					return 1;
				} else if (this.rank < otherCard.rank) {
					return -1;
				} else {
					return 0;
				}
			}
		} else {
			if (this.rank > otherCard.rank) {
				return 1;
			} else if (this.rank < otherCard.rank) {
				return -1;
			} else {
				return 0;
			}
		}
		
		return 0;
	}
}
