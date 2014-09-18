package com.apollo.training.set6;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.apollo.training.set6.loader.Loader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoaderTests {
	Loader loader = new Loader();
	
	@Test
	public void test1ParseXML() {
		boolean valid;
		try {
			loader.ParseXML();
			valid = true;
		} catch (FileNotFoundException e) {
			valid = false;
		}
		assertTrue(valid);
	}
	
	@Test
	public void test2CreateProductTable() {
		boolean valid;
		try {
			loader.ParseXML();
			loader.CreateProductTable();
			valid = true;
		} catch (SQLException | FileNotFoundException e) {
			valid = false;
		}
		assertTrue(valid);
	}
	
	@Test
	public void test3CreatePriceTable() {
		boolean valid;
		try {
			loader.ParseXML();
			loader.CreatePriceTable();
			valid = true;
		} catch (SQLException | FileNotFoundException e) {
			valid = false;
		}
		assertTrue(valid);
	}
	
	@Test
	public void test4CreateCustomerTable() {
		boolean valid;
		try {
			loader.ParseXML();
			loader.CreateCustomerTable();
			valid = true;
		} catch (SQLException | FileNotFoundException e) {
			valid = false;
		}
		assertTrue(valid);
	}

}
