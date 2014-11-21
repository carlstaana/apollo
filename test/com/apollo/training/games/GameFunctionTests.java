package com.apollo.training.games;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("unused")
public class GameFunctionTests {

	@SuppressWarnings("static-access")
	@Test
	public void test() {
		GameFunction gf = new GameFunction();
		gf.pressAnyKeyToContinue();
	}

}
