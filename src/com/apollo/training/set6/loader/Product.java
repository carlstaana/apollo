package com.apollo.training.set6.loader;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("product")
public class Product {
	@XStreamAlias("id")
	private String id;

	@XStreamAlias("name")
	private String name;

	public Product(String i, String string) {
		id = i;
		name = string;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
