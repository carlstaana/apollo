package com.apollo.training.games;

public class GameFunction {

	
	public static void delay(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void clearScreen() {
		for (int i = 0; i < 70; i++) {
			System.out.println();
		}
	}
	
	public static void pressAnyKeyToContinue() {
		System.out.println("Press enter to continue...");
        try {
            System.in.read();
        }  
        catch(Exception e) {
        	
        }
	}
}
