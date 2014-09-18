package com.apollo.training.set4.phonebook;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * main class and provides user interface
 * 
 * @author apollo/carlstaana
 *
 */
public class Launcher extends PhoneBook {
	/**
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * 
	 * @param ph
	 *            instantiates PhoneBook class
	 * @param key
	 *            stores the user input when choosing tasks
	 * @param in
	 *            used for getting user inputs
	 * @param loading
	 *            will be true if Load Contact List is selected OR 5 is entered
	 */
	public static void main(String[] args) throws FileNotFoundException {
		PhoneBook ph = new PhoneBook();
		String input;
		int key = 0;
		Scanner in = new Scanner(System.in);
		boolean loading = false;

		while (key != 6) {
			System.out.println("----------PHONEBOOK----------");
			System.out.println("Enter the number of the assigned task:");
			System.out.println("[1] Add New Contact");
			System.out.println("[2] View Contact List");
			System.out.println("[3] Edit Contact");
			System.out.println("[4] Save Contacts");
			System.out.println("[5] Load Contact List");
			System.out.println("[6] Close");
			System.out.print("Answer:\t");

			try {
				input = in.nextLine();
				key = Integer.parseInt(input);
			} catch (InputMismatchException | IllegalArgumentException e) {
				System.out.println("\n[Error] Input must be an integer");
				key = 0;
			} catch (NoSuchElementException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			switch (key) {
			case 1:
				Contact con = new Contact();
				try {
					con.setContacts();
				} catch (InputMismatchException e) {
					System.out
							.println("\n[Error] Phone number must be a positive integer");
				} catch (NoSuchElementException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					key = 0;
				}
				ph.addEntry(con);
				break;
			case 2:
				try {
					ph.listContacts();
				} catch (IndexOutOfBoundsException e) {
					System.out.println("\n[!] No contacts yet\n");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					key = 0;
				}

				break;
			case 3:
				int ID = 0;
				// check if there are current contacts in PhoneBook
				if (ph.getContacts().size() == 0) {
					System.out.println("\n[!] No contacts yet\n");
					break;
				}
				ph.listContacts();
				System.out
						.print("Enter the number of the contact you want to edit:\t");
				try {
					input = in.nextLine();
					ID = Integer.parseInt(input);
				} catch (InputMismatchException | IllegalArgumentException e) {
					ID = 0;
					System.out
							.println("\n[Error] Please enter an appropriate value.\n");
					break;
				} catch (NoSuchElementException e) {
					e.printStackTrace();
				}

				if (ID < 0 || ID > ph.getContacts().size() - 1) {
					System.out
							.println("\n[Error] Please input an existing ID number.\n");
				} else {
					ph.editEntry(ph.getContacts().get(ID));
				}
				break;
			case 4:
				if (!loading) { // when loading files is not pressed yet and
								// user added new contacts
					// TODO concept is still questionable
					ph.saveContacts("~temp.txt"); // save contents to a
													// temporary file
					ph.loadContacts("contacts.txt", true); // load contents of
															// the file to fill
															// the arraylist
					ph.loadContacts("~temp.txt", false); // add the contents of
															// the temporary
															// file in the
															// arraylist
					ph.saveContacts("contacts.txt"); // save all of them into
														// one file
				} else {
					ph.saveContacts("contacts.txt");
					ph.loadContacts("contacts.txt", true);
				}
				System.out.println();
				System.out.println("Contacts Saved: contacts.txt");
				break;
			case 5:
				loading = true;
				ph.loadContacts("contacts.txt", true);
				System.out.println();
				System.out.println("Phonebook Loaded\n"
						+ "Number of Contacts Found:	"
						+ ph.getContacts().size());
				break;
			case 6:
				in.close();
				System.exit(0);
				break;
			default:
				System.out
						.println("You have entered a wrong input. Please try again.\n");
				break;
			}
		}

	}
}
