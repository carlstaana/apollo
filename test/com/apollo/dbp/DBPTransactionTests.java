package com.apollo.dbp;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.Test;

public class DBPTransactionTests {

	@Test
	public void testGetTransactionType() throws Exception {
		FileReader fr = new FileReader("VISALogs.20150127.txt");
		BufferedReader reader = new BufferedReader(fr);
		ArrayList<String> input = new ArrayList<String>();
		String currentLine = null;
		do {
			currentLine = reader.readLine();
			input.add(currentLine);
		} while (!currentLine.contains("process() -- END"));
		DBPTransaction dbpTransaction = new DBPTransaction(input);
		assertEquals("REGULAR_TRANSACTION", dbpTransaction.getTransactionType(input));
	}
	
	@Test
	public void testGetRRN() throws Exception {
		FileReader fr = new FileReader("VISALogs.20150127.txt");
		BufferedReader reader = new BufferedReader(fr);
		ArrayList<String> input = new ArrayList<String>();
		String currentLine = null;
		do {
			currentLine = reader.readLine();
			input.add(currentLine);
		} while (!currentLine.contains("process() -- END"));
//		for (String string : input) {
//			System.out.println(string);
//		}
		DBPTransaction dbpTransaction = new DBPTransaction(input);
		assertTrue(dbpTransaction.getRRN(input).length() == 12);
	}
	
	@Test
	public void testGetResponseCode() throws Exception {
		FileReader fr = new FileReader("VISALogs.20150127.txt");
		BufferedReader reader = new BufferedReader(fr);
		ArrayList<String> input = new ArrayList<String>();
		String currentLine = null;
		do {
			currentLine = reader.readLine();
			input.add(currentLine);
		} while (!currentLine.contains("process() -- END"));
		DBPTransaction dbpTransaction = new DBPTransaction(input);
		assertEquals("51", dbpTransaction.getResponseCode(input));
	}
	
	@Test
	public void testGetStartTime() throws Exception {
		FileReader fr = new FileReader("VISALogs.20150127.txt");
		BufferedReader reader = new BufferedReader(fr);
		ArrayList<String> input = new ArrayList<String>();
		String currentLine = null;
		do {
			currentLine = reader.readLine();
			input.add(currentLine);
		} while (!currentLine.contains("process() -- END"));
		DBPTransaction dbpTransaction = new DBPTransaction(input);
		assertEquals("00:00:51.241", dbpTransaction.getStartTime(input));
	}
	
	@Test
	public void testGetPostillionRequestTime() throws Exception {
		FileReader fr = new FileReader("VISALogs.20150127.txt");
		BufferedReader reader = new BufferedReader(fr);
		ArrayList<String> input = new ArrayList<String>();
		String currentLine = null;
		do {
			currentLine = reader.readLine();
			input.add(currentLine);
		} while (!currentLine.contains("process() -- END"));
		DBPTransaction dbpTransaction = new DBPTransaction(input);
		assertEquals("00:00:51.279", dbpTransaction.getPostillionRequestTime(input));
	}
	
	@Test
	public void testGetPostillionResponseTime() throws Exception {
		FileReader fr = new FileReader("VISALogs.20150127.txt");
		BufferedReader reader = new BufferedReader(fr);
		ArrayList<String> input = new ArrayList<String>();
		String currentLine = null;
		do {
			currentLine = reader.readLine();
			input.add(currentLine);
		} while (!currentLine.contains("process() -- END"));
		DBPTransaction dbpTransaction = new DBPTransaction(input);
		assertEquals("00:00:51.445", dbpTransaction.getPostillionResponseTime(input));
	}
	
	@Test
	public void testGetEndTime() throws Exception {
		FileReader fr = new FileReader("VISALogs.20150127.txt");
		BufferedReader reader = new BufferedReader(fr);
		ArrayList<String> input = new ArrayList<String>();
		String currentLine = null;
		do {
			currentLine = reader.readLine();
			input.add(currentLine);
		} while (!currentLine.contains("process() -- END"));
		DBPTransaction dbpTransaction = new DBPTransaction(input);
		assertEquals("00:00:51.507", dbpTransaction.getEndTime(input));
	}
	
	@Test
	public void testGetPostillionRequestElapsedTime() throws Exception {
		FileReader fr = new FileReader("VISALogs.20150127.txt");
		BufferedReader reader = new BufferedReader(fr);
		ArrayList<String> input = new ArrayList<String>();
		String currentLine = null;
		do {
			currentLine = reader.readLine();
			input.add(currentLine);
		} while (!currentLine.contains("process() -- END"));
		DBPTransaction dbpTransaction = new DBPTransaction(input);
		assertEquals("38", dbpTransaction.getPostillionRequestElapsedTime(input));
	}
	
	@Test
	public void testGetPostillionResponseElapsedTime() throws Exception {
		FileReader fr = new FileReader("VISALogs.20150127.txt");
		BufferedReader reader = new BufferedReader(fr);
		ArrayList<String> input = new ArrayList<String>();
		String currentLine = null;
		do {
			currentLine = reader.readLine();
			input.add(currentLine);
		} while (!currentLine.contains("process() -- END"));
		DBPTransaction dbpTransaction = new DBPTransaction(input);
		assertEquals("165", dbpTransaction.getPostillionResponseElapsedTime(input));
	}
	
	@Test
	public void testGetVISAResponseElapsedTime() throws Exception {
		FileReader fr = new FileReader("VISALogs.20150127.txt");
		BufferedReader reader = new BufferedReader(fr);
		ArrayList<String> input = new ArrayList<String>();
		String currentLine = null;
		do {
			currentLine = reader.readLine();
			input.add(currentLine);
		} while (!currentLine.contains("process() -- END"));
		DBPTransaction dbpTransaction = new DBPTransaction(input);
		assertEquals("61", dbpTransaction.getVISAResponseElapsedTime(input));
	}
	
	@Test
	public void testGetTotalCycleTime() throws Exception {
		FileReader fr = new FileReader("VISALogs.20150127.txt");
		BufferedReader reader = new BufferedReader(fr);
		ArrayList<String> input = new ArrayList<String>();
		String currentLine = null;
		do {
			currentLine = reader.readLine();
			input.add(currentLine);
		} while (!currentLine.contains("process() -- END"));
		DBPTransaction dbpTransaction = new DBPTransaction(input);
		assertEquals("266", dbpTransaction.getTotalCycleTime(input));
	}
	
	@Test
	public void testPrintToCSVString() throws Exception {
		FileReader fr = new FileReader("VISALogs.20150127.txt");
		BufferedReader reader = new BufferedReader(fr);
		ArrayList<String> input = new ArrayList<String>();
		String currentLine = null;
		do {
			currentLine = reader.readLine();
			input.add(currentLine);
		} while (!currentLine.contains("process() -- END"));
		DBPTransaction dbpTransaction = new DBPTransaction(input);
		System.out.println(dbpTransaction.toCSVString());
	}
}
