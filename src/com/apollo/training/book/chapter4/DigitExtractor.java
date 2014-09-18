package com.apollo.training.book.chapter4;

public class DigitExtractor {
	private int digit;
	private int count;
	
	public DigitExtractor(int digit) {
		this.digit = digit;
		count = 0;
	}

	public int nextDigit() {
		String tempDigit = String.valueOf(digit);
		count++;
		return Integer.parseInt(String.valueOf(tempDigit.charAt(tempDigit.length() - count)));
	}
	
}
