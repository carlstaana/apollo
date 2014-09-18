package com.apollo.training.set2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FleschIndexer {

	private Scanner in;

	public String index(File fileRead) throws FileNotFoundException {
		in = new Scanner(fileRead);
		String input = "";
		int numWords, numSyllables, numSentences;

		System.out.println("MESSAGE:");
		while (in.hasNextLine()) {
			input += in.nextLine();
			System.out.println(input);
		}

		System.out.println();
		// compute the number of words
		numWords = countWords(input);
		System.out.println("Number of words = \t" + numWords);

		// compute the number of syllables
		numSyllables = countSyllables(input);
		System.out.println("Number of syllables = \t" + numSyllables);

		// count the number of sentences
		numSentences = countSentences(input);
		System.out.println("Number of sentences = \t" + numSentences);

		double index = computeIndex(numWords, numSyllables, numSentences);
		System.out.println("Index = \t" + index);

		String levels = getLevels(index);
		System.out.println("Educational level = \t" + levels);

		return levels;
	}

	private String getLevels(double index) {
		String output;

		if (index >= 91) {
			output = "5th Grader";
		} else if (index >= 81) {
			output = "6th Grader";
		} else if (index >= 71) {
			output = "7th Grader";
		} else if (index >= 66) {
			output = "8th Grader";
		} else if (index >= 61) {
			output = "9th Grader";
		} else if (index >= 51) {
			output = "High School student";
		} else if (index >= 31) {
			output = "College student";
		} else if (index >= 0) {
			output = "College graduate";
		} else {
			output = "Law school graduate";
		}

		return output;
	}

	private double computeIndex(double numWords, double numSyllables,
			double numSentences) {
		double output = 206.835 - (1.015 * ((double) numWords / numSentences))
				- (84.6 * ((double) numSyllables / numWords));
		return output;
	}

	private int countSentences(String sample) {
		int output = 0;
		String denum = ".,;?!";

		for (int i = 0; i < sample.length(); i++) {
			for (int j = 0; j < denum.length(); j++) {
				if (sample.charAt(i) == denum.charAt(j)) {
					output++;
					break;
				}
			}
		}

		return output;
	}

	private int countSyllables(String sample) {
		int output = 0;
		String[] wordArray = sample.split(" |\\.|\\,|\\;|\\?|\\!"); // split the
																	// message
																	// word for
																	// word
		for (int i = 0; i < wordArray.length; i++) {
			String tempWord = wordArray[i].toLowerCase(); // temporary storage
															// for word
			for (int j = 0; j < tempWord.length(); j++) {
				char tempChar = tempWord.charAt(j); // temporary storage for the
													// selected letter
				if (searchVowel(tempChar)) {
					if (j > 0 && searchVowel(tempWord.charAt(j - 1))) {
						continue;
					} else {
						output++;
					}
				}
			}
			// check if word ends with 'e'
			if (tempWord.endsWith("e")
					&& !searchVowel(tempWord.charAt(tempWord.length() - 2))) {
				output--;
			}
		}
		return output;
	}

	private boolean searchVowel(char s) {
		String vowels = "aeiouy";
		for (int i = 0; i < vowels.length(); i++) {
			if (s == vowels.charAt(i)) {
				return true;
			}
		}
		return false;
	}

	private int countWords(String sample) {
		int output = 1;

		for (int i = 0; i < sample.length(); i++) {
			if (sample.charAt(i) == ' ') {
				output++;
			}
		}

		return output;
	}

}