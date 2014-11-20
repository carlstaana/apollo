package com.circla.pd;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Scanner;

public class DEFunctions {
	private ArrayList<Double> E24 = new ArrayList<Double>();
	private double desErr;
	
	public DEFunctions() {
		loadE24();
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

	public int getCommercialValue(double value) {
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

	public double computeDesErr(double target, double computed) {
		desErr = (Math.abs(target - computed) / target) * 100;
		return getDesErr();
	}

	public double to3SigFig(double number) {
		BigDecimal bd = new BigDecimal(number);
		bd = bd.round(new MathContext(3));
		double rounded = bd.doubleValue();
		return rounded;
	}
	
	public double getDesErr() {
		return desErr;
	}

	public String ohmSign() {
		return "\u2126";
	}

	public double toKOhms(double ohmValue) {
		BigDecimal bd = new BigDecimal(ohmValue);
		bd = bd.divide(new BigDecimal(1000));
		return bd.doubleValue();
	}
}
