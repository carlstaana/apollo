package com.apollo.training.set2;

public class CreditCardChecker {

	public boolean check(String creditCardnumber) {
		int sum;
		String getZero;
		
		sum = stepOne(creditCardnumber);
		//System.out.println(sum);
		
		sum += stepTwo(creditCardnumber);
		System.out.print(sum);
		
		getZero = String.valueOf(sum);
		
		if(getZero.charAt(getZero.length()-1) == '0') {
			System.out.println("\tThe Credit Card Number is VALID!");
			return true;
		}
		else {
			System.out.println("\tINVALID Credit Card Number!");
			return false;
		}
		
	}

	private int stepOne(String num) {
		int sum = 0;
		
		for(int i = num.length()-1; i >= 0; i -= 2) {
			sum += Integer.parseInt(num.substring(i, i+1));
		}
		
		return sum;
	}
	
	private int stepTwo(String num) {
		int sum = 0;
		String temp = "";
		
		for(int i = num.length()-2; i >= 0 ; i -= 2) {
			temp += Integer.parseInt(num.substring(i, i+1)) * 2;
		}
		
		for(int i = 0; i < temp.length(); i++) {
			sum += Integer.parseInt(temp.substring(i, i+1));
		}
		
		return sum;
	}

}
