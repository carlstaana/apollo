package com.apollo.training.set4.phonebook;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The Contact class is where the details of the contacts are managed
 * @author apollo/carlstaana
 * @param in gets the user input in the console
 * @param name stores the name of the contact
 * @param number stores the phone number of the contact
 * @param eMail stores the e-mail address of the contact
 */
public class Contact extends PhoneBook {
	Scanner in = new Scanner(System.in);
	private String name;
	private long number;
	private String eMail;
	/**
	 * Sets the contact details and provides the user instructions to input correct values
	 */
	public void setContacts() {
		System.out.print("Enter name:\t");
		try {
			setName(in.nextLine());
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("[Error] No input was found.");
		}
		
		System.out.print("Enter number:\t");
		try {
			setNumber(in.nextLong());
		} catch (InputMismatchException e) {
			throw new InputMismatchException("[Error] Please input a positive integer");
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("[Error] No input was found.");
		}
		
		System.out.print("Enter e-mail address:\t");
		try {
			seteMail(in.nextLine());
			seteMail(in.nextLine());
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("[Error] No input was found.");
		}
		
		System.out.println("---New contact added---");
		System.out.println(toString());
	}

	// -- GETTERS and SETTERS -- //
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long d) {
		this.number = d;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	// -- END -- //
	
	/**
	 * This gives the user the choice what field will edit in the selected contact
	 */
	public void editInfo() {
		String input;
		int num = 0;
		System.out.println("\n[EDIT]");
		System.out.println("Press 1 to edit [Name]");
		System.out.println("Press 2 to edit [Phone Number]");
		System.out.println("Press 3 to edit [E-Mail]");
		try {
			input = in.nextLine();
			num = Integer.parseInt(input);
		} catch (NumberFormatException | InputMismatchException e) {
			num = 0;
			System.out.println("[Error] Please enter a positive integer");
		}
		
		switch (num) {
		case 1:
			System.out.print("Enter New Name:\t");
			try {
				setName(in.nextLine());
				setName(in.nextLine());
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException("[Error] No line found");
			}
			
			break;
		case 2:
			System.out.print("Enter New Phone Number:\t");
			try {
				input = in.nextLine();
				setNumber(Long.parseLong(input));
			} catch (InputMismatchException | IllegalArgumentException e) {
				System.out.println("\n[Error] Input is not a number");
				System.out.println("--Contact Details Unchanged--");
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException("[Error] No line found");
			}
			
			break;
		case 3:
			System.out.print("Enter New E-mail Address:\t");
			try {
				seteMail(in.nextLine());
				seteMail(in.nextLine());
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException("[Error] No line found");
			}
			
			break;
		default:
			System.out.println("Wrong input. Please try again");
			break;
		}
		System.out.println(toString());
	}
	
	/**
	 * It displays a full detailed list of the contact
	 */
	public String toString() {
		String output = "Name:\t" + getName() + 
				"\nPhoneNumber:\t" + getNumber() + 
				"\nE-mail Address:\t" + geteMail() + "\n";
		return output;
	}
	
}
