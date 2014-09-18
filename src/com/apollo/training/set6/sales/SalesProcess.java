package com.apollo.training.set6.sales;

import java.sql.SQLException;
import java.sql.Timestamp;

public interface SalesProcess {
	/**
	 * Creates a sales document.
	 * @param effectiveDate the date where the purchase/sales is effective
	 * @param customerID the ID of the customer
	 * @return a specific sales_id
	 */
	public int createSalesDocument(Timestamp effectiveDate, int customerID);
	/**
	 * Orders a specific product existing on the products list.
	 * @param salesID the sales_id existing in the sales document
	 * @param productID the product_id of the existing product
	 * @param quantity the number of orders in the specific product
	 * @throws SQLException exception when errors on querying happened
	 */
	public void orderProduct(int salesID, String productID, int quantity) throws SQLException;
	/**
	 * Lists all data existing on the Sales database.
	 */
	public void listAll();
	/**
	 * List all data/sales document of a specific customer.
	 * @param customerID the customer's ID
	 */
	public void listOrdersofCustomer(int customerID);
	/**
	 * Records all data committed and closes the database connection.
	 */
	public void saveDatabase();

}
