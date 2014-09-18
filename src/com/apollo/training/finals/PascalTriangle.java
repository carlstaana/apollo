package com.apollo.training.finals;

public class PascalTriangle {

	public int[][] generate(int i) {
		int[][] myArray = new int[i][i];
		myArray[0][0] = 1;
		
		for (int j = 1; j < myArray.length; j++) {
			for (int j2 = 0; j2 < myArray.length; j2++) {
				if (j2 == 0) {
					myArray[j][j2] = 1;
				} else {
					myArray[j][j2] = myArray[j-1][j2-1] + myArray[j-1][j2];
				}
			}
		}
		
		for (int j = 0; j < myArray.length; j++) {
			for (int j2 = 0; j2 < myArray.length; j2++) {
				if (myArray[j][j2] == 0) {
					break;
				} else {
					System.out.print(myArray[j][j2] + "\t");
				}
				
			}
			System.out.println();
		}
		
		return myArray;
	}

}
