package com.apollo.training.book.chapter3;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("unused")
public class StudentTests {

	@Test
	public void test() {
		Student student = new Student("Sta.Ana", "Carl Kenneth", "Esquillo");
		
		student.addQuiz(95);
		student.addQuiz(100);
		student.addQuiz(80);
		student.addQuiz(100);
		
		int totalScore = student.getTotalScore();
		double averageScore = student.getAverageScore();
	}

}
