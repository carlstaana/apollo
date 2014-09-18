package com.apollo.training.set6.sales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Sales implements SalesProcess {
	String url = "jdbc:derby:BigJavaDB";
	Connection con = null;
	Statement stat;

	public Sales() {
		try {
			// open the connection
			con = DriverManager.getConnection(url);
			stat = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			// if the table already exists
			stat.execute("SELECT * FROM sales");
		} catch (SQLException e) {
			// create the table if the execution above occurred errors
			try {
				stat.execute("CREATE TABLE sales ("
						+ "sales_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
						+ "create_date TIMESTAMP NOT NULL, "
						+ "effective_date TIMESTAMP, "
						+ "customer_id BIGINT NOT NULL, "
						+ "PRIMARY KEY (sales_id))");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		try {
			// if orders table already exists in the database
			stat.execute("SELECT * FROM orders");
		} catch (SQLException e) {
			// create if executing the command above is error
			try {
				stat.execute("CREATE TABLE orders ("
						+ "id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
						+ "sales_id BIGINT NOT NULL, "
						+ "product_id VARCHAR(255) NOT NULL, "
						+ "customer_id BIGINT NOT NULL, "
						+ "quantity BIGINT NOT NULL, "
						+ "unit_price DECIMAL(19,3) NOT NULL, "
						+ "total_price DECIMAL(19,3) NOT NULL, "
						+ "FOREIGN KEY (sales_id) REFERENCES sales(sales_id))");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public int createSalesDocument(Timestamp effectiveDate, int customerID) {
		// TODO catching errors and exceptions

		Timestamp createdDate = new Timestamp(System.currentTimeMillis());
		int ID = 0;
		try {
			stat.execute("INSERT INTO sales (create_date, effective_date, customer_id) VALUES ("
					+ "'"
					+ createdDate
					+ "', '"
					+ effectiveDate
					+ "', "
					+ customerID + ")");
			ResultSet result = stat
					.executeQuery("SELECT sales_id FROM sales WHERE create_date = '"
							+ createdDate + "'");
			result.next();
			ID = result.getInt("sales_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ID;
	}

	public void orderProduct(int salesID, String productID, int quantity)
			throws SQLException {

		// get the create_date from TABLE sales
		ResultSet result = stat
				.executeQuery("SELECT effective_date FROM sales WHERE sales_id = "
						+ salesID + "");
		result.next();
		Timestamp compareDate = result.getTimestamp("effective_date");
		// get the customer id from TABLE sales
		result = stat
				.executeQuery("SELECT customer_id FROM sales WHERE sales_id = "
						+ salesID + "");
		result.next();
		int customerID = result.getInt("customer_id");
		// get the unit price or amount from TABLE price
		result = stat
				.executeQuery("SELECT price_id, amount, from_date, thru_date FROM price WHERE product_id = '"
						+ productID + "'");
		double amount = 0;

		while (result.next()) {
			Timestamp fromDate = result.getTimestamp("from_date");
			Timestamp thruDate = result.getTimestamp("thru_date");
			if (compareDate.after(fromDate) && compareDate.before(thruDate)) {
				amount = result.getDouble("amount");
				break;
			}
		}

		// compute total price
		double totalPrice = amount * quantity;
		// insert into TABLE orders
		stat.execute("INSERT INTO orders (sales_id, product_id, customer_id, quantity, unit_price, total_price)"
				+ "VALUES ("
				+ salesID
				+ ", '"
				+ productID
				+ "', "
				+ customerID
				+ ", " + quantity + ", " + amount + ", " + totalPrice + ")");
	}

	@Override
	public void listAll() {

		// select all data from TABLE sales
		ResultSet getSales = null;
		try {
			Statement comm = con.createStatement();
			getSales = comm.executeQuery("SELECT * FROM sales");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while (getSales.next()) {
				int salesID = getSales.getInt("sales_id");
				System.out.println("Sales ID: [" + salesID + "]");
				System.out.println("Document created: "
						+ getSales.getTimestamp("create_date"));
				System.out.println("Effective date: "
						+ getSales.getTimestamp("effective_date"));
				System.out.println("---Customer Information---");
				System.out.println("   Customer ID: "
						+ getSales.getInt("customer_id"));
				ResultSet getCustomerInfo = stat
						.executeQuery("SELECT * FROM customer WHERE customer_id = "
								+ getSales.getInt("customer_id") + "");
				getCustomerInfo.next();
				// printing the name will be optional. check if middle name is
				// NULL
				if (getCustomerInfo.getString("middle_name") == null) {
					System.out.println("   Name: "
							+ getCustomerInfo.getString("first_name") + " "
							+ getCustomerInfo.getString("last_name"));
				} else {
					System.out.println("   Name: "
							+ getCustomerInfo.getString("first_name") + " "
							+ getCustomerInfo.getString("middle_name") + " "
							+ getCustomerInfo.getString("last_name"));
				}
				System.out.println("   Address: "
						+ getCustomerInfo.getString("address"));
				System.out.println("   Telephone: "
						+ getCustomerInfo.getString("telephone"));
				System.out.println("   E-mail: "
						+ getCustomerInfo.getString("email"));
				System.out.println("---Products Ordered---");
				Statement command = con.createStatement();
				ResultSet getProducts = command
						.executeQuery("SELECT * FROM orders WHERE sales_id = "
								+ salesID + "");
				System.out
						.println("\t[Quantity]\t[Product Name]\t\t\t\t[Unit Price]\t[Total Price]");
				while (getProducts.next()) {
					// get the product name
					ResultSet getProdName = stat
							.executeQuery("SELECT product_name FROM product WHERE product_id = '"
									+ getProducts.getString("product_id") + "'");
					getProdName.next();
					int quantity = getProducts.getInt("quantity");
					// alignment will vary depending of the length of the
					// product name
					if (getProdName.getString("product_name").length() > 25) {
						System.out.println("\t" + quantity + "\t\t"
								+ getProdName.getString("product_name") + "\t"
								+ getProducts.getDouble("unit_price") + "\t\t"
								+ getProducts.getDouble("total_price"));
					} else if (getProdName.getString("product_name").length() < 25
							&& getProdName.getString("product_name").length() > 15) {
						System.out.println("\t" + quantity + "\t\t"
								+ getProdName.getString("product_name")
								+ "\t\t\t"
								+ getProducts.getDouble("unit_price") + "\t\t"
								+ getProducts.getDouble("total_price"));
					} else {
						System.out.println("\t" + quantity + "\t\t"
								+ getProdName.getString("product_name")
								+ "\t\t\t\t"
								+ getProducts.getDouble("unit_price") + "\t\t"
								+ getProducts.getDouble("total_price"));
					}

				}
				System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\n\n");
	}

	@Override
	public void listOrdersofCustomer(int customerID) {
		// this method must list all the sales order by a specific customer
		// display properly the customer's name and his orders
		/*
		 * Sales order of customer ID: ['customer's id'] ['customer's name']
		 * Sales ID: ['sales_id'] ---Products Ordered--- ['list of products']
		 * 
		 * ['sales_id'] ['list of products']
		 */
		ResultSet getSales = null;
		try {
			Statement statSales = con.createStatement();
			getSales = statSales
					.executeQuery("SELECT * FROM sales WHERE customer_id = "
							+ customerID + "");
			// get the customer's name
			Statement statName = con.createStatement();
			ResultSet getName = statName
					.executeQuery("SELECT first_name, middle_name, last_name FROM customer WHERE customer_id = "
							+ customerID + "");
			getName.next();
			// varies if the middle_name is NULL
			if (getName.getString("middle_name") == null) {
				System.out.println("Sales orders of customer ID: ["
						+ customerID + "]" + " "
						+ getName.getString("first_name") + " "
						+ getName.getString("last_name"));
			} else {
				System.out.println("Sales orders of customer ID: ["
						+ customerID + "]" + " "
						+ getName.getString("first_name") + " "
						+ getName.getString("middle_name") + " "
						+ getName.getString("last_name"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (getSales.next()) {
				int salesID = getSales.getInt("sales_id");
				System.out.println("Sales ID: [" + salesID + "]");
				System.out.println("Document created: "
						+ getSales.getTimestamp("create_date"));
				System.out.println("Effective date: "
						+ getSales.getTimestamp("effective_date"));
				System.out.println("---Products Ordered---");
				Statement statProducts = con.createStatement();
				ResultSet getProducts = statProducts
						.executeQuery("SELECT * FROM orders WHERE customer_id = "
								+ customerID
								+ " AND sales_id = "
								+ salesID
								+ "");
				System.out
						.println("\t[Quantity]\t[Product Name]\t\t\t\t[Unit Price]\t[Total Price]");
				while (getProducts.next()) {
					// get the product name
					ResultSet getProdName = stat
							.executeQuery("SELECT product_name FROM product WHERE product_id = '"
									+ getProducts.getString("product_id") + "'");
					getProdName.next();
					int quantity = getProducts.getInt("quantity");
					if (getProdName.getString("product_name").length() > 25) {
						System.out.println("\t" + quantity + "\t\t"
								+ getProdName.getString("product_name") + "\t"
								+ getProducts.getDouble("unit_price") + "\t\t"
								+ getProducts.getDouble("total_price"));
					} else if (getProdName.getString("product_name").length() < 25
							&& getProdName.getString("product_name").length() > 15) {
						System.out.println("\t" + quantity + "\t\t"
								+ getProdName.getString("product_name")
								+ "\t\t\t"
								+ getProducts.getDouble("unit_price") + "\t\t"
								+ getProducts.getDouble("total_price"));
					} else {
						System.out.println("\t" + quantity + "\t\t"
								+ getProdName.getString("product_name")
								+ "\t\t\t\t"
								+ getProducts.getDouble("unit_price") + "\t\t"
								+ getProducts.getDouble("total_price"));
					}
				}
				System.out.println();
			}
			System.out.println("\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void saveDatabase() {
		try {
			stat.execute("DELETE FROM orders");
			stat.execute("DELETE FROM sales");
			con.close();
			System.out.println("Database saved!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
}
