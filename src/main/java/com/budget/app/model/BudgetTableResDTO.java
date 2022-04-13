package com.budget.app.model;

import java.util.List;

public class BudgetTableResDTO {
	List<BudgetTableResponseDto> budgetTableResponse;
	private double totalSpent;
	private double totalEarn;
	private double totalEstiSpent;
	private double totalEstiEarn;
	private String spentPercentage;
	private String earnPercentage;
	public List<BudgetTableResponseDto> getBudgetTableResponse() {
		return budgetTableResponse;
	}
	public void setBudgetTableResponse(List<BudgetTableResponseDto> budgetTableResponse) {
		this.budgetTableResponse = budgetTableResponse;
	}
	public double getTotalSpent() {
		return totalSpent;
	}
	public void setTotalSpent(double totalSpent) {
		this.totalSpent = totalSpent;
	}
	public double getTotalEarn() {
		return totalEarn;
	}
	public void setTotalEarn(double totalEarn) {
		this.totalEarn = totalEarn;
	}
	public double getTotalEstiSpent() {
		return totalEstiSpent;
	}
	public void setTotalEstiSpent(double totalEstiSpent) {
		this.totalEstiSpent = totalEstiSpent;
	}
	public double getTotalEstiEarn() {
		return totalEstiEarn;
	}
	public void setTotalEstiEarn(double totalEstiEarn) {
		this.totalEstiEarn = totalEstiEarn;
	}
	public String getSpentPercentage() {
		return spentPercentage;
	}
	public void setSpentPercentage(String spentPercentage) {
		this.spentPercentage = spentPercentage;
	}
	public String getEarnPercentage() {
		return earnPercentage;
	}
	public void setEarnPercentage(String earnPercentage) {
		this.earnPercentage = earnPercentage;
	}
}
