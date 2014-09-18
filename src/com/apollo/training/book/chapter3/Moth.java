package com.apollo.training.book.chapter3;

public class Moth {
	private double position;
	
	
	public Moth(double initialPosition) {
		this.position = initialPosition;
	}


	public void moveToLight(double lightPosition) {
		if (position != lightPosition) {
			if (position < lightPosition) {
				position += (lightPosition - position) / 2;
			} else {
				position -= (position - lightPosition) / 2;
			}
		} else {
			System.out.println("You are already in the light. The moth is burnt.");
			System.exit(0);
		}
	}


	public double getPosition() {
		return position;
	}
	
	

}
