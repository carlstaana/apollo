package com.apollo.training.set1;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class TriangleTests {

	@Test
	public void test() {
		Point pt1 = new Point(0, 0);
		Point pt2 = new Point(2, 2);
		Point pt3 = new Point(4, 0);
		String actualLengths, actualAngles, expectedLengths, expectedAngles;
		double actualPerimeter, expectedPerimeter;
		
		Triangle triangle = new Triangle(pt1, pt2, pt3);
		
		actualLengths = triangle.calculateLengths();
		expectedLengths = "2.8284271247461903 2.8284271247461903 4.0";
		assertEquals(expectedLengths, actualLengths);
		
		actualAngles = triangle.calculateAngles();
		expectedAngles = "0.7853981633974486 0.7853981633974486 1.5707963267948963";
		assertEquals(expectedAngles, actualAngles);
		
		actualPerimeter = triangle.calculatePerimeter();
		expectedPerimeter = 9.65685424949238;
		assertEquals(expectedPerimeter, actualPerimeter, 0);
		
	}

}
