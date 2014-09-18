package com.apollo.training.set6.loader;

import java.math.BigInteger;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("customer")
public class Customer {
	@XStreamAlias("id")
	private BigInteger customerID;
	@XStreamAlias("firstName")
	private String firstName;
	@XStreamAlias("middleName")
	private String middleName;
	@XStreamAlias("lastName")
	private String lastName;
	@XStreamAlias("address")
	private String address;
	@XStreamAlias("telephone")
	private String telephone;
	@XStreamAlias("email")
	private String email;

	public Customer(BigInteger customerID, String firstName, String middleName,
			String lastName, String address, String telephone, String email) {
		this.customerID = customerID;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.address = address;
		this.telephone = telephone;
		this.email = email;
	}

	public BigInteger getCustomerID() {
		return customerID;
	}

	public void setCustomerID(BigInteger customerID) {
		this.customerID = customerID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
