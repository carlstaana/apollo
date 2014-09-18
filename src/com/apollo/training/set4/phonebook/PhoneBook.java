package com.apollo.training.set4.phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * The class that implements the DataProcessor interface
 * @author apollo/carlstaana
 * @param contacts where the contact list is added
 */
public class PhoneBook implements DataProcessor {
	private ArrayList<Contact> contacts = new ArrayList<Contact>();

	public void addEntry(Contact contact) {
		getContacts().add(contact);	// add the contact to the ArrayList
	}

	public void editEntry(Contact contact) {
		contact.editInfo();
	}

	public void listContacts() {
		if (getContacts().size() == 0) {
			throw new IndexOutOfBoundsException();
		}
		else {
			System.out.println("\n--------[CONTACTS]--------\n");
			// display elements inside contacts ArrayList
			for (int i = 0; i < getContacts().size(); i++) {
				Contact con = getContacts().get(i);
				System.out.println("ID: [" + i + "]");
				System.out.println(con.toString());
			}
		}
	}

	public void saveContacts(String fileName) {
		PrintWriter out = null;
		
		try {
			out = new PrintWriter(new File(fileName)); // get the file and invoke PrintWriter class
		} catch (NullPointerException e) {
			throw new NullPointerException("[Error] fileName is NULL.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < getContacts().size(); i++) {
			Contact con = getContacts().get(i); // get contact object inside the ArrayList
			String name = con.getName();		// get the name, phone number, and email address
			long number = con.getNumber();		
			String email = con.geteMail();
			out.println(name + "%" + number + "%" + email);	// write in the file in this order
		}
		out.close();
		getContacts().clear(); // clear ArrayList
	}

	public void loadContacts(String fileName, boolean replace) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);;
		if (replace) {
			getContacts().clear();	// it will clear the arraylist if replace is true
		}
		
		try {
			in = new Scanner(new File(fileName));
		}  catch (FileNotFoundException e) {
			throw new FileNotFoundException("[Error] " + fileName + " does not exist");
		} 
		
		String input;
		try {
			while (in.hasNextLine()) {
				Contact con = new Contact();
				input = in.nextLine();
				String[] contactArray = input.split("%"); // split the read line in the text file and save it to an array
				// re-add entries to ArrayList
				con.setName(contactArray[0]);
				con.setNumber(Long.parseLong((contactArray[1])));	// convert String to long
				con.seteMail(contactArray[2]);
				addEntry(con);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		
		in.close();
	}

	/**
	 * Gets the contact list
	 * @return the current working phone book
	 */
	public ArrayList<Contact> getContacts() {
		return contacts;
	}
}
