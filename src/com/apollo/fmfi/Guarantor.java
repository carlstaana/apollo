package com.apollo.fmfi;

public class Guarantor {
	int pointsGuaranteed = 0;
	
	int pointsReturned = 0;
	
	public Guarantor() {
		// TODO Auto-generated constructor stub
	}

	public Guarantor(int pointsGuaranteed) {
		this.pointsGuaranteed = pointsGuaranteed;
	}

	public void setPointsGuaranteed(int pointsGuaranteed) {
		this.pointsGuaranteed = pointsGuaranteed;
	}

	public void setPointsReturned(int pointsReturned) {
		this.pointsReturned = pointsReturned;
	}

	public int getPointsGuaranteed() {
		return pointsGuaranteed;
	}

	public int getPointsReturned() {
		return pointsReturned;
	}
	
	
}
