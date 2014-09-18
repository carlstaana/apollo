package com.apollo.training.set6;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.apollo.training.set6.sales.Sales;
import com.apollo.training.set6.sales.SalesProcess;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SalesTests {
	public SalesProcess sales = new Sales();

	public void setup() {
		Timestamp effectiveDate = Timestamp.valueOf("2014-07-05 23:03:20");
		int salesID = sales.createSalesDocument(effectiveDate, 3);
		String productID = "4806985232565";
		try {
			sales.orderProduct(salesID, productID, 5);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		productID = "4805279843658";
		try {
			sales.orderProduct(salesID, productID, 10);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// test adding another sales document
		effectiveDate = Timestamp.valueOf("2014-07-08 09:52:20");
		salesID = sales.createSalesDocument(effectiveDate, 3);
		productID = "4809648741447";
		try {
			sales.orderProduct(salesID, productID, 7);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		productID = "4806985232565";
		try {
			sales.orderProduct(salesID, productID, 6);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		effectiveDate = Timestamp.valueOf("2014-07-08 09:52:20");
		salesID = sales.createSalesDocument(effectiveDate, 4);
		productID = "4809648741447";
		try {
			sales.orderProduct(salesID, productID, 7);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		productID = "4806985232565";
		try {
			sales.orderProduct(salesID, productID, 6);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test1CreateSalesDocument() throws SQLException {
		setup();

		// check if sales documents are added
		int expected = 3; // expected count of documents are 3
		Connection con = DriverManager.getConnection("jdbc:derby:BigJavaDB");
		Statement command = con.createStatement();
		ResultSet result = command.executeQuery("SELECT COUNT(*) FROM sales");
		result.next();
		int actual = result.getInt(1);
		assertEquals(expected, actual);
	}

	// TODO editing sales order document

	@Test
	public void test2ListAll() throws SQLException {
		sales.listAll();

		// check if sales documents are added
		int expected = 3; // expected count of documents are 3
		Connection con = DriverManager.getConnection("jdbc:derby:BigJavaDB");
		Statement command = con.createStatement();
		ResultSet result = command.executeQuery("SELECT COUNT(*) FROM sales");
		result.next();
		int actual = result.getInt(1);
		assertEquals(expected, actual);
	}

	@Test
	public void test3SalesofSpecificCustomer() throws SQLException {
		int customerID = 3;
		sales.listOrdersofCustomer(customerID);

		// check if sales documents are added
		int expected = 2; // expected count of documents are 3
		Connection con = DriverManager.getConnection("jdbc:derby:BigJavaDB");
		Statement command = con.createStatement();
		ResultSet result = command.executeQuery("SELECT COUNT(*) FROM sales WHERE customer_id = "+customerID+"");
		result.next();
		int actual = result.getInt(1);
		assertEquals(expected, actual);
	}

	@Test
	public void test4SaveDatabase() {
		boolean valid;
		try {
			sales.saveDatabase();
			valid = true;
		} catch (Exception e) {
			valid = false;
		}
		assertTrue(valid);
	}

}
