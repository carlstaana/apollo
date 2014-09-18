package com.apollo.training.finals;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Activities {
	Exercise swimming = new Exercise("swimming", 75, 110);
	Exercise tennis = new Exercise("tennis", 61, 85);
	Exercise skiing = new Exercise("skiing", 11, 32);
	Exercise checkers = new Exercise("checkers", -20, 10);
	Exercise soccer = new Exercise("soccer", 33, 70);
	ArrayList<Exercise> exercises = new ArrayList<Exercise>();
	
	
	public Activities() {
		exercises.add(swimming);
		exercises.add(tennis);
		exercises.add(skiing);
		exercises.add(checkers);
		exercises.add(soccer);
	}


	public void recommend(int temperature) throws InputMismatchException {
		String output = "";
		
		if (temperature < -20 || temperature > 110) {
			throw new InputMismatchException();
		}
		
		for (Exercise exe : exercises) {
			if (temperature >= exe.getLowtemp() && temperature <= exe.getHightemp()) {
				output += exe.getName() + " ";
			}
		}
		
		output = output.trim();
		output = output.replace(" ", " and ");
		System.out.println("The recommended activities are: " + output);
	}
}
