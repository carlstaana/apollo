package com.apollo.training.set1;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class BirthdayCalculator {
	GregorianCalendar cal = new GregorianCalendar();
	
	public BirthdayCalculator(GregorianCalendar cal) {
		this.cal = cal;
	}

	public String getWeekDay(int input) {
		cal.add(Calendar.YEAR, input);
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		String output = "";
		
		switch(weekday){
		case 1:
			output = "Sunday";
			break;
		case 2:
			output = "Monday";
			break;
		case 3:
			output = "Tuesday";
			break;
		case 4:
			output = "Wednesday";
			break;
		case 5:
			output = "Thursday";
			break;
		case 6:
			output = "Friday";
			break;
		case 7:
			output = "Saturday";
			break;
		}
		
		return output;
	}
	
}
