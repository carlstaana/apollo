package com.apollo.training.finals;

import static org.junit.Assert.*;

import org.junit.Test;

import com.apollo.training.finals.matrix.IncompatibleDimensionsException;

public class MatrixTests {

	@Test
	public void test() {
		Matrix matrix = new Matrix(3, 3);
		int[] column = {1, 2, 3};
		try {
			matrix.populateColumn(0, column);
			matrix.populateColumn(1, column);
			matrix.populateColumn(2, column);
			matrix.toString();
		} catch (IncompatibleDimensionsException e) {
			System.out.println(e.getMessage());
		}
		
		
		Matrix matrix2 = new Matrix(3, 3);
		int[] row = new int[3];
		int a = 1;
		for (int i = 0; i < row.length; i++) {
			row[i] = a;
			a++;
		}
		try {
			matrix2.populateRow(0, row);
			for (int i = 0; i < row.length; i++) {
				row[i] = a;
				a++;
			}
			matrix2.populateRow(1, row);
			for (int i = 0; i < row.length; i++) {
				row[i] = a;
				a++;
			}
			matrix2.populateRow(2, row);
			matrix2.toString();
		} catch (IncompatibleDimensionsException e) {
			System.out.println(e.getMessage());
		}
		
		
		try {
			Matrix sumMatrix = matrix.add(matrix2);
			sumMatrix.toString();
		} catch (IncompatibleDimensionsException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			Matrix productMatrix = matrix.multiply(matrix2);
			productMatrix.toString();
		} catch (IncompatibleDimensionsException e) {
			System.out.println(e.getMessage());
		}
	}

}
