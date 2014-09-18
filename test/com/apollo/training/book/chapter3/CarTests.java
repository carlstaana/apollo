package com.apollo.training.book.chapter3;

import static org.junit.Assert.*;

import org.junit.Test;

public class CarTests {

	@Test
	public void test() {
		Car car = new Car(50);
		
		car.addGas(20); // Tank 20 gallons
		car.drive(100); // Drive 100 miles
		double gasLeft = car.getGasInTank(); // Get gas remaining in tank
		
		// expected 18
		assertEquals(18, gasLeft, 0);
	}

}
