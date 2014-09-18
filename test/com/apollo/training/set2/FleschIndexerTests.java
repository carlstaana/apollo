package com.apollo.training.set2;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class FleschIndexerTests {

	@Test
	public void testGoodIndex() throws IOException {
		File fileRead = new File("file.txt");
		FleschIndexer flesch = new FleschIndexer();
		String actual = flesch.index(fileRead);
		String expected = "5th Grader";
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBadIndex() throws Exception {
		File fileRead = new File("file.txt");
		FleschIndexer flesch = new FleschIndexer();
		String actual = flesch.index(fileRead);
		String expected = "9th Grader"; // expect a wrong value
		assertNotEquals(expected, actual);
	}

}
