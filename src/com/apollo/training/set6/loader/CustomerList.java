package com.apollo.training.set6.loader;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("customerList")
public class CustomerList {
	@XStreamImplicit(itemFieldName = "customer")
	private List<?> customer = new ArrayList<Object>();

	public List<?> getCustomer() {
		return customer;
	}
}
