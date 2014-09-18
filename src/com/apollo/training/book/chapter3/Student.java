package com.apollo.training.book.chapter3;

import java.util.ArrayList;

public class Student {
	private String lastName;
	private String firstName;
	private String middleName;
	private ArrayList<Integer> quizzes;

	public Student(String lastName, String firstName, String middleName) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = middleName;
		
		System.out.println("New Student\nName: " + getName());
		
		quizzes = new ArrayList<Integer>();
	}

	private String getName() {
		String output = lastName + ", " + firstName + " " + middleName.charAt(0) + ".";
		return output;
	}

	public void addQuiz(int score) {
		quizzes.add(score);
	}

	public int getTotalScore() {
		int total = 0;
		
		for (Integer score : quizzes) {
			total += score;
		}
		
		System.out.println("Total Score: " + total);
		return total;
	}

	public double getAverageScore() {
		double average = getTotalScore() / quizzes.size();
		System.out.println("Average Score: " + average);
		return average;
	}

}
