package com.circla.pd.de06;

import java.math.BigDecimal;
import java.util.Scanner;

import com.apollo.training.book.chapter1.NamePrinter;
import com.circla.pd.DEFunctions;

public class DE06 {

	public static void main(String[] args) {
		DEFunctions defunc = new DEFunctions();
		NamePrinter np = new NamePrinter();
		double classNo;
		
		// voltage source
		final int VCC = 12;
		
		// target values
		double totalCurrent;	// in mA
		double r1Current, r2Current, r3Current;	// in mA
		// top three output
		VariablesDE06[] topThree = new VariablesDE06[3];
		// %maxDesErr, %error(iT), %error(i1), %error(i2), %error(i3), iT, i1, i2, i3, R1, R2, R3
		
		// other variables
		Scanner input = new Scanner(System.in);
		
		
		// main program -- START
		System.out.print("Please input your class number (e.g: 15.3):\t");
		classNo = input.nextDouble();
		// solve for the target values
		totalCurrent = 20 + classNo;
		r1Current = ((5+classNo)/100) * totalCurrent;
		r2Current = ((10+classNo)/100) * totalCurrent;
		r3Current = ((85-(2*classNo))/100) * totalCurrent;
		System.out.println("[Class No]: " + classNo);
		System.out.println("[Target Values]:");
		System.out.println("   Total Current: " + totalCurrent + " mA");
		System.out.println("   Current 1: " + r1Current + " mA");
		System.out.println("   Current 2: " + r2Current + " mA");
		System.out.println("   Current 3: " + r3Current + " mA");
		System.out.print("\nSelect Version\n"
				+ "[1] Direct Solution\n"
				+ "[2] Top 3 Design Errors\n"
				+ "[3] 2-Resistor Combination\n"
				+ "[4] 3-Resistor Combination\n"
				+ "Answer:\t");
		int version = input.nextInt();
		
		if (version == 1) {
			// computation -- START
			System.out.println("Computing...");
			double r1 = VCC/(r1Current/1000);
			double r2 = VCC/(r2Current/1000);
			double r3 = VCC/(r3Current/1000);
			
			double commR1 = defunc.getCommercialValueR(r1);
			double commR2 = defunc.getCommercialValueR(r2);
			double commR3 = defunc.getCommercialValueR(r3);
			
			double compTotalResistance = 1/((1/commR1)+(1/commR2)+(1/commR3));
			double compTotalCurrent = (VCC/compTotalResistance) * 1000;
			double compCurrent1 = (VCC/commR1) * 1000;
			double compCurrent2 = (VCC/commR2) * 1000;
			double compCurrent3 = (VCC/commR3) * 1000;
			
			double desErrTotalCurrent = defunc.computeDesErr(totalCurrent, compTotalCurrent);
			double desErrCurrent1 = defunc.computeDesErr(r1Current, compCurrent1);
			double desErrCurrent2 = defunc.computeDesErr(r2Current, compCurrent2);
			double desErrCurrent3 = defunc.computeDesErr(r3Current, compCurrent3);
			
			VariablesDE06 compVariables = new VariablesDE06(desErrTotalCurrent, desErrCurrent1, desErrCurrent2, desErrCurrent3, compTotalCurrent, compCurrent1, compCurrent2, compCurrent3, commR1, commR2, commR3);
			System.out.println("\n\n");
			np.print("RESULT");
			System.out.println("--computed values--");
			System.out.println("R1: " + r1 + " " + defunc.ohmSign());
			System.out.println("R2: " + r2 + " " + defunc.ohmSign());
			System.out.println("R3: " + r3 + " " + defunc.ohmSign());
			System.out.println("\n--commercial values--");
			System.out.println("R1: " + (int)commR1 + " " + defunc.ohmSign());
			System.out.println("R2: " + (int)commR2 + " " + defunc.ohmSign());
			System.out.println("R3: " + (int)commR3 + " " + defunc.ohmSign());
			System.out.println("\n--computed currents--");
			System.out.println("iT: " + compTotalCurrent + " mA");
			System.out.println("i1: " + compCurrent1 + " mA");
			System.out.println("i2: " + compCurrent2 + " mA");
			System.out.println("i3: " + compCurrent3 + " mA");
			System.out.println("\n--design errors--");
			System.out.println("desErr(iT): " + defunc.to3SigFig(desErrTotalCurrent) + " %");
			System.out.println("desErr(i1): " + defunc.to3SigFig(desErrCurrent1) + " %");
			System.out.println("desErr(i2): " + defunc.to3SigFig(desErrCurrent2) + " %");
			System.out.println("desErr(i3): " + defunc.to3SigFig(desErrCurrent3) + " %");
		}
		else if (version == 2) {
			// computation -- START
			// get values to trial and error total resistance
			System.out.println("Computing...");
			for (BigDecimal r1 : defunc.getE24()) {
				for (BigDecimal r2 : defunc.getE24()) {
					for (BigDecimal r3 : defunc.getE24()) {
						// temporary variables for resistors
						double compTotalResistance;
						double compTotalCurrent;
						double desErrTotalCurrent;
						double compCurrent1, compCurrent2, compCurrent3;
						double desErrCurrent1, desErrCurrent2, desErrCurrent3;
						compTotalResistance = 1/((1/r1.doubleValue())+(1/r2.doubleValue())+(1/r3.doubleValue()));
						compTotalCurrent = (VCC/compTotalResistance) * 1000;
						compCurrent1 = (VCC/r1.doubleValue()) * 1000;
						compCurrent2 = (VCC/r2.doubleValue()) * 1000;
						compCurrent3 = (VCC/r3.doubleValue()) * 1000;
						desErrTotalCurrent = defunc.computeDesErr(totalCurrent, compTotalCurrent);
						desErrCurrent1 = defunc.computeDesErr(r1Current, compCurrent1);
						desErrCurrent2 = defunc.computeDesErr(r2Current, compCurrent2);
						desErrCurrent3 = defunc.computeDesErr(r3Current, compCurrent3);
						
						VariablesDE06 compVariables = new VariablesDE06(desErrTotalCurrent, desErrCurrent1, desErrCurrent2, desErrCurrent3, compTotalCurrent, compCurrent1, compCurrent2, compCurrent3, r1.doubleValue(), r2.doubleValue(), r3.doubleValue());
						
						// adding to topThree
						if (topThree[0] == null) {
							add(0, topThree, compVariables);
						}
						else if (topThree[1] == null) {
							add(1, topThree, compVariables);
						}
						else if (topThree[2] == null) {
							add(2, topThree, compVariables);
						} 
						else if (compVariables.getMaxDesErr() < topThree[0].getMaxDesErr()) {
							add(0, topThree, compVariables);
						}
						else if (compVariables.getMaxDesErr() < topThree[1].getMaxDesErr()) {
							add(1, topThree, compVariables);
						}
						else if (compVariables.getMaxDesErr() < topThree[2].getMaxDesErr()) {
							add(2, topThree, compVariables);
						}
					}
				}
			}
			
			System.out.println("\n");
			np.print("RESULT");
			for (int i = 0; i < topThree.length; i++) {
				System.out.println("-----------------\n");
				System.out.println("#" + (i+1) + ": " + defunc.to3SigFig(topThree[i].getMaxDesErr()) + "%");
				System.out.println("--Resistors--");
				System.out.println("   R1: " + topThree[i].getR1().intValue() + " " + defunc.ohmSign());
				System.out.println("   R2: " + topThree[i].getR2().intValue() + " " + defunc.ohmSign());
				System.out.println("   R3: " + topThree[i].getR3().intValue() + " " + defunc.ohmSign());
				System.out.println("--Computed Currents--");
				System.out.println("   iT: " + defunc.to3SigFig(topThree[i].getTotalCurrent()) + " mA");
				System.out.println("   i1: " + defunc.to3SigFig(topThree[i].getCurrent1()) + " mA");
				System.out.println("   i2: " + defunc.to3SigFig(topThree[i].getCurrent2()) + " mA");
				System.out.println("   i3: " + defunc.to3SigFig(topThree[i].getCurrent3()) + " mA");
				System.out.println("--Design Errors--");
				System.out.println("   DesErr (iT): " + defunc.to3SigFig(topThree[i].getDesErrCurrentTotal()) + " %");
				System.out.println("   DesErr (i1): " + defunc.to3SigFig(topThree[i].getDesErrCurrent1()) + " %");
				System.out.println("   DesErr (i2): " + defunc.to3SigFig(topThree[i].getDesErrCurrent2()) + " %");
				System.out.println("   DesErr (i3): " + defunc.to3SigFig(topThree[i].getDesErrCurrent3()) + " %");
				System.out.println();
			}
		}
		else if (version == 3) {
			// computation -- START
			// get possible 6 resistors
			System.out.println("Computing...");
			final double totalR1 = VCC/(r1Current/1000);
			final double totalR2 = VCC/(r2Current/1000);
			final double totalR3 = VCC/(r3Current/1000);
			
			double[][] threeCommR1 = defunc.getThreePossiblePairs(totalR1);
			double[][] threeCommR2 = defunc.getThreePossiblePairs(totalR2);
			double[][] threeCommR3 = defunc.getThreePossiblePairs(totalR3);
			
			for (int i = 0; i < 3; i++) {
				double r11 = threeCommR1[i][0];
				double r12 = threeCommR1[i][1];
				double rt1 = r11 + r12;
				double r21 = threeCommR2[i][0];
				double r22 = threeCommR2[i][1];
				double rt2 = r21 + r22;
				double r31 = threeCommR3[i][0];
				double r32 = threeCommR3[i][1];
				double rt3 = r31 + r32;
				
				double compTotalResistance = 1/((1/rt1)+(1/rt2)+(1/rt3));
				double compTotalCurrent = (VCC/compTotalResistance) * 1000;
				double compCurrent1 = (VCC/rt1) * 1000;
				double compCurrent2 = (VCC/rt2) * 1000;
				double compCurrent3 = (VCC/rt3) * 1000;
				
				double desErrTotalCurrent = defunc.computeDesErr(totalCurrent, compTotalCurrent);
				double desErrCurrent1 = defunc.computeDesErr(r1Current, compCurrent1);
				double desErrCurrent2 = defunc.computeDesErr(r2Current, compCurrent2);
				double desErrCurrent3 = defunc.computeDesErr(r3Current, compCurrent3);
				VariablesDE06 compVariables = new VariablesDE06(desErrTotalCurrent, desErrCurrent1, desErrCurrent2, desErrCurrent3, compTotalCurrent, compCurrent1, compCurrent2, compCurrent3, r11, r12, r21, r22, r31, r32);
				compVariables.toString2();
			}
		}
	}

	private static void add(int rowNumber, VariablesDE06[] topThree,
			VariablesDE06 compVariables) {
		for (int i = topThree.length - 1; i > rowNumber; i--) {
			topThree[i] = topThree[i-1];
		}
		
		topThree[rowNumber] = compVariables;
	}
}
