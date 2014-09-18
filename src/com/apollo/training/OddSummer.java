package com.apollo.training;

public class OddSummer {

	public long getSumOfOdds(int i, int j) {
		long sum = 0;
		
		System.out.println("Odd Numbers:");
		for (int k = i; k <= j; k++) {
			if (k%2 > 0) {
				System.out.println(k);
				sum += k;
				
			}
		}
		System.out.println("sum = " + sum);
		
		return sum;
	}

}
