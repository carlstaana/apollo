package com.apollo.training.finals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class House implements Serializable {
	private String address;
	private int numOfBedrooms;
	private BigDecimal price;
	private boolean isPending;
	private Heating status;
	private static ArrayList<House> houseList = new ArrayList<House>();

	public String getAddress() {
		return address;
	}

	public int getNumOfBedrooms() {
		return numOfBedrooms;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public boolean isPending() {
		return isPending;
	}

	public Heating getStatus() {
		return status;
	}

	static void register(House house) {
		houseList.add(house);
	}

	@SuppressWarnings("unchecked")
	static void load(File dataFile) throws FileNotFoundException, IOException, ClassNotFoundException {
		@SuppressWarnings("resource")
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFile));
		houseList = (ArrayList<House>) in.readObject();
	}

	public static ArrayList<House> getHouseList() {
		return houseList;
	}

	public enum Heating {
		OIL, GAS, ELECTRIC
	}
}
