package com.apollo.training.set6.loader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import com.thoughtworks.xstream.XStream;

public class Loader {
	private FileReader reader;
	private ProductList prodList;
	private PriceList priceList;
	private CustomerList custList;
	private String url = "jdbc:derby:BigJavaDB";
	private Connection con = null;

	public void ParseXML() throws FileNotFoundException {
		// --PARSING products.xml--
		try {
			reader = new FileReader("products.xml");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("products.xml is not found");
		}

		XStream xstream = new XStream();
		xstream.processAnnotations(ProductList.class);
		xstream.processAnnotations(Product.class);
		prodList = (ProductList) xstream.fromXML(reader);

		// --PARSING prices.xml--
		try {
			reader = new FileReader("prices.xml");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("prices.xml is not found");
		}

		xstream = new XStream();
		xstream.processAnnotations(PriceList.class);
		xstream.processAnnotations(Price.class);
		priceList = (PriceList) xstream.fromXML(reader);

		// --PARSING customers.xml--
		try {
			reader = new FileReader("customers.xml");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("customers.xml is not found");
		}

		xstream = new XStream();
		xstream.processAnnotations(CustomerList.class);
		xstream.processAnnotations(Customer.class);
		custList = (CustomerList) xstream.fromXML(reader);
	}

	
	public void CreateProductTable() throws SQLException {
		// --CREATING "product" TABLE
		try {
			con = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new SQLException("Cannot connect to database");
		}
		Statement command = con.createStatement();
		try {
			// if TABLE product already exists
			command.execute("SELECT * FROM product");
		} catch (SQLException e) {
			// else CREATE the table
			command.execute("CREATE TABLE product ("
					+ "product_id VARCHAR(20) NOT NULL, "
					+ "product_name VARCHAR(255), "
					+ "PRIMARY KEY (product_id)" + ")");
		}
		for (int i = 0; i < prodList.getProduct().size(); i++) {
			Product selectedProd = (Product) prodList.getProduct().get(i);
			String selectedID = selectedProd.getId();
			String selectedName = selectedProd.getName();
			try {
				command.execute("INSERT INTO product VALUES('" + selectedID
						+ "', '" + selectedName + "')");
			} catch (SQLIntegrityConstraintViolationException e) {
				throw new SQLException("Data inserted in TABLE 'product' already exists.");
			} finally {
				System.out.println();
				continue;
			}
		}
		// close database connection
		con.close();
	}
	
	public void CreatePriceTable() throws SQLException {
		// creating "price" table
		try {
			con = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new SQLException("Cannot connect to database");
		}
		Statement command = con.createStatement();
		try {
			command.execute("SELECT * FROM price");
		} catch (SQLException e) {
			command.execute("CREATE TABLE price ("
					+ "price_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
					+ "amount DECIMAL(19,2) NOT NULL, "
					+ "currency VARCHAR(100) DEFAULT 'PHP', "
					+ "from_date TIMESTAMP NOT NULL, "
					+ "thru_date TIMESTAMP, "
					+ "product_id VARCHAR(20) NOT NULL, "
					+ "PRIMARY KEY (price_id), "
					+ "FOREIGN KEY (product_id) REFERENCES product(product_id))");
		}
		
		try {
			for (int j = 0; j < priceList.getPrice().size(); j++) {
				Price selectPrice = (Price) priceList.getPrice().get(j);
				command.execute("INSERT INTO price (amount, currency, from_date, thru_date, product_id)"
						+ "VALUES ("
						+ selectPrice.getAmount().getAmount()
						+ ", '"
						+ selectPrice.getAmount().getCurrency()
						+ "', "
						+ "'"
						+ selectPrice.getFromDate()
						+ "', '"
						+ selectPrice.getThruDate()
						+ "', '"
						+ selectPrice.getProductId() + "')");

			}
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLException("Data inserted in TABLE 'price' already exists.");
		} finally {
			System.out.println();
		}
		// close database connection
		con.close();
	}
	
	public void CreateCustomerTable() throws SQLException {
		// creating "customer" table
		try {
			con = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new SQLException("Cannot connect to database");
		}
		Statement command = con.createStatement();
		try {
			command.execute("SELECT * FROM customer");
		} catch (SQLException e) {
			command.execute("CREATE TABLE customer ("
					+ "customer_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
					+ "first_name VARCHAR(255) NOT NULL, "
					+ "middle_name VARCHAR(255), "
					+ "last_name VARCHAR(255) NOT NULL, "
					+ "address VARCHAR(255), " + "telephone VARCHAR(255), "
					+ "email VARCHAR(255), " + "PRIMARY KEY (customer_id))");
		}
		try {
			for (int i = 0; i < custList.getCustomer().size(); i++) {
				Customer selectedCustomer = (Customer) custList
						.getCustomer().get(i);
				command.execute("INSERT INTO customer (first_name, middle_name, last_name, address, telephone, email) "
						+ "VALUES ('"
						+ selectedCustomer.getFirstName()
						+ "', '"
						+ selectedCustomer.getMiddleName()
						+ "', "
						+ "'"
						+ selectedCustomer.getLastName()
						+ "', '"
						+ selectedCustomer.getAddress()
						+ "', "
						+ "'"
						+ selectedCustomer.getTelephone()
						+ "', '"
						+ selectedCustomer.getEmail() + "')");
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLException("Data inserted in TABLE 'customer' already exists.");
		} finally {
			System.out.println();
		}
		// close database connection
		con.close();
	}
	
}
