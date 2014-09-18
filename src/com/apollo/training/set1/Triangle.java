package com.apollo.training.set1;

import java.awt.Point;
import java.util.Arrays;

public class Triangle {
	Point pt1 = new Point();
	Point pt2 = new Point();
	Point pt3 = new Point();
	double sideArray[] = new double[3]; // will get lengths
	double angleArray[] = new double[3]; // will get angles
	
	public Triangle(Point pt1, Point pt2, Point pt3) {
		this.pt1 = pt1;
		this.pt2 = pt2;
		this.pt3 = pt3;
	}

	public String calculateLengths() {
		String output;
		
		sideArray[0] = pt1.distance(pt2);
		sideArray[1] = pt2.distance(pt3);
		sideArray[2] = pt3.distance(pt1);
		Arrays.sort(sideArray);
		
		output = sideArray[0] + " " + sideArray[1] + " " + sideArray[2];
		System.out.println(output);
		
		return output;
	}
	
	public String calculateAngles() {
		String output;
		
		// ---apply cosine rule---
		angleArray[0] = Math.acos((sideArray[1] * sideArray[1] + sideArray[2] * sideArray[2] - sideArray[0] * sideArray[0]) / 
				(2 * sideArray[1] * sideArray[2]));
		angleArray[1] = Math.acos((sideArray[0] * sideArray[0] + sideArray[2] * sideArray[2] - sideArray[1] * sideArray[1]) / 
				(2 * sideArray[0] * sideArray[2]));
		angleArray[2] = Math.acos((sideArray[0] * sideArray[0] + sideArray[1] * sideArray[1] - sideArray[2] * sideArray[2]) / 
				(2 * sideArray[0] * sideArray[1]));
		Arrays.sort(angleArray);
		
		output = angleArray[0] + " " + angleArray[1] + " " + angleArray[2];
		System.out.println(output);
		
		return output;
	}

	public double calculatePerimeter() {
		double output = sideArray[0] + sideArray[1] + sideArray[2];
		System.out.println(output);
		
		return output;
	}
	
}
