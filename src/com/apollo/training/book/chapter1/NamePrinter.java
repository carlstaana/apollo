package com.apollo.training.book.chapter1;

public class NamePrinter {

	public void print(String input) {
		String output = "";
		output += insertBorder(input.length());
		output += insertName(input);
		output += insertBorder(input.length());
		
		System.out.println(output);
	}

	private String insertName(String input) {
		return "|"+input+"|\n";
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
