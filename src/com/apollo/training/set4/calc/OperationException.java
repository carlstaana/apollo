package com.apollo.training.set4.calc;

@SuppressWarnings("serial")
public class OperationException extends Exception {
	public OperationException(String message) {
		super(message);
		System.out.println(message);
	}
}
