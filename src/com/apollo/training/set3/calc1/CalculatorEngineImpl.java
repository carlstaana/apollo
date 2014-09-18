package com.apollo.training.set3.calc1;


public class CalculatorEngineImpl implements Calc1 {
	public double currentTotal = 0;
	public double currentDisplay = 0;
	
	@Override
	public void updateDisplay() {
		currentDisplay = currentTotal;
		System.out.println("Display: " + currentDisplay);
	}

	@Override
	public double getDisplay(double num) {
		return num;
	}

	/**
	 * 1. get the display. @param currentDisplay
	 * 2. pass the CURRENT TOTAL and the RETRIEVED VALUE to the operator method @param: currentTotal, currentDisplay
	 * 3. produce new total from the method (return value) and update the display (must display the return value)
	 */
	
	@Override
	public double add(double currentTotal, double num) {
		this.currentTotal = currentTotal + num;
		updateDisplay();
		return this.currentTotal;
	}

	@Override
	public double subtract(double currentTotal, double num) {
		this.currentTotal = currentTotal - num;
		updateDisplay();
		return this.currentTotal;
	}

	@Override
	public double multiply(double currentTotal, double num) {
		this.currentTotal = currentTotal * num;
		updateDisplay();
		return this.currentTotal;
	}

	@Override
	public double divide(double currentTotal, double num) {
		this.currentTotal = currentTotal / num;
		updateDisplay();
		return this.currentTotal;
	}

	@Override
	public double sqRoot(double currentTotal) {
		this.currentTotal = Math.sqrt(currentTotal);
		updateDisplay();
		return this.currentTotal;
	}

	@Override
	public double percent(double currentTotal) {
		this.currentTotal = currentTotal / 100;
		updateDisplay();
		return this.currentTotal;
	}

	@Override
	public double fraction(double currentTotal) {
		this.currentTotal = 1 / currentTotal;
		updateDisplay();
		return this.currentTotal;
	}


}
