package com.apollo.dbp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBPExcelFileGenerator {

	private String path;
	
	private FileReader fr;
	
	private BufferedReader reader;
	
	private LogType logType;
	
	private ArrayList<DBPTransaction> transactions = new ArrayList<DBPTransaction>();
	
	public enum LogType { CAS, VISA }
	
	public DBPExcelFileGenerator(String path) {
		this.path = path;
		if (isFileExists()) {
			try {
				fr = new FileReader(this.path);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reader = new BufferedReader(fr);
			
			if (path.startsWith("CAS")) {
				logType = LogType.CAS;
			}
			else if (path.startsWith("VISA")) {
				logType = LogType.VISA;
			}
			else {
				System.err.println("File not compatible for DBP Excel File Generator");
			}
			
			saveTransactions();
		}
		else {
			System.err.println("File " + path + " not found.");
		}
	}

	public void saveTransactions() {
		String currentLine = null;
		ArrayList<String> transactionBlock = new ArrayList<String>();
		try {
			currentLine = reader.readLine();
			do {
				if (currentLine.contains("process() -- START")) {
					transactionBlock = new ArrayList<String>();
					transactionBlock.add(currentLine);
				}
				else if (currentLine.contains("process() -- END")) {
					transactionBlock.add(currentLine);
					transactions.add(new DBPTransaction(transactionBlock));
				}
				else {
					transactionBlock.add(currentLine);
				}
				currentLine = reader.readLine();
			} while (currentLine != null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isFileExists() {
		File file = new File(path);
		if (file.exists() && !file.isDirectory()) {
			return true;
		}
		return false;
	}

	public void testRead() {
		ArrayList<String> testResult = new ArrayList<String>();
		String currentline = null;
		try {
			testResult.add(this.reader.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (testResult.get(0).contains("process() -- START")) {
			do {
				try {
					currentline = this.reader.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				testResult.add(currentline);
			} while (!currentline.contains("process() -- END"));
		}
		
		for (String string : testResult) {
			System.out.println(string);
		}
	}

	public void testCountTransactions() {
		long transactionCount = 0;
		String currentLine = null;
		try {
			currentLine = reader.readLine();
			do {
				if (currentLine.contains("process() -- END")) {
					transactionCount++;
				}
				currentLine = reader.readLine();
			} while (currentLine != null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Total Number of Transactions: " + transactionCount);
	}

	public String getPath() {
		return path;
	}

	public FileReader getFr() {
		return fr;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public LogType getLogType() {
		return logType;
	}

	public ArrayList<DBPTransaction> getTransactions() {
		return transactions;
	}

	public void generateCSVFile() {
		String dateInFile = null;
		if (logType == LogType.VISA) {
			dateInFile = path.substring(9, 17);
		}
		else {
			dateInFile = path.substring(8, 16);
		}
		long count = 1;
		try {
			PrintWriter writer = new PrintWriter(logType + "Transactions." + dateInFile + ".csv");
			writer.println("Transaction #|"
					+ "Transaction Type|"
					+ "Retrieval Reference Number|"
					+ "Response Code|"
					+ "Received Transaction from VISA|"
					+ "Forwarded Request to Postbridge|"
					+ "Received Response from Postbridge|"
					+ "Forwarded Response to VISA|"
					+ "Elapsed Time VISA Request to Postbridge Request (ms)|"
					+ "Elapsed Time Postbridge Request to Postbridge Response (ms)|"
					+ "Elapsed Time Postbridge Response to VISA Response (ms)|"
					+ "Total Cycle Time (ms)");
			for (DBPTransaction dbpTransaction : transactions) {
				writer.println(count + "|" + dbpTransaction.toCSVString());
				count++;
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
