package com.apollo.training.finals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.apollo.training.finals.House.Heating;

public class HouseFinder {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		String input;
		int numOfBedrooms = 0;
		Heating heating = null;
		boolean valid = false;

		House house = new House();
		File file = new File("houselist.dat");
		try {
			house.load(file);
			System.out.println("Loaded 500 registered houses");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (!valid) {
			System.out.print("Enter minimum number of bedrooms: ");
			input = in.next();
			try {
				numOfBedrooms = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				continue;
			}
			break;
		}
		
		while (!valid) {
			System.out.print("Enter type of heating (oil,gas,electric): ");
			input = in.next();
			if (input.equalsIgnoreCase("oil")) {
				heating = Heating.OIL;
			} else if (input.equalsIgnoreCase("gas")) {
				heating = Heating.GAS;
			} else if (input.equalsIgnoreCase("electric")) {
				heating = Heating.ELECTRIC;
			} else {
				continue;
			}
			break;
		}
		
		for (House h : house.getHouseList()) {
			if (h.getNumOfBedrooms() == numOfBedrooms && h.getStatus().compareTo(heating) == 0) {
				if (h.isPending()) {
					System.out.println("House " + h.getAddress() + " is available for $" + h.getPrice() + " with pending offers");
				} else {
					System.out.println("House " + h.getAddress() + " is available for $" + h.getPrice() + " with no pending offers");
				}
				
			}
		}

	}


}
