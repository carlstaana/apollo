package com.apollo.dbp;

import java.util.ArrayList;

public class DBPTransaction {

	private ArrayList<String> transactionBlock = new ArrayList<String>();
	
	public ArrayList<String> output = new ArrayList<String>();
	
	private TransactionType tranType;
	
	public String rrn;
	
	public enum TransactionType { REGULAR_TRANSACTION, BALANCE_INQUIRY, CASH_OUT, REVERSAL, UNKNOWN }
	
	public DBPTransaction(ArrayList<String> transactionBlock) {
		this.transactionBlock = transactionBlock;
		
		processOutput();
	}

	public DBPTransaction(String startLine, String rrn) {
		this.rrn = rrn;
		addToTransactionBlock(startLine);
	}

	public void addToTransactionBlock(String currentLine) {
		transactionBlock.add(currentLine);
		if (currentLine.contains("process() -- END")) {
			processOutput();
		}
	}

	private void processOutput() {
		// type | rrn | response code | TIME[process() -- START] | TIME[Request to Postillion] | TIME[Response from Postillion] | TIME[process() -- END] | TIME_ELAPSED[VISA Request -- END] | TIME_ELAPSED[Postillion Received] | TIME_ELAPSED[Postillion Response -- END] | TIME_ELAPSED[TOTAL CYCLE TIME]
		
		// type
		output.add(getTransactionType(transactionBlock));
		// rrn
		output.add(getRRN(transactionBlock));
		// response code
		output.add(getResponseCode(transactionBlock));
		// TIME[process() -- START]
		output.add(getStartTime(transactionBlock));
		// TIME[Request to Postillion]
		output.add(getPostillionRequestTime(transactionBlock));
		// TIME[Response from Postillion] | TIME[process() -- END]
		output.add(getPostillionResponseTime(transactionBlock));
		// TIME[process() -- END]
		output.add(getEndTime(transactionBlock));
		// TIME_ELAPSED[VISA Request -- END]
		output.add(getPostillionRequestElapsedTime(transactionBlock));
		// TIME_ELAPSED[Postillion Received] | TIME_ELAPSED[Postillion Response -- END]
		output.add(getPostillionResponseElapsedTime(transactionBlock));
		output.add(getVISAResponseElapsedTime(transactionBlock));
		// TIME_ELAPSED[TOTAL CYCLE TIME]
		output.add(getTotalCycleTime(transactionBlock));
	}

	public String getTransactionType(ArrayList<String> transactionBlock) {
		String startLine = transactionBlock.get(0);
		String[] splitLines = startLine.split(" ");
		if (splitLines.length < 8) {
			return "TRANSACTION";
		}
		else {
			String output = splitLines[8];
			switch (output) {
			case "REGULAR_TRANSACTION":
				tranType = TransactionType.REGULAR_TRANSACTION;
				break;
			case "BALANCE_INQUIRY":
				tranType = TransactionType.BALANCE_INQUIRY;
				break;
			case "CASH_OUT":
				tranType = TransactionType.CASH_OUT;
				break;
			case "REVERSAL":
				tranType = TransactionType.REVERSAL;
				break;
			case "UNKNOWN":
				tranType = TransactionType.UNKNOWN;
				break;
			}
			return output;
		}
	}

	public String getRRN(ArrayList<String> transactionBlock) {
		String startLine = transactionBlock.get(0);
		String[] splitLines = startLine.split(" ");
		return splitLines[3].substring(4, 16);
	}

	public String getResponseCode(ArrayList<String> transactionBlock) {
		String capturedLine = null;
		for (String string : transactionBlock) {
			if (string.contains("Response Code")) {
				capturedLine = string;
				break;
			}
		}
		if (capturedLine == null) {
			return "N/A";
		}
		else {
			return capturedLine.split(" ")[6];
		}
	}

	public String getStartTime(ArrayList<String> transactionBlock) {
		return transactionBlock.get(0).split(" ")[2];
	}

	public String getPostillionRequestTime(ArrayList<String> transactionBlock) {
		String capturedLine = null;
		for (String string : transactionBlock) {
			if (string.contains("Sending Request to Postillion")) {
				capturedLine = string;
				break;
			}
		}
		return capturedLine.split(" ")[2];
	}

	public String getPostillionResponseTime(ArrayList<String> input) {
		String capturedLine = null;
		for (String string : transactionBlock) {
			if (string.contains("Response from Postillion Received")) {
				capturedLine = string;
				break;
			}
		}
		if (capturedLine == null) {
			return "N/A";
		}
		else {
			return capturedLine.split(" ")[2];
		}
	}

	public String getEndTime(ArrayList<String> transactionBlock) {
		return transactionBlock.get(transactionBlock.size()-1).split(" ")[2];
	}

	public String getPostillionRequestElapsedTime(ArrayList<String> transactionBlock) {
		String capturedLine = null;
		for (String string : transactionBlock) {
			if (string.contains("Processing") && string.contains("Request -- END")) {
				capturedLine = string;
				break;
			}
		}
		return capturedLine.split(" ")[12];
	}

	public String getPostillionResponseElapsedTime(ArrayList<String> transactionBlock) {
		String capturedLine = null;
		for (String string : transactionBlock) {
			if (string.contains("Response from Postillion Received")) {
				capturedLine = string;
				break;
			}
		}
		if (capturedLine == null) {
			return "N/A";
		}
		else {
			return capturedLine.split(" ")[11];
		}
	}

	public String getVISAResponseElapsedTime(ArrayList<String> input) {
		String capturedLine = null;
		for (String string : transactionBlock) {
			if (string.contains("Processing Postillion Response -- END")) {
				capturedLine = string;
				break;
			}
		}
		if (capturedLine == null) {
			return "N/A";
		}
		else {
			return capturedLine.split(" ")[12];
		}
	}

	public String getTotalCycleTime(ArrayList<String> transactionBlock) {
		return transactionBlock.get(transactionBlock.size()-1).split(" ")[11];
	}
	
	public String toCSVString() {
		String output = "";
		for (int i = 0; i < this.output.size(); i++) {
			output += this.output.get(i);
			if (i != this.output.size() - 1) {
				output += "|";
			}
		}
		
		return output;
	}
}
