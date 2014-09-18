package com.apollo.training.set2;

import static org.junit.Assert.*;

import org.junit.Test;

public class MagicSquareTests {

	@Test
	public void testGoodGenerate() {
		MagicSquare magSquare = new MagicSquare();
		int dimension = 3;
		int[][] actual = magSquare.generate(dimension);
		int[][] expected = new int[][] {
				{ 4, 9, 2 },
				{ 3, 5, 7 },
				{ 8, 1, 6 }
		};

		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testBadGenerate() throws Exception {
		MagicSquare magSquare = new MagicSquare();
		int dimension = 3;
		int[][] actual = magSquare.generate(dimension);
		int[][] expected = new int[][] {
				{ 4, 9, 2 },
				{ 3, 5, 7 },
				{ 6, 1, 8 }
		};
		
		assertNotSame(expected, actual);
	}
	
	@Test
	public void testGoodCheck() throws Exception {
		MagicSquare magSquare = new MagicSquare();
		int[][] expected = new int[][] {
				{ 4, 9, 2 },
				{ 3, 5, 7 },
				{ 8, 1, 6 }
		};
		boolean validSquare = magSquare.check(expected);
		assertTrue(validSquare);
	}
	
	@Test
	public void testBadCheck() throws Exception {
		MagicSquare magSquare = new MagicSquare();
		int[][] expected = new int[][] {
				{ 9, 4, 5 },
				{ 3, 5, 7 },
				{ 8, 1, 6 }
		};
		boolean validSquare = magSquare.check(expected);
		assertFalse(validSquare);
	}

}
