package com.apollo.training.book.chapter3;

public class Car {
	private double efficiency; // miles per gallon
	private double gas;
	
	public Car(double efficiency) {
		this.efficiency = efficiency;
		this.gas = 0;
	}

	public void addGas(double gas) {
		System.out.println("Refilled " + gas + " gallons of fuel.");
		this.gas += gas;
	}

	public void drive(double distance) {
		System.out.println("Car traveled " + distance + " miles.");
		gas -= distance * (1 / efficiency);
	}

	public double getGasInTank() {
		System.out.println("Gas left in the Car: " + gas + " gallons");
		return gas;
	}

}
