package com.circla.pd.de06;

import java.util.Arrays;

public class VariablesDE06 {
	private double maxDesErr;
	
	private double desErrCurrentTotal;
	
	private double desErrCurrent1;
	
	private double desErrCurrent2;
	
	private double desErrCurrent3;
	
	private double totalCurrent;
	
	private double current1;
	
	private double current2;
	
	private double current3;
	
	private Double r1;
	
	private Double r2;
	
	private Double r3;
	
	public VariablesDE06() {
		// TODO Auto-generated constructor stub
	}

	public VariablesDE06(double desErrTotalCurrent, double desErrCurrent1,
			double desErrCurrent2, double desErrCurrent3,
			double compTotalCurrent, double compCurrent1, double compCurrent2,
			double compCurrent3, Double r1, Double r2, Double r3) {
		this.desErrCurrentTotal = desErrTotalCurrent;
		this.desErrCurrent1 = desErrCurrent1;
		this.desErrCurrent2 = desErrCurrent2;
		this.desErrCurrent3 = desErrCurrent3;
		this.totalCurrent = compTotalCurrent;
		this.current1 = compCurrent1;
		this.current2 = compCurrent2;
		this.current3 = compCurrent3;
		this.r1 = r1;
		this.r2 = r2;
		this.r3 = r3;
		computeMaxDesErr();
	}

	private void computeMaxDesErr() {
		double desErrArray[] = {desErrCurrentTotal, desErrCurrent1, desErrCurrent2, desErrCurrent3};
		
		Arrays.sort(desErrArray);
		this.maxDesErr = desErrArray[3];
	}

	public double getMaxDesErr() {
		return maxDesErr;
	}

	public double getDesErrCurrentTotal() {
		return desErrCurrentTotal;
	}

	public double getDesErrCurrent1() {
		return desErrCurrent1;
	}

	public double getDesErrCurrent2() {
		return desErrCurrent2;
	}

	public double getDesErrCurrent3() {
		return desErrCurrent3;
	}

	public double getTotalCurrent() {
		return totalCurrent;
	}

	public double getCurrent1() {
		return current1;
	}

	public double getCurrent2() {
		return current2;
	}

	public double getCurrent3() {
		return current3;
	}

	public Double getR1() {
		return r1;
	}

	public Double getR2() {
		return r2;
	}

	public Double getR3() {
		return r3;
	}

}
