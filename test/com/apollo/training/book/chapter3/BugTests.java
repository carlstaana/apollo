package com.apollo.training.book.chapter3;

import static org.junit.Assert.*;

import org.junit.Test;

public class BugTests {

	@Test
	public void test() {
		Bug bug = new Bug(10);
		
		bug.move();
		bug.turn();
		bug.move();
		bug.move();
		
		int position = bug.getPosition();
		
		assertEquals(9, position);
	}

}
