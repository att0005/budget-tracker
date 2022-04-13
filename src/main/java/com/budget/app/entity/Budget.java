package com.budget.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "budget")
public class Budget {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@Column(name="estimated_expense")
	private Double estimatedExpense;
	
	@Column(name="estimated_income")
	private Double estimatedIncome;
	
	@Column(name="month")
	private String month;
	
	@Column(name="year")
	private String year;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public Budget(User user, Category category, Double estimatedExpense, Double estimatedIncome, String month,
			String year) {
		super();
		this.user = user;
		this.category = category;
		this.estimatedExpense = estimatedExpense;
		this.estimatedIncome = estimatedIncome;
		this.month = month;
		this.year = year;
	}

	public Budget() {
		super();
	}
	
	
}
