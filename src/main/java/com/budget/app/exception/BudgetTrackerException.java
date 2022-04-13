package com.budget.app.exception;

public class BudgetTrackerException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public BudgetTrackerException(String message) {
		super(message);
	}
	
	public BudgetTrackerException() {
		super("Not found");
	}

}
