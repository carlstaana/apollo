package com.apollo.training.finals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class FileSummer {
	public static void main(String[] args) {
		File file = new File("filesummer.txt");
		Scanner in = null;
		int number = 0;
		int count = 1;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		
		while (in.hasNext()) {
			String line = in.next();
			if (line.equalsIgnoreCase("next")) {
				number = 0;
				while (in.hasNext()) {
					String getNumber = in.next();
					if (getNumber.equalsIgnoreCase("next")) {
						if (number == 0) {
							System.out.println("Group " + count + " contains no data");	
						} else {
							System.out.println("Sum of group " + count + " is " + number);
						}
						count++;
						break;
					} else {
						number += Integer.parseInt(getNumber);
					}
				}
			}
		}
	}
}
