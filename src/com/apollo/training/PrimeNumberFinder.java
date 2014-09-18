package com.apollo.training;

public class PrimeNumberFinder {

	public void findPrimes(int max) {
		System.out.println("Prime Numbers:");
		for (int i = 0; i <= max; i++) {
			for (int j = 0; j <= max; j++) {
				if (i >= 2 && j >= 2) {
					if (i > j && i%j == 0) {
						break;
					} else if (i == j) {
						System.out.println(i);
						break;
					} 
				}
			}
		}
	}

}
