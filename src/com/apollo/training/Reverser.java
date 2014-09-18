package com.apollo.training;

public class Reverser {

	public String process(String input) {
		String[] words = input.split(" ");
		StringBuilder builder = new StringBuilder();
		
		for(int i = words.length - 1; i >= 0; i--){
			builder.append(words[i]);
			if(i > 0){
				builder.append(" ");
			}
			
		}
		return builder.toString();

	}

}
