package com.apollo.training.finals;

import javax.management.InvalidAttributeValueException;

public class AirlineSeating {
	boolean[][] firstClass = new boolean[4][4];
	boolean[][] economyClass = new boolean[16][6];
	public enum flightClass { FIRST, ECONOMY }
	public enum seatingPreference { AISLE, CENTER, WINDOW }

	private char checkSeat(boolean seat) {
		if (seat) {
			return 'O';
		} else {
			return 'A';
		}
	}

	public void addPassengers(String ticketClass, int numPassengers, String preference) throws InvalidAttributeValueException {
		flightClass flight = getClass(ticketClass);
		if (flight == flightClass.FIRST) {
			// number of passengers must be 1-2
			if (numPassengers < 1 || numPassengers > 2) {
				throw new InvalidAttributeValueException("Number of passengers is 1-2");
			}
			// preferences must be only aisle and center
			seatingPreference prefer = getPreference(preference);
			if (prefer == seatingPreference.CENTER) {
				throw new InvalidAttributeValueException("First Class flights can only pick AISLE and WINDOW");
			} else if (prefer == seatingPreference.WINDOW) {
				firstClass = seatPassengersInWindow(numPassengers, firstClass, flight);
			} else {
				// AISLE
				firstClass = seatPassengersInAisle(numPassengers, firstClass, flight);
			}
		} else {
			// ECONOMY CLASS
			// number of passengers must be 1-2
			if (numPassengers < 1 || numPassengers > 3) {
				throw new InvalidAttributeValueException("Number of passengers is 1-3");
			}
			// preferences must be only aisle and center
			seatingPreference prefer = getPreference(preference);
			if (prefer == seatingPreference.CENTER) {
				economyClass = seatPassengersInCenter(numPassengers, economyClass);
			} else if (prefer == seatingPreference.WINDOW) {
				economyClass = seatPassengersInWindow(numPassengers, economyClass, flight);
			} else {
				// AISLE
				economyClass = seatPassengersInAisle(numPassengers, economyClass, flight);
			}
		}

	}

	private boolean[][] seatPassengersInCenter(int numPassengers,
			boolean[][] seating) {
		boolean[][] tempSeating = seating;
		// counts the passengers that are not seated
		int counter = numPassengers;
		
		System.out.println("Seats assigned:");
		
		for (int i = 0; i < tempSeating.length; i++) {
			if (counter == 0) {
				break;
			}
			for (int j = 0; j < tempSeating[i].length; j++) {
				if (counter == 0) {
					break;
				}
				if (j == 1 || j == 4) {
					if (!seating[i][j]) {
						tempSeating[i][j] = true;
						counter--;
						
						System.out.print("row#");
						
						if ((i+1) < 10) {
							System.out.print("0");
						}
						
						if (j == 1) {
							System.out.println((i+1) + " left center");
						} else {
							System.out.println((i+1) + " right center");
						}
					}
				}
			}
		}
		
		if (counter > 0) {
			System.out.println("There are no matches based on your preference");
			return seating;
		} else {
			return tempSeating;
		}
	}

	private boolean[][] seatPassengersInAisle(int numPassengers,
			boolean[][] seating, flightClass flight) {
		boolean[][] tempSeating = seating;
		int leftAisle;
		int rightAisle;
		if (flight == flightClass.FIRST) {
			leftAisle = 1;
			rightAisle = 2;
		} else {
			leftAisle = 2;
			rightAisle = 3;
		}
		
		System.out.println("Seats assigned:");
		
		// counts the passengers that are not seated
		int counter = numPassengers;
		for (int i = 0; i < tempSeating.length; i++) {
			if (counter == 0) {
				break;
			}
			for (int j = 0; j < tempSeating[i].length; j++) {
				if (counter == 0) {
					break;
				}
				if (i == 0) {
					if (!tempSeating[i][j]) {
						tempSeating[i][j] = true;
						counter--;
						
						System.out.print("row#");
						
						if ((i+1) < 10) {
							System.out.print("0");
						}
						
						if (flight == flightClass.FIRST) {
							if (j == 0) {
								System.out.println((i+1) + " left window");
							} else if (j == leftAisle) {
								System.out.println((i+1) + " left aisle");
							} else if (j == rightAisle) {
								System.out.println((i+1) + " right aisle");
							} else {
								System.out.println((i+1) + " right window");
							}
						} else {
							if (j == 0) {
								System.out.println((i+1) + " left window");
							} else if (j == 1) {
								System.out.println((i+1) + " left center");
							} else if (j == leftAisle) {
								System.out.println((i+1) + " left aisle");
							} else if (j == rightAisle) {
								System.out.println((i+1) + " right aisle");
							} else if (j == 4) {
								System.out.println((i+1) + " right center");
							} else {
								System.out.println((i+1) + " right window");
							}
						}
					}
				} else {
					if (j == leftAisle || j == rightAisle) {
						if (!tempSeating[i][j]) {
							tempSeating[i][j] = true;
							counter--;
							
							System.out.print("row#");
							
							if ((i+1) < 10) {
								System.out.print("0");
							}
							
							if (j == leftAisle) {
								System.out.println((i+1) + " left aisle");
							} else {
								System.out.println((i+1) + " right aisle");
							}
						}
					}
				}
			}
		}
		
		if (counter > 0) {
			System.out.println("There are no matches based on your preference");
			return seating;
		} else {
			return tempSeating;
		}
	}

