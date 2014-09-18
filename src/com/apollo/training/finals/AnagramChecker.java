package com.apollo.training.finals;

import java.util.Arrays;

public class AnagramChecker {

	public boolean check(String input1, String input2) {
		input1 = input1.replace(" ", "").toLowerCase();
		input2 = input2.replace(" ", "").toLowerCase();
		char[] newInput1 = input1.toCharArray();
		Arrays.sort(newInput1);
		char[] newInput2 = input2.toCharArray();
		Arrays.sort(newInput2);
		
		//check if the length of array is equal
		if (checkLength(newInput1, newInput2) && checkLetters(newInput1, newInput2)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkLetters(char[] newInput1, char[] newInput2) {
		boolean output = false;
		
		for (int i = 0; i < newInput1.length; i++) {
			if (newInput1[i] == newInput2[i]) {
				output = true;
			} else {
				output = false;
			}
		}
		
		return output;
	}

	private boolean checkLength(char[] newInput1, char[] newInput2) {
		int lengthOfInput1 = newInput1.length;
		int lengthOfInput2 = newInput2.length;
		
		if (lengthOfInput1 == lengthOfInput2) {
			return true;
		} else {
			return false;
		}
	}

}
