package com.apollo.training.book.chapter7;

import java.util.ArrayList;

public class Purse {
	private ArrayList<String> purse = new ArrayList<String>();
	
	public void addCoin(String coinName) {
		switch (coinName) {
		case "Penny":
			purse.add(coinName);
			break;
		case "Nickel":
			purse.add(coinName);
			break;
		case "Dime":
			purse.add(coinName);
			break;
		case "Quarter":
			purse.add(coinName);
			break;
		default:
			System.out.println("Error. Invalid coin name.");
			break;
		}
	}

	@Override
	public String toString() {
		String output = "Purse[";
		for (int i = 0; i < purse.size(); i++) {
			output += purse.get(i);
			if (i == purse.size() - 1) {
				output += "]";
			} else {
				output += ",";
			}
		}
		return output;
	}

	public void reverse() {
		ArrayList<String> newPurse = new ArrayList<String>();
		for (int i = purse.size() - 1; i >= 0; i--) {
			newPurse.add(purse.get(i));
		}
		purse.clear();
		purse.addAll(newPurse);
	}

	public void transfer(Purse otherPurse) {
		for (String coins : otherPurse.purse) {
			purse.add(coins);
		}
		otherPurse.purse.clear();
	}

	public boolean sameContents(Purse otherPurse) {
		if (purse.size() != otherPurse.purse.size()) {
			return false;
		} else {
			for (int i = 0; i < purse.size(); i++) {
				if (!purse.get(i).equalsIgnoreCase(otherPurse.purse.get(i))) {
					return false;
				}
			}
			
			return true;
		}
	}

	public boolean sameCoins(Purse otherPurse) {
		this.sort();
		otherPurse.sort();
		System.out.println("\n--Sorted Purse--\n" + this.toString() + "\n" + otherPurse.toString());

		if (this.sameContents(otherPurse)) {
			return true;
		} else {
			return false;
		}		
	}

	private void sort() {
		ArrayList<String> sortedPurse = new ArrayList<String>();
		
		findAndAdd("Penny", sortedPurse);
		findAndAdd("Dime", sortedPurse);
		findAndAdd("Nickel", sortedPurse);
		findAndAdd("Quarter", sortedPurse);
		
		purse.clear();
		purse.addAll(sortedPurse);
	}

	private void findAndAdd(String coinName, ArrayList<String> sortedPurse) {
		for (String coin : purse) {
			if (coin.equalsIgnoreCase(coinName)) {
				sortedPurse.add(coin);
			}
		}
	}
}
