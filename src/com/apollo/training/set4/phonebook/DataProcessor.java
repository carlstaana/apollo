package com.apollo.training.set4.phonebook;

import java.io.FileNotFoundException;

public interface DataProcessor {
	/**
	 * Adds a new entry to the phone book
	 * 
	 * @param contact
	 *            the contact/entry to be added
	 */
	public void addEntry(Contact contact);

	/**
	 * Edits a specific detail to the selected contact
	 * 
	 * @param contact
	 *            the selected contact to be edited
	 */
	public void editEntry(Contact contact);

	/**
	 * Lists all the contacts in detail
	 */
	public void listContacts();

	/**
	 * Saves the phone book to a file
	 * 
	 * @param fileName
	 *            the name and file extension of the file to be saved
	 */
	public void saveContacts(String fileName);

	/**
	 * Loads the phone book from an existing file
	 * 
	 * @param fileName
	 *            the name and file extension of the file to be loaded
	 * @param replace
	 *            the flag wherein the user wanted to replace the current
	 *            working phone book or not
	 * @throws FileNotFoundException
	 *             the exception when the file has not found
	 */
	public void loadContacts(String fileName, boolean replace)
			throws FileNotFoundException;
}
