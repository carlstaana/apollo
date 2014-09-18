package com.apollo.training.set4.phonebook;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PhoneBookTest {

	@Test
	public void testAddEntry() {
		PhoneBook pb = new PhoneBook();
		ArrayList<Contact> conArray = pb.getContacts();
		int actual = conArray.size();
		int expected = 0;
		assertEquals(expected, actual);
		
		Contact con = new Contact();
		con.setName("Carl");
		con.setNumber(12345);
		con.seteMail("carl@apollo.com.ph");
		pb.addEntry(con);	
		conArray = pb.getContacts();
		actual = conArray.size();
		expected = 1;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testEditEntry() throws Exception {
		PhoneBook pb = new PhoneBook();
		ArrayList<Contact> conArray = pb.getContacts();
		Contact con = new Contact();
		con.setName("Carl");
		con.setNumber(12345);
		con.seteMail("carl@apollo.com.ph");
		pb.addEntry(con);	
		conArray = pb.getContacts();
		con.setName("Carl Kenneth Sta.Ana");
		conArray.set(0, con);
		String actual = con.getName();
		String expected = "Carl Kenneth Sta.Ana";
		assertEquals(expected, actual);
	}
	
	@Test
	public void testListContacts() throws Exception {
		PhoneBook pb = new PhoneBook();
		ArrayList<Contact> conArray = pb.getContacts();
		Contact con = new Contact();
		
		con.setName("Carl");
		con.setNumber(12345);
		con.seteMail("carl@apollo.com.ph");
		pb.addEntry(con);
		
		Contact con2 = new Contact();
		con2.setName("Kenneth");
		con2.setNumber(6543654);
		con2.seteMail("Kenneth@apollo.com.ph");
		pb.addEntry(con2);
		
		conArray = pb.getContacts();
		assertTrue(conArray.size() == 2);
	}
	
	@Test
	public void testSaveContacts() throws Exception {
		PhoneBook pb = new PhoneBook();
		Contact con = new Contact();
		
		con.setName("Carl");
		con.setNumber(12345);
		con.seteMail("carl@apollo.com.ph");
		pb.addEntry(con);
		pb.saveContacts("contacts.txt");
		
		// check if file exists
		File f = new File("contacts.txt");
		assertTrue(f.exists());
	}
	
	@Test
	public void testLoadContacts() throws Exception {
		PhoneBook pb = new PhoneBook();
		
		pb.loadContacts("contacts.txt", true);
		ArrayList<Contact> conArray = pb.getContacts();
		
		assertTrue(conArray.size() > 0);
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testLoadNonExistingFile() throws FileNotFoundException {
		exception.expect(FileNotFoundException.class);
		PhoneBook pb = new PhoneBook();
		pb.loadContacts("hey,txt", true);
	}
	
	@Test
	public void testLoadNullFile() throws Exception {
		exception.expect(NullPointerException.class);
		PhoneBook pb = new PhoneBook();
		pb.loadContacts(null, true);
	}
	
	@Test
	public void testSaveNull() throws Exception {
		exception.expect(NullPointerException.class);
		PhoneBook pb = new PhoneBook();
		pb.saveContacts(null);
	}
}
