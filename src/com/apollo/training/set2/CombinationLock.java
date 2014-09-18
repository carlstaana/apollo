package com.apollo.training.set2;

import java.util.ArrayList;
import java.util.Scanner;

public class CombinationLock {

	private char pos1;
	private char pos2;
	private char pos3;
	Scanner in = new Scanner(System.in);
	ArrayList<Character> combinations = new ArrayList<Character>();
	boolean isOpen = false;
	
	public void set(char pos) {
		combinations.add(pos);
		System.out.println(pos);
	}

	public boolean open() {
		// check if lock is already open
		if (isOpen) {
			System.out.println("Lock is already open");
			return false;
		}
		
		// check first if the user has set at least 3 combinations
		if (combinations.size() < 3) {
			return false;
		}
		
		// get the entered combinations
		char firstCom = combinations.get(combinations.size() - 3);
		char secondCom = combinations.get(combinations.size() - 2);
		char thirdCom = combinations.get(combinations.size() - 1);
		
		// check if the combinations are correct
		if (pos1 == firstCom && pos2 == secondCom && pos3 == thirdCom) {
			combinations.clear(); // clear the arraylist
			isOpen = true; // set lock status to OPEN
			System.out.println("Unlocked");
			return true;
		}
		else {
			combinations.clear();
			System.out.println("Wrong Combination!");
			return false;
		}
	}

	public void close(char pos1, char pos2, char pos3) {
		this.pos1 = pos1;
		this.pos2 = pos2;
		this.pos3 = pos3;
		isOpen = false;
	}

}
