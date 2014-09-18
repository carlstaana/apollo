package com.apollo.training.finals;

public class PythagoreanTriplet {

	public boolean check(int i) {
		int a = 0;
		int b = 0;
		int c = 0;
		boolean output = false;
		
		for (a = 0; a < i; a++) {
			c = i;
			c = i - a;
			for (b = 0; b < i; b++) {
				if (checkTriplet(a, b, c)) {
					output = true;
					break;		
				}
				c--;
			}
			
			if (checkTriplet(a, b, c)) {
				output = true;
				System.out.println("Triplet found: A=" + a + " B=" + b + " C=" + c);
				break;
			}
		}
		
		return output;
	}

	private boolean checkTriplet(int a, int b, int c) {
		if (((a*a) + (b*b)) == (c*c) && a > 0 && b > 0 && c > 0) {
			return true;
		} else {
			return false;
		}
	}
}
