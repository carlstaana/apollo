package com.circla.pd;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.apollo.training.OSValidator;
import com.apollo.training.set6.loader.Price.Amount;

public class DEFunctions {
	private OSValidator os = new OSValidator();
	
	private ArrayList<BigDecimal> E24 = new ArrayList<BigDecimal>();
	private ArrayList<Double> capacitors = new ArrayList<Double>();
	
	private double desErr;
	
	
	public DEFunctions() {
		loadE24();
		loadCapacitors();
	}
	
	
	private void loadE24() {
		BigDecimal factors[] = new BigDecimal[24];
		factors[0] = new BigDecimal(1.0);
		factors[1] = new BigDecimal(1.1);
		factors[2] = new BigDecimal(1.2);
		factors[3] = new BigDecimal(1.3);
		factors[4] = new BigDecimal(1.5);
		factors[5] = new BigDecimal(1.6);
		factors[6] = new BigDecimal(1.8);
		factors[7] = new BigDecimal(2.0);
		factors[8] = new BigDecimal(2.2);
		factors[9] = new BigDecimal(2.4);
		factors[10] = new BigDecimal(2.7);
		factors[11] = new BigDecimal(3.0);
		factors[12] = new BigDecimal(3.3);
		factors[13] = new BigDecimal(3.6);
		factors[14] = new BigDecimal(3.9);
		factors[15] = new BigDecimal(4.3);
		factors[16] = new BigDecimal(4.7);
		factors[17] = new BigDecimal(5.1);
		factors[18] = new BigDecimal(5.6);
		factors[19] = new BigDecimal(6.2);
		factors[20] = new BigDecimal(6.8);
		factors[21] = new BigDecimal(7.5);
		factors[22] = new BigDecimal(8.2);
		factors[23] = new BigDecimal(9.1);
			//{1.0, 1.1, 1.2, 1.3, 1.5, 1.6, 1.8, 2.0, 2.2, 2.4, 2.7, 3.0, 3.3, 3.6, 3.9, 4.3, 4.7, 5.1, 5.6, 6.2, 6.8, 7.5, 8.2, 9.1};
		
		for (int i = 0; i < factors.length; i++) {
			E24.add(factors[i]);
			E24.add(factors[i].multiply(new BigDecimal(10)));
			E24.add(factors[i].multiply(new BigDecimal(100)));
			E24.add(factors[i].multiply(new BigDecimal(1000)));
			E24.add(factors[i].multiply(new BigDecimal(10000)));
			E24.add(factors[i].multiply(new BigDecimal(100000)));
		}
		
		
		for (int i = 0; i < E24.size(); i++) {
			BigDecimal r = E24.get(i).round(MathContext.DECIMAL32);
			E24.set(i, r);
			// System.out.println(E24.get(i));
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
			double selectedResistor = E24.get(i).doubleValue();
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
		
		if (anotherCommValue > 0) {
			Scanner input = new Scanner(System.in);
			System.out.println("Commercial Value #2 = " + anotherCommValue + " " + ohmSign());
			System.out.print("Do you want to use " + anotherCommValue + " " + ohmSign() + " than " + bestCommValue + " " + ohmSign() + "? [y/n]\t");
			String decision = input.nextLine();
			if (decision.equalsIgnoreCase("y") || decision.equalsIgnoreCase("yes")) {
				bestCommValue = anotherCommValue;
			}
			input.close();
		}
		return bestCommValue;
	}
	
	/**
	 * Gets the closest two(2) E24 resistors for the sum of your input
	 * @param value is the computed resistance (in Ohms)
	 * @return array that contains 2 commercial resistors
	 */
	public double[] getCommercialValue2R(double value) {
		double bestTolerance = 1000;
		double commR1 = 0;
		double commR2 = 0;
		double anotherCommR1 = 0;
		double anotherCommR2 = 0;
		
		for (BigDecimal r1 : E24) {
			for (BigDecimal r2 : E24) {
				double totalResistance = r1.add(r2).doubleValue();
				double computedTolerance = computeDesErr(value, totalResistance);
				if (computedTolerance < bestTolerance) {
					commR1 = r1.doubleValue();
					commR2 = r2.doubleValue();
					bestTolerance = computedTolerance;
					anotherCommR1 = 0;
					anotherCommR2 = 0;
				}
				else if (computedTolerance == bestTolerance &&
						commR1 != r1.doubleValue() && commR2 != r2.doubleValue() &&
						commR1 != r2.doubleValue() && commR2 != r1.doubleValue()) {
					anotherCommR1 = r1.doubleValue();
					anotherCommR2 = r2.doubleValue();
				}
			}
		}
		
		if (anotherCommR1 > 0 && anotherCommR2 > 0) {
			Scanner input = new Scanner(System.in);
			System.out.println("Commercial Values #2 = " + anotherCommR1 + " " + ohmSign() + " " + anotherCommR2 + " " + ohmSign());
			System.out.print("Do you want to use " + anotherCommR1 + " " + ohmSign() + " and " + " " + anotherCommR2 + " " + ohmSign() + " than " + commR1 + " " + ohmSign() + " " + commR2 + " " + ohmSign() + "? [y/n]\t");
			String decision = input.nextLine();
			if (decision.equalsIgnoreCase("y") || decision.equalsIgnoreCase("yes")) {
				commR1 = anotherCommR1;
				commR2 = anotherCommR2;
			}
			input.close();
		}
		
		double[] finalResistors = {commR1, commR2};
		return finalResistors;
	}
	
	/**
	 * Gets the closest three(3) E24 resistors for the sum of your input
	 * @param value is the computed resistance (in Ohms)
	 * @return array that contains 3 commercial resistors
	 */
	public double[] getCommercialValue3R(double value) {
		double bestTolerance = 1000;
		int commR1 = 0;
		int commR2 = 0;
		int commR3 = 0;
		int anotherCommR1 = 0;
		int anotherCommR2 = 0;
		int anotherCommR3 = 0;
		
		for (BigDecimal r1 : E24) {
			for (BigDecimal r2 : E24) {
				for (BigDecimal r3 : E24) {
					double totalResistance = r1.add(r2).add(r3).doubleValue();
					double computedTolerance = computeDesErr(value, totalResistance);
					if (computedTolerance < bestTolerance) {
						commR1 = r1.intValue();
						commR2 = r2.intValue();
						commR3 = r3.intValue();
						bestTolerance = computedTolerance;
						anotherCommR1 = 0;
						anotherCommR2 = 0;
						anotherCommR3 = 0;
					}
					else if (computedTolerance == bestTolerance &&
							commR1 != r1.intValue() && commR2 != r2.intValue() &&
							commR1 != r2.intValue() && commR2 != r1.intValue() &&
							commR1 != r3.intValue() && commR3 != r1.intValue() &&
							commR2 != r3.intValue() && commR3 != r2.intValue()) {
						anotherCommR1 = r1.intValue();
						anotherCommR2 = r2.intValue();
						anotherCommR3 = r3.intValue();
					}
				}
			}
		}
		
		if (anotherCommR1 > 0 && anotherCommR2 > 0 && anotherCommR3 > 0) {
			Scanner input = new Scanner(System.in);
			System.out.println("Commercial Values #2 = " + anotherCommR1 + " " + ohmSign() + " + " + anotherCommR2 + " " + ohmSign() + " + " + anotherCommR3 + " " + ohmSign());
			System.out.print("Do you want to use " + anotherCommR1 + " " + ohmSign() + ", " + " " + anotherCommR2 + " " + ohmSign() + ", " + anotherCommR3 + " " + ohmSign() + " than " + commR1 + " " + ohmSign() + ", " + commR2 + " " + ohmSign() + ", " + commR3 + " " + ohmSign() + "? [y/n]\t");
			String decision = input.nextLine();
			if (decision.equalsIgnoreCase("y") || decision.equalsIgnoreCase("yes")) {
				commR1 = anotherCommR1;
				commR2 = anotherCommR2;
				commR3 = anotherCommR3;
			}
			input.close();
		}
		
		double[] finalResistors = {commR1, commR2, commR3};
		return finalResistors;
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
			input.close();
		}
		
		return bestCommValue;
	}

