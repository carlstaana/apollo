package com.apollo.training.finals;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("unused")
public class PascalTriangleTests {

	@Test
	public void test() {
		PascalTriangle pascal = new PascalTriangle();
		int[][] testArray = pascal.generate(5);
	}

}
