package com.apollo.training.book.chapter1;


public class NamePrinter {

	public void print(String input) {
		String[] stringPerLine = (input.split("\n"));
		// get the longest string inside the splitted input
		int longestString = 0;
		for (String line : stringPerLine) {
			if (line.length() > longestString) {
				longestString = line.length();
			}
		}
		// print it out perfectly
		
		String output = "";
		output += insertBorder(longestString);
		for (String line : stringPerLine) {
			output += insertName(line, longestString);
		}
		output += insertBorder(longestString);
		
		System.out.println(output);
	}

	private String insertName(String input, int longestString) {
		String output = "|" + input;
		if (input.length() < longestString) {
			for (int i = 0; i < (longestString - input.length()); i++) {
				output += " ";
			}
		}
		output += "|\n";
		
		return output;
	}

	private String insertBorder(int length) {
		String output = "";
		output += "+";
		for (int i = 0; i < length; i++) {
			output += "-";
		}
		output += "+\n";
		return output;
	}

}
