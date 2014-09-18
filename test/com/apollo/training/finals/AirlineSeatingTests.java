package com.apollo.training.finals;

import static org.junit.Assert.*;

import javax.management.InvalidAttributeValueException;

import org.junit.Test;

public class AirlineSeatingTests {

	@Test
	public void test() {
		AirlineSeating seating = new AirlineSeating();
		String output = seating.toString();
		try {
			seating.addPassengers("first", 2, "window");
			seating.addPassengers("first", 2, "window");
			seating.addPassengers("economy", 2, "window");
			seating.addPassengers("economy", 3, "aisle");
			seating.addPassengers("economy", 3, "aisle");
			seating.addPassengers("economy", 3, "center");
			output = seating.toString();
		} catch (InvalidAttributeValueException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			seating.addPassengers("first", 12, "window");
			fail("This must fail because max passengers are only 2");
			output = seating.toString();
		} catch (InvalidAttributeValueException e) {
			System.out.println(e.getMessage());
		}
	}

}
