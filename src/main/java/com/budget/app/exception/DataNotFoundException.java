package com.budget.app.exception;

public class DataNotFoundException  extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String message) {
		super(message);
	}
	
	public DataNotFoundException() {
		super("Data not found!");
	}

}
