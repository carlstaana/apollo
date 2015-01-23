package com.apollo.fmfi;

import java.util.ArrayList;

public class Loan {
	private ArrayList<Guarantor> guarantors = new ArrayList<Guarantor>();
	
	private Double amount;
	
	private Double guaranteePointsNeeded;
	
	private Double totalGuaranteePoints;
	
	public Loan() {
		// TODO Auto-generated constructor stub
	}

	public Loan(Double amount) {
		this.amount = amount;
		this.guaranteePointsNeeded = amount * 0.20;
	}

	public ArrayList<Guarantor> getGuarantors() {
		return guarantors;
	}

	public Double getAmount() {
		return amount;
	}

	public void guarantee(Guarantor guarantor) {
		guarantors.add(guarantor);
		computeTotalGuaranteePoints();
		
		while (totalGuaranteePoints > guaranteePointsNeeded) {
			ArrayList<Integer> indexOfHighestBidders = getHighestBidders();
			for (Integer i : indexOfHighestBidders) {
				int gpoints = guarantors.get(i).getPointsGuaranteed() - 1;
				int rpoints = guarantors.get(i).getPointsReturned() + 1;
				
				guarantors.get(i).setPointsGuaranteed(gpoints);
				guarantors.get(i).setPointsReturned(rpoints);
				
				computeTotalGuaranteePoints();
				if (totalGuaranteePoints == guaranteePointsNeeded) {
					break;
				}
			}
		}
	}

	private ArrayList<Integer> getHighestBidders() {
		int highestBid = 0;
		
		for (Guarantor guarantor : guarantors) {
			int guarantorBid = guarantor.getPointsGuaranteed();
			if (guarantorBid > highestBid) {
				highestBid = guarantorBid;
			}
		}
		
		ArrayList<Integer> output = new ArrayList<Integer>();
		
		for (int i = 0; i < guarantors.size(); i++) {
			if (guarantors.get(i).getPointsGuaranteed() == highestBid) {
				output.add(i);
			}
		}
		
		return output;
	}

	private void computeTotalGuaranteePoints() {
		this.totalGuaranteePoints = 0.0;
		for (Guarantor guarantor : guarantors) {
			this.totalGuaranteePoints += guarantor.getPointsGuaranteed();
		}
	}
	
	

}
