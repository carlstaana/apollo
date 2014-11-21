package com.apollo.training.book.chapter4;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("unused")
public class EasterSundayTests {

	@Test
	public void test() {
		Easter easter = new Easter(2001);
		
		String month = easter.getEasterSundayMonth();
		int day = easter.getEasterSundayDay();
		
		System.out.println(month + " " + day);
	}

}
