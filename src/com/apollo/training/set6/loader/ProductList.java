package com.apollo.training.set6.loader;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("productList")
public class ProductList {
	@XStreamImplicit(itemFieldName = "product")
	private List<?> product = new ArrayList<Object>();

	public List<?> getProduct() {
		return product;
	}
}
