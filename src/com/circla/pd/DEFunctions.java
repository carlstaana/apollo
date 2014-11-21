package com.circla.pd;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Scanner;

import com.apollo.training.OSValidator;

public class DEFunctions {
	private OSValidator os = new OSValidator();
	
	private ArrayList<Double> E24 = new ArrayList<Double>();
	private ArrayList<Double> capacitors = new ArrayList<Double>();
	
	private double desErr;
	
	
	public DEFunctions() {
		loadE24();
		loadCapacitors();
	}
	
	
	private void loadE24() {
		double factors[] = {1.0, 1.1, 1.2, 1.3, 1.5, 1.6, 1.8, 2.0, 2.2, 2.4, 2.7, 3.0, 3.3, 3.6, 3.9, 4.3, 4.7, 5.1, 5.6, 6.2, 6.8, 7.5, 8.2, 9.1};
		
		for (int i = 0; i < factors.length; i++) {
			E24.add(factors[i]);
			E24.add(factors[i] * 10);
			E24.add(factors[i] * 100);
			E24.add(factors[i] * 1000);
			E24.add(factors[i] * 10000);
			E24.add(factors[i] * 100000);
			E24.add(factors[i] * 1000000);
		}
	}

	private void loadCapacitors() {
		double factors[] = {0.00001, 0.000015, 0.000022, 0.000033, 0.000047, 0.000068};
		
		for (int i = 0; i < factors.length; i++) {
			capacitors.add(factors[i]);
			capacitors.add(factors[i] * 10);
			capacitors.add(factors[i] * 100);
			capacitors.add(factors[i] * 1000);
			capacitors.add(factors[i] * 10000);
			capacitors.add(factors[i] * 100000);
			capacitors.add(factors[i] * 1000000);
			capacitors.add(factors[i] * 10000000);
			capacitors.add(factors[i] * 100000000);
			capacitors.add(factors[i] * 1000000000);
		}
	}

	public double computeDesErr(double target, double measured) {
		desErr = (Math.abs(target - measured) / target) * 100;
		return getDesErr();
	}

	public double to3SigFig(double number) {
		BigDecimal bd = new BigDecimal(number);
		bd = bd.round(new MathContext(3));
		double rounded = bd.doubleValue();
		return rounded;
	}
	
	public String ohmSign() {
		if (os.isUnix()) {
			return "\u2126";
		}
		else {
			return "ohms";
		}
	}

	public String microfaradSign() {
		if (os.isUnix()) {
			return "\u00B5" + "F";
		} else {
			return "uF";
		}
	}
	
	public String displayToKOhms(double ohmValue) {
		BigDecimal bd = new BigDecimal(ohmValue);
		bd = bd.divide(new BigDecimal(1000));
		return bd.doubleValue() + " k" + ohmSign();
	}

	public void displayMicroToPico(double microfarad) {
		BigDecimal bd = new BigDecimal(microfarad);
		bd = bd.multiply(new BigDecimal(1000000));
		System.out.println(bd.doubleValue() + " pF");
	}

	/**
	 * Converts microfarad to farad (for computation purposes)
	 * @param microfarad is the value to be converted
	 * @return capacitance in farad
	 */
	public double microfaradToBaseValue(double microfarad) {
		BigDecimal bd = new BigDecimal(microfarad);
		bd = bd.divide(new BigDecimal(100000));
		return bd.doubleValue();
	}

	/**
	 * Gets the closest E24 commercial resistor value of your input
	 * @param value is the computed resistance (in Ohms)
	 * @return E24 resistor value
	 */
	public int getCommercialValueR(double value) {
		int bestCommValue = 0;
		double bestTolerance = 1000;
		int anotherCommValue = 0;
		
		for (int i = 0; i < E24.size(); i++) {
			double selectedResistor = E24.get(i);
			double computedTolerance = computeDesErr(value, selectedResistor);
			if (computedTolerance <  bestTolerance) {
				bestCommValue = (int) selectedResistor;
				bestTolerance = computedTolerance;
				anotherCommValue = 0;
			}
			else if (computedTolerance == bestTolerance && selectedResistor != bestCommValue) {
				anotherCommValue = (int) selectedResistor;
			}
		}
		
		System.out.println("Commercial Value = " + bestCommValue + " " + ohmSign());
		if (anotherCommValue > 0) {
			Scanner input = new Scanner(System.in);
			System.out.println("Commercial Value #2 = " + anotherCommValue + " " + ohmSign());
			System.out.print("Do you want to use " + anotherCommValue + " " + ohmSign() + " than " + bestCommValue + " " + ohmSign() + "? [y/n]\t");
			String decision = input.nextLine();
			if (decision.equalsIgnoreCase("y") || decision.equalsIgnoreCase("yes")) {
				bestCommValue = anotherCommValue;
			}
		}
		
		return bestCommValue;
	}
	
	/**
	 * Gets the closest and most common capacitor value of your input
	 * @param value is the computed capacitance (in microfarads)
	 * @return commercial capacitor value
	 */
	public double getCommercialValueC(double value) {
		double bestCommValue = 0;
		double bestTolerance = 1000;
		double anotherCommValue = 0;
		
		for (int i = 0; i < capacitors.size(); i++) {
			double selCap = capacitors.get(i);
			double computedTolerance = computeDesErr(value, selCap);
			if (computedTolerance < bestTolerance) {
				bestCommValue = selCap;
				bestTolerance = computedTolerance;
				anotherCommValue = 0;
			}
			else if (computedTolerance == bestTolerance && selCap != bestCommValue) {
				anotherCommValue = selCap;
			}
		}
		
		System.out.println("Commercial Value = " + bestCommValue + " " + microfaradSign());
		if (anotherCommValue > 0) {
			Scanner input = new Scanner(System.in);
			System.out.println("Commercial Value #2 = " + anotherCommValue + " " + microfaradSign());
			System.out.print("Do you want to use " + anotherCommValue + " " + microfaradSign() + " than " + bestCommValue + " " + microfaradSign() + "? [y/n]\t");
			String decision = input.nextLine();
			if (decision.equalsIgnoreCase("y") || decision.equalsIgnoreCase("yes")) {
				bestCommValue = anotherCommValue;
			}
		}
		
		return bestCommValue;
	}

	public double getDesErr() {
		return desErr;
	}
	
	public ArrayList<Double> getE24() {
		return E24;
	}
}