	private boolean[][] seatPassengersInWindow(int numPassengers,
			boolean[][] seating, flightClass flight) {
		boolean[][] tempSeating = seating;
		final int leftWindows = 0;
		int rightWindows;
		if (flight == flightClass.FIRST) {
			rightWindows = 3;
		} else {
			rightWindows = 5;
		}
		
		System.out.println("Seats assigned:");
		
		// counts the passengers that are not seated
		int counter = numPassengers;
		for (int i = 0; i < tempSeating.length; i++) {
			for (int j = 0; j < tempSeating[i].length; j++) {
				if (counter == 0) {
					break;
				}
				if (j == leftWindows || j == rightWindows) {
					if (!tempSeating[i][j]) {
						tempSeating[i][j] = true;
						counter--;
						
						System.out.print("row#");

						if ((i+1) < 10) {
							System.out.print("0");
						}
						
						if (j == leftWindows) {
							System.out.println((i+1) + " left window");
						} else {
							System.out.println((i+1) + " right window");
						}
					}
				}
			}
			if (counter == 0) {
				break;
			}
		}
		
		if (counter > 0) {
			System.out.println("There are no seats available based on your preference");
			return seating;
		} else {
			return tempSeating;
		}
	}

	private seatingPreference getPreference(String preference) throws InvalidAttributeValueException {
		seatingPreference output;
		preference = preference.toLowerCase();

		switch (preference) {
		case "aisle":
			output = seatingPreference.AISLE;
			break;
		case "center":
			output = seatingPreference.CENTER;
			break;
		case "window":
			output = seatingPreference.WINDOW;
			break;
		default:
			throw new InvalidAttributeValueException("Seating preferences are only AISLE, CENTER, and WINDOW");
		}
		return output;
	}

	private com.apollo.training.finals.AirlineSeating.flightClass getClass(
			String ticketClass) throws InvalidAttributeValueException {
		flightClass output;
		ticketClass = ticketClass.toLowerCase();

		switch (ticketClass) {
		case "economy":
			output = flightClass.ECONOMY;
			break;
		case "first":
			output = flightClass.FIRST;
			break;
		default:
			throw new InvalidAttributeValueException("Flight Classes are only ECONOMY and FIRST class");
		}

		return output;
	}
	
	@Override
	public String toString() {
		String output = "";

		// First Class
		output += "First Class\n---------------\n";
		for (int i = 0; i < firstClass.length; i++) {
			if (i+1 < 10) {
				output += "row#0" + (i+1) + " ";
			} else {
				output += "row#" + (i+1) + " ";
			}
			for (int j = 0; j < firstClass[i].length; j++) {
				char seatStatus = checkSeat(firstClass[i][j]);
				if (j == 2) {
					output += "   ";
				}
				output += seatStatus;
			}
			output += "\n";
		}

		//Economy Class
		output += "---------------\nEconomyClass\n---------------\n";
		for (int i = 0; i < economyClass.length; i++) {
			if (i+1 < 10) {
				output += "row#0" + (i+1) + " ";
			} else {
				output += "row#" + (i+1) + " ";
			}
			for (int j = 0; j < economyClass[i].length; j++) {
				char seatStatus = checkSeat(economyClass[i][j]);
				if (j == 3) {
					output += " ";
				}
				output += seatStatus;
			}
			output += "\n";
		}

		System.out.println(output);
		return super.toString();
	}
}
