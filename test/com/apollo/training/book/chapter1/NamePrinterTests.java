package com.apollo.training.book.chapter1;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("unused")
public class NamePrinterTests {

	@Test
	public void test() {
		NamePrinter np = new NamePrinter();
		
		np.print("Carl Kenneth E. Sta.Ana");
	}

}
