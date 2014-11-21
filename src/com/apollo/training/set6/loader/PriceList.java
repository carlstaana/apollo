package com.apollo.training.set6.loader;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("priceList")
public class PriceList {
	@XStreamImplicit(itemFieldName = "price")
	private List<?> price = new ArrayList<Object>();

	public List<?> getPrice() {
		return price;
	}
}
