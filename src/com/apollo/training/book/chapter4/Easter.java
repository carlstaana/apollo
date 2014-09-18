package com.apollo.training.book.chapter4;

public class Easter {
	private int year;
	private int easterMonth;
	private int easterDay;

	public Easter(int year) {
		this.year = year;
		
		int a = year % 19;
		int b = year / 100;
		int c = b % a;
		int d = b / 4;
		int e = d % a;
		int g = ((8 * b) + 13) / 25;
		int h = ((19 * a) + b - d - g + 15) % 30;
		int j = c / 4;
		int k = c % 4;
		int m = (a + (11 * h)) / 319;
		int r = ((2 * e) + (2 * j) - k - h - m + 32) % 7;
		easterMonth = (h - m + r + 90) / 25;
		easterDay = (h - m + r + easterMonth + 19) % 32;
	}

	public String getEasterSundayMonth() {
		switch (easterMonth) {
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9:
			return "September";
		case 10:
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";
		}
		return null;
	}

	public int getEasterSundayDay() {
		return easterDay;
	}

}
