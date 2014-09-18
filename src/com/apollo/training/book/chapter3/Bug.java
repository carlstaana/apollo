package com.apollo.training.book.chapter3;

public class Bug {
	private int position;
	private boolean forward;
	
	public Bug(int position) {
		this.position = position;
		forward = true;
	}

	public void move() {
		if (forward) {
			position++;
		} else {
			position--;
		}
	}

	public void turn() {
		forward = (forward) ? false : true;
	}

	public int getPosition() {
		return position;
	}

}
