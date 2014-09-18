package com.apollo.training.book.chapter3;

import static org.junit.Assert.*;

import org.junit.Test;

public class MothTests {

	@Test
	public void test() {
		Moth moth = new Moth(10.0);
		
		moth.moveToLight(0);
		double position = moth.getPosition();
		
		assertEquals(5, position, 0);
	}

}
