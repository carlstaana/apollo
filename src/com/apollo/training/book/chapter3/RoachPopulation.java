package com.apollo.training.book.chapter3;

public class RoachPopulation {
	private int roaches;
	
	public RoachPopulation(int initialPopulation) {
		roaches = initialPopulation;
	}

	public void breed() {
		roaches *= 2;
	}

	public void spray() {
		roaches -= roaches / 10;
	}

	public int getRoaches() {
		return roaches;
	}

}
