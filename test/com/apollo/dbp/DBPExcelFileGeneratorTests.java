package com.apollo.dbp;

import static org.junit.Assert.*;

import org.junit.Test;

public class DBPExcelFileGeneratorTests {

//	@Test
//	public void testRightFileExists() {
//		DBPExcelFileGenerator excel = new DBPExcelFileGenerator("VISALogs.20150121.txt");
//		assertTrue(excel.isFileExists());
//	}
//	
//	@Test
//	public void testWrongFileExists() throws Exception {
//		DBPExcelFileGenerator excel = new DBPExcelFileGenerator("CARLLogs.20150121.txt");
//		assertFalse(excel.isFileExists());
//	}
//
//	@Test
//	public void testSaveOneTransaction() throws Exception {
//		DBPExcelFileGenerator excel = new DBPExcelFileGenerator("VISALogs.20150121.txt");
//		excel.testRead();
//	}
//	
//	@Test
//	public void testCountTotalTransactions() throws Exception {
//		DBPExcelFileGenerator excel = new DBPExcelFileGenerator("VISALogs.20150121.txt");
//		excel.testCountTransactions();
//	}
	
//	@Test
//	public void testSaveTransactions() throws Exception {
//		DBPExcelFileGenerator excel = new DBPExcelFileGenerator("VISALogs.20150121.txt");
//		excel.saveTransactions();
//		System.out.println("Total Transactions: " + excel.getTransactions().size());
//		assertTrue(!excel.getTransactions().isEmpty());
//	}
	
	@Test
	public void generateExcelCSVFile() throws Exception {
		DBPExcelFileGenerator excel = new DBPExcelFileGenerator("CASLogs.20150122.txt");
		excel.generateCSVFile();
	}
}
