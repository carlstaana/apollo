package com.apollo.training.finals;

public class Invoice {

	private final int CENTS_PER_BOLT = 5;
	private final int CENTS_PER_NUT = 3;
	private final int CENTS_PER_WASHER = 1;

	private int bolts;
	private int nuts;
	private int washers;

	public Invoice(int bolts, int nuts, int washers) {
		this.bolts = bolts;
		this.nuts = nuts;
		this.washers = washers;
	}

	public boolean hasEnoughNuts() {
		if (nuts >= bolts) {
			return true;
		}
		return false;
	}

	public boolean hasEnoughWashers() {
		if (washers >= 2 * bolts) {
			return true;
		}
		return false;
	}

	public int getTotalCost() {
		int total = (CENTS_PER_BOLT*bolts) + (CENTS_PER_NUT*nuts) + (CENTS_PER_WASHER * washers);
		return total;
	}

}
