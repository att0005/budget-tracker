package com.budget.app.model;

public class BudgetDTO {
	private long userId;
	
	private long categoryId;
	
	private Double estimatedExpense;
	
	private Double estimatedIncome;
	
	private String month;
	
	private String year;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public Double getEstimatedExpense() {
		return estimatedExpense;
	}

	public void setEstimatedExpense(Double estimatedExpense) {
		this.estimatedExpense = estimatedExpense;
	}

	public Double getEstimatedIncome() {
		return estimatedIncome;
	}

	public void setEstimatedIncome(Double estimatedIncome) {
		this.estimatedIncome = estimatedIncome;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public BudgetDTO(long userId, long categoryId, Double estimatedExpense, Double estimatedIncome, String month,
			String year) {
		super();
		this.userId = userId;
		this.categoryId = categoryId;
		this.estimatedExpense = estimatedExpense;
		this.estimatedIncome = estimatedIncome;
		this.month = month;
		this.year = year;
	}

	public BudgetDTO() {
		super();
	}
	
	
}
