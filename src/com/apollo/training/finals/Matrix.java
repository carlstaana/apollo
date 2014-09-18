package com.apollo.training.finals;

import com.apollo.training.finals.matrix.IncompatibleDimensionsException;

public class Matrix {
	int maxRow;
	int maxCol;
	int[][] theMatrix;

	public Matrix(int row, int col) {
		maxRow = row;
		maxCol = col;
		theMatrix = new int[row][col];
	}

	public void populateRow(int i, int[] row) throws IncompatibleDimensionsException {
		// if row number is not in the scope of the matrix
		if (i < 0 || i > maxRow - 1) {
			throw new IncompatibleDimensionsException("Row Index " + i + " does not exist!");
		} else if (row.length != maxCol) {
			throw new IncompatibleDimensionsException("Values to be populated lacks/exceeds the existing matrix!");
		}

		for (int j = 0; j < maxCol; j++) {
			theMatrix[i][j] = row[j];
		}
	}

	public void populateColumn(int i, int[] column) throws IncompatibleDimensionsException {
		// if column number is not in the scope of the matrix
		if (i < 0 || i > maxCol - 1) {
			throw new IncompatibleDimensionsException("Column Index " + i + " does not exist!");
		} else if (column.length != maxRow) {
			throw new IncompatibleDimensionsException("Values to be populated lacks/exceeds the existing matrix!");
		}

		for (int j = 0; j < maxRow; j++) {
			theMatrix[j][i] = column[j];
		}
	}

	public Matrix add(Matrix otherMatrix) throws IncompatibleDimensionsException {
		// check lengths of rows and columns. it should be equal
		if (maxRow != otherMatrix.maxRow || maxCol != otherMatrix.maxCol) {
			throw new IncompatibleDimensionsException("Dimensions are not equal!");
		}
		
		Matrix newMatrix = new Matrix(maxRow, maxCol);
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxCol; j++) {
				newMatrix.theMatrix[i][j] = theMatrix[i][j] + otherMatrix.theMatrix[i][j];
			}
		}
		
		return newMatrix;
	}
	
	public Matrix multiply(Matrix otherMatrix) throws IncompatibleDimensionsException {
		// check length of columns and rows
		if (maxCol != otherMatrix.maxRow) {
			throw new IncompatibleDimensionsException("Column number of Matrix1 and Row number of Matrix2 must be equal!");
		}
		
		Matrix newMatrix = new Matrix(maxRow, otherMatrix.maxCol);
		for (int i = 0; i < newMatrix.maxRow; i++) {
			for (int j = 0; j < newMatrix.maxCol; j++) {
				for (int j2 = 0; j2 < maxCol; j2++) {
					newMatrix.theMatrix[i][j] += theMatrix[i][j2] * otherMatrix.theMatrix[j2][j]; 
				}
			}
		}
		
		return newMatrix;
	}
	
	@Override
	public String toString() {
		String output = "";
		System.out.println();
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxCol; j++) {
				output += theMatrix[i][j] + "\t";
			}
			output += "\n";
		}
		System.out.println(output);
		return output;
	}
}