	public double getDesErr() {
		return desErr;
	}
	
	public ArrayList<BigDecimal> getE24() {
		return E24;
	}


	public double[][] getThreePossiblePairs(double totalR1) {
		double[][] resistors = new double[3][2];
		
		while (resistors[0][0] == 0 && resistors[1][0] == 0 && resistors[2][0] == 0) {
			double compTolerance = 0;
			double bestTolerance = 1000;
			double commR1 = 0;
			double commR2 = 0;
			
			for (BigDecimal r1 : E24) {
				for (BigDecimal r2 : E24) {
					double totalResistance = r1.doubleValue() + r2.doubleValue();
					compTolerance = computeDesErr(totalR1, totalResistance);
					if (compTolerance < bestTolerance) {
						if (resistors[0][0] != 0) {
							if (resistors[0][0] != r1.doubleValue() && resistors[0][1] != r2.doubleValue() &&
									resistors[0][0] != r2.doubleValue() && resistors[0][1] != r1.doubleValue()) {
								bestTolerance = compTolerance;
								commR1 = r1.doubleValue();
								commR2 = r2.doubleValue();
							}
						}
						else if (resistors[1][0] != 0) {
							if (resistors[1][0] != r1.doubleValue() && resistors[1][1] != r2.doubleValue() &&
									resistors[1][0] != r2.doubleValue() && resistors[1][1] != r1.doubleValue()) {
								bestTolerance = compTolerance;
								commR1 = r1.doubleValue();
								commR2 = r2.doubleValue();
							}
						}
						else if (resistors[2][0] != 0) {
							if (resistors[2][0] != r1.doubleValue() && resistors[2][1] != r2.doubleValue() &&
									resistors[2][0] != r2.doubleValue() && resistors[2][1] != r1.doubleValue()) {
								bestTolerance = compTolerance;
								commR1 = r1.doubleValue();
								commR2 = r2.doubleValue();
							}
						}
					}
				}
			}
			
			if (resistors[0][0] == 0) {
				resistors[0][0] = commR1;
				resistors[0][1] = commR2;
			}
			else if (resistors[1][0] == 0) {
				resistors[1][0] = commR1;
				resistors[1][1] = commR2;
			}
			else if (resistors[2][0] == 0) {
				resistors[2][0] = commR1;
				resistors[2][1] = commR2;
			}
		}
		
		return resistors;
	}
}
