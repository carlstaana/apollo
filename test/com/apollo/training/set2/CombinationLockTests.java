package com.apollo.training.set2;

import static org.junit.Assert.*;

import org.junit.Test;

public class CombinationLockTests {

	// ---PROBLEM---
	// a lock with 26 positions, A to Z
	// dial needs to be set THREE times, ONE AT A TIME
	// if combination is correct, lock is OPENED
	// when lock is CLOSED, the combination can be entered again; "can only enter again when lock is CLOSED"
	// if user sets the dial more than three times, only the LAST THREE combinations are considered

	@Test
	public void testGoodSet1() {
		CombinationLock comLock = new CombinationLock();	// add a combination lock Class
		comLock.close('C', 'K', 'S'); // set key combinations
		// set correct combinations
		comLock.set('C');
		comLock.set('K');
		comLock.set('S');
		boolean lockOpen = comLock.open();
		assertTrue(lockOpen);
	}
	
	@Test
	public void testBadSet1() throws Exception {
		CombinationLock comLock = new CombinationLock();	// add a combination lock Class
		comLock.close('C', 'K', 'S'); // set key combinations
		// set WRONG combinations
		comLock.set('A');
		comLock.set('B');
		comLock.set('C');
		boolean lockOpen = comLock.open(); // open the lock
		
		//assertion must be false
		assertFalse(lockOpen);
	}
	
	@Test
	public void testGoodSet2() throws Exception {
		CombinationLock comLock = new CombinationLock();	// add a combination lock Class
		comLock.close('C', 'K', 'S'); // set key combinations
		// set positions more than THREE times, but the last three combinations are CORRECT
		comLock.set('A');
		comLock.set('B');
		comLock.set('C');
		comLock.set('C');
		comLock.set('K');
		comLock.set('S');
		boolean lockOpen = comLock.open(); // open the lock
		
		//assertion must be true
		assertTrue(lockOpen);
	}
	
	@Test
	public void testBadSet2() throws Exception {
		CombinationLock comLock = new CombinationLock();	// add a combination lock Class
		comLock.close('C', 'K', 'S'); // set key combinations
		// set positions more than THREE times, but the last three combinations are CORRECT
		comLock.set('A');
		comLock.set('B');
		comLock.set('C');
		comLock.set('S');
		comLock.set('R');
		comLock.set('T');
		boolean lockOpen = comLock.open(); // open the lock
		
		//assertion must be false
		assertFalse(lockOpen);
	}
	
	@Test
	public void testBadOpen() throws Exception {
		CombinationLock comLock = new CombinationLock();	// add a combination lock Class
		comLock.close('C', 'K', 'S'); // set key combinations
		// set correct combinations
		comLock.set('C');
		comLock.set('K');
		comLock.set('S');
		boolean lockOpen = comLock.open();
		lockOpen = comLock.open(); // test to open again the lock
		assertFalse(lockOpen);
	}

}
