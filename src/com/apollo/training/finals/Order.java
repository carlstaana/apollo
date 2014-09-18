package com.apollo.training.finals;

import java.math.BigDecimal;

public class Order {
	public String itemName;
	public BigDecimal price;
	public boolean isOvernight;
	public BigDecimal shippingCost;
	
	public Order(String itemName, BigDecimal price, boolean isOvernight) {
		this.itemName = itemName;
		this.price = price;
		this.isOvernight = isOvernight;
	}
	
	public BigDecimal getTotal() {
		price = price.add(shippingCost);
		return price;
	}
	
	public BigDecimal getShippingCost() {
		shippingCost = BigDecimal.valueOf(0);
		
		if (price.compareTo(BigDecimal.valueOf(10)) == -1) {
			shippingCost = shippingCost.add(BigDecimal.valueOf(2.00));
		} else {
			shippingCost = shippingCost.add(BigDecimal.valueOf(3.00));
		}
		
		if (isOvernight) {
			shippingCost = shippingCost.add(BigDecimal.valueOf(5.00));
		}
		
		return shippingCost;
	}
	
}
