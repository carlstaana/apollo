package com.apollo.training.set2;

public class MagicSquare {

	public int[][] generate(int dim) {
		// check if dimension is odd
		if (dim % 2 == 0 && dim == 1) {
			return null;
		}

		int area = dim * dim;
		int[][] magic = new int[dim][dim];
		int max = dim - 1;
		int x = max, y = max / 2, k = 1;
		// place number 1
		magic[x][y] = k;
		k++;
		while (k <= area) { // loop until total area
			if (x == max && y < max) { // if row reached maximum but column
										// still hasn't
				// move to the top and right
				x = 0;
				y++;
				magic[x][y] = k;
				k++;
			} else if (y == max && x < max) { // if column reached maximum but
												// row still hasn't
				// move down then left
				y = 0;
				x++;
				magic[x][y] = k;
				k++;
			} else if ((y < max && x < max && magic[x + 1][y + 1] > 0)
					|| (y == max && x == max)) { // if column reached the next
													// point wherein it was
													// already filled
				x--;
				magic[x][y] = k;
				k++;
			} else {
				x++;
				y++;
				magic[x][y] = k;
				k++;
			}
		}
		for (int i = 0; i <= max; i++) {
			for (int j = 0; j <= max; j++) {
				System.out.print(magic[i][j] + "\t");
			}
			System.out.println();
		}

		return magic;
	}

	public boolean check(int[][] expected) {
		int targetTotal = 0; 
		int trialTotal = 0;
		
		for (int j = 0; j < expected.length; j++) {
			targetTotal += expected[0][j];
		}
		
		// check arrays horizontally, starting at the second row
		for (int i = 1; i < expected.length; i++) {
			for (int j = 0; j < expected.length; j++) {
				trialTotal += expected[i][j];
			}
			// check if equal
			if (trialTotal != targetTotal) {
				return false;
			}
			trialTotal = 0;
		}

		// check arrays vertically
		for (int i = 0; i < expected.length; i++) {
			for (int j = 0; j < expected.length; j++) {
				trialTotal += expected[j][i];
			}
			if (trialTotal != targetTotal) {
				return false;
			}
			trialTotal = 0;
		}

		//check arrays diagonally
		for (int i = 0; i < expected.length; i++) {
			trialTotal += expected[i][i];
		}
		if (trialTotal != targetTotal) {
			return false;
		}
		trialTotal = 0;
		
		for (int i = 0; i < expected.length; i++) {
			trialTotal += expected[i][expected.length - (i+1)];
		}
		if (trialTotal != targetTotal) {
			return false;
		}
		else {
			return true;
		}
	}

}
