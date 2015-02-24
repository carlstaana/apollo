package com.apollo.dbp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBPExcelFileGenerator {

	private static String path;
	
	private FileReader fr;
	
	private BufferedReader reader;
	
	private static LogType logType;
	
	private static ArrayList<DBPTransaction> transactions = new ArrayList<DBPTransaction>();
	
	public enum LogType { CAS, VISA }
	
	public static void main(String[] args) {
		for (String string : args) {
			if (string.endsWith(".txt")) {
				new DBPExcelFileGenerator(string);
				generateCSVFile();
			}
		}
	}
	
	public DBPExcelFileGenerator(String path) {
		DBPExcelFileGenerator.path = path;
		transactions = new ArrayList<DBPTransaction>();
		
		if (isFileExists()) {
			if (path.startsWith("CAS")) {
				logType = LogType.CAS;
			}
			else if (path.startsWith("VISA")) {
				logType = LogType.VISA;
			}
			else {
				System.err.println("File not compatible for DBP Excel File Generator");
			}
			
			if (!isFileClean()) {
				cleanFile();
			}
			
			try {
				fr = new FileReader(DBPExcelFileGenerator.path);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			reader = new BufferedReader(fr);
			
			saveTransactions();
		}
		else {
			System.err.println("File " + path + " not found.");
		}
	}

	public void saveTransactions() {
		String currentLine = null;
		ArrayList<DBPTransaction> transactionBlocks = new ArrayList<DBPTransaction>();
		try {
			currentLine = reader.readLine();
			do {
				if (currentLine.contains("process() -- START")) {
					transactionBlocks.add(new DBPTransaction(currentLine, getRRN(currentLine)));
				}
				else if (currentLine.contains("process() -- END")) {
					Integer indexToBeRemoved = null;
					for (int i = 0; i < transactionBlocks.size(); i++) {
						if (currentLine.contains(transactionBlocks.get(i).rrn)) {
							transactionBlocks.get(i).addToTransactionBlock(currentLine);
							if (!transactionBlocks.get(i).output.isEmpty()) {
								transactions.add(transactionBlocks.get(i));
								indexToBeRemoved = i;
							}
							break;
						}
					}
					
					transactionBlocks.remove(indexToBeRemoved.intValue());
				}
				else {
					for (DBPTransaction dbpTransaction : transactionBlocks) {
						if (currentLine.contains(dbpTransaction.rrn)) {
							dbpTransaction.addToTransactionBlock(currentLine);
						}
					}
				}
				
				currentLine = reader.readLine();
			} while (currentLine != null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getRRN(String currentLine) {
		String[] splitLines = currentLine.split(" ");
		return splitLines[3].substring(4, 16);
	}

	public boolean isFileExists() {
		File file = new File(path);
		if (file.exists() && !file.isDirectory()) {
			return true;
		}
		return false;
	}

	public boolean isFileClean() {
		FileReader fr = null;
		String currentLine = null;
		try {
			fr = new FileReader(DBPExcelFileGenerator.path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fr);
		try {
			while ((currentLine=reader.readLine()) != null) {
				if (!currentLine.startsWith(logType.toString())) {
					return false;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	public void cleanFile() {
		File temp = new File("temp.txt");
		File targetFile = new File(path);
		FileReader fr = null;
		FileWriter fw = null;
		
		if (!temp.exists()) {
			try {
				temp.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			fr = new FileReader(targetFile);
			fw = new FileWriter(temp);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		BufferedWriter bw = new BufferedWriter(fw);
		String currentLine = null;
		String newLine = null;
		
		try {
			while ((currentLine=br.readLine()) != null) {
				bw.write("\n");
				if (!currentLine.startsWith(logType.toString() + " 2015")) {
					int index = 0;
					for (int i = 0; i < currentLine.length(); i++) {
						if (logType == LogType.CAS) {
							if (currentLine.charAt(i) == 'C' && currentLine.charAt(i+1) == 'A' && currentLine.charAt(i+2) == 'S') {
								index = i;
								break;
							}
						}
						else if (logType == LogType.VISA) {
							if (currentLine.charAt(i) == 'V' && currentLine.charAt(i+1) == 'I' && currentLine.charAt(i+2) == 'S' && currentLine.charAt(i+3) == 'A') {
								index = i;
								break;
							}
						}
					}
					
					newLine = currentLine.substring(index);
					bw.write(newLine);
				}
				else {
					bw.write(currentLine);
				}
			}
			
			br.close();
			bw.close();
			
			targetFile.delete();
			temp.renameTo(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public static void generateCSVFile() {
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
			e.printStackTrace();
		}
	}

	public void testRead() {
		ArrayList<String> testResult = new ArrayList<String>();
		String currentline = null;
		try {
			testResult.add(this.reader.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (testResult.get(0).contains("process() -- START")) {
			do {
				try {
					currentline = this.reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				testResult.add(currentline);
			} while (!currentline.contains("process() -- END"));
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
}
