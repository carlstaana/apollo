package com.apollo.training.set3.calc1;

public interface Calc1 {
	/**
	 * interface methods
	 */
	void updateDisplay();
	double getDisplay(double num);
	double add(double currentTotal, double num);
	double subtract(double currentTotal, double num);
	double multiply(double currentTotal, double num);
	double divide(double currentTotal, double num);
	double sqRoot(double currentTotal);
	double percent(double currentTotal);
	double fraction(double currentTotal);
}
