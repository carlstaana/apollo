package com.apollo.training.finals;

public class Exercise {
	private String name;
	private int lowtemp;
	private int hightemp;
	
	public Exercise(String name, int lowtemp, int hightemp) {
		this.name = name;
		this.lowtemp = lowtemp;
		this.hightemp = hightemp;
	}

	public String getName() {
		return name;
	}

	public int getLowtemp() {
		return lowtemp;
	}

	public int getHightemp() {
		return hightemp;
	}

	public boolean equals(Exercise otherExercise) {
		return getName().equals(otherExercise.getName());
	}
	
	
}
