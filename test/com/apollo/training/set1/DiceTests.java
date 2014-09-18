package com.apollo.training.set1;

import static org.junit.Assert.*;

import org.junit.Test;

public class DiceTests {

	@Test
	public void testDice() {
		int N = 6; 			//total number of sides on dice
		int expected = 3; 	//any number between 1-6
		int actual = 0; 	//set new variable
		
		Dice dice = new Dice(N);
		while(actual != 3){
			actual = dice.roll();
		}
		
		
		assertEquals(expected, actual);
	}

}
