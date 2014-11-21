package com.circla.pd.de02;

import java.util.Scanner;

import com.circla.pd.DEFunctions;

public class DE02 {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		DEFunctions deFunc = new DEFunctions();
		Scanner input = new Scanner(System.in);
		double classNo;
		double targetRes;
		double[][] topThree = new double[3][6];
		// resistor values reference
		int[] rBrowns = {100, 110, 120, 130, 150, 160, 180,
				1000, 1100, 1200, 1300, 1500, 1600, 1800,
				10000, 11000, 12000, 13000, 15000, 16000, 18000,
				100000, 110000, 120000, 130000, 150000, 160000, 180000};
		int[] rOranges = {300, 330, 360, 390,
				3000, 3300, 3600, 3900,
				30000, 33000, 36000, 39000,
				300000, 330000, 360000, 390000};
		int[] rGreens = {510, 560,
				5100, 5600,
				51000, 56000,
				510000, 560000};
		int[] rViolets = {750, 7500, 75000, 750000};
		int[] rWhites = {910, 9100, 91000, 910000};
		
		System.out.print("Please input your class number:\t");
		classNo = (double) input.nextInt() + 0.3;
		targetRes = (classNo + 1) * 10 * 1000;
		System.out.println("[Class Number]:\t" + classNo);
		System.out.println("[Target Resistance]:\t" + deFunc.displayToKOhms(targetRes));
		System.out.println("computing the top three values...");
		
		// computation START
		for (int brown : rBrowns) {
			for (int orange : rOranges) {
				for (int green : rGreens) {
					for (int violet : rViolets) {
						for (int white : rWhites) {
							int count = 0;
							double computedRes = 0;
							double computedDesErr = 0;
							// pd can only accept only 1 resistor that has a value less than 1
							if (brown < 1000) {
								count++;
							}
							if (orange < 1000) {
								count++;
							}
							if (green < 1000) {
								count++;
							}
							if (violet < 1000) {
								count++;
							}
							if (white < 1000) {
								count++;
							}
							
							if (count <= 1) {
								computedRes = brown + orange + green + violet + white;
								computedDesErr = deFunc.computeDesErr(targetRes, computedRes);
								
								if (topThree[0][1] <= 0) {
									// add automatically if there are no records yet
									add(0, topThree, computedDesErr, brown, orange, green, violet, white);
								}
								else if (topThree[1][1] <= 0) {
									add(1, topThree, computedDesErr, brown, orange, green, violet, white);
								}
								else if (topThree[2][1] <= 0) {
									add(2, topThree, computedDesErr, brown, orange, green, violet, white);
								}
								else if (computedDesErr < topThree[0][0]) {
									add(0, topThree, computedDesErr, brown, orange, green, violet, white);
								}
								else if (computedDesErr < topThree[1][0]) {
									add(1, topThree, computedDesErr, brown, orange, green, violet, white);
								}
								else if (computedDesErr < topThree[2][0]) {
									add(2, topThree, computedDesErr, brown, orange, green, violet, white);
								}
							}
						}
					}
				}
			}
		}
		// computation END
		
		printTopThree(topThree);
	}

	private static void add(int rowNumber, double[][] topThree, double computedDesErr,
			int brown, int orange, int green, int violet, int white) {
		for (int i = topThree.length - 1; i > rowNumber; i--) {
			topThree[i][0] = topThree[i-1][0];
			topThree[i][1] = topThree[i-1][1];
			topThree[i][2] = topThree[i-1][2];
			topThree[i][3] = topThree[i-1][3];
			topThree[i][4] = topThree[i-1][4];
			topThree[i][5] = topThree[i-1][5];
		}
		
		topThree[rowNumber][0] = computedDesErr;
		topThree[rowNumber][1] = brown;
		topThree[rowNumber][2] = orange;
		topThree[rowNumber][3] = green;
		topThree[rowNumber][4] = violet;
		topThree[rowNumber][5] = white;
	}

	private static void printTopThree(double[][] topThree) {
		DEFunctions deFunc = new DEFunctions();
		System.out.println("\n\n");
		for (int i = 0; i < topThree.length; i++) {
			System.out.println("=======================");
			System.out.println("--Top "+ (i + 1) +"--");
			double desErr = topThree[i][0];
			double brown = topThree[i][1];
			double orange = topThree[i][2];
			double green = topThree[i][3];
			double violet = topThree[i][4];
			double white = topThree[i][5];
			double totalResistance = brown + orange + green + violet + white;
			System.out.println("Total Resistance:\t" + (int) totalResistance + " " + deFunc.ohmSign());
			System.out.println("\tR-brown:\t" + (int) brown + " " + deFunc.ohmSign());
			System.out.println("\tR-orange:\t" + (int) orange + " " + deFunc.ohmSign());
			System.out.println("\tR-green:\t" + (int) green + " " + deFunc.ohmSign());
			System.out.println("\tR-violet:\t" + (int) violet + " " + deFunc.ohmSign());
			System.out.println("\tR-white:\t" + (int) white + " " + deFunc.ohmSign());
			System.out.println("DesErr: " + deFunc.to3SigFig(desErr) + "%");
		}
	}
}
