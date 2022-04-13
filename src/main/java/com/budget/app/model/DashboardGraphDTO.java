package com.budget.app.model;

import java.util.List;

public class DashboardGraphDTO {
	
	private List<DashboardResDTO> incomeDtoList;
	private List<DashboardResDTO> expenseDtoList;
	public List<DashboardResDTO> getIncomeDtoList() {
		return incomeDtoList;
	}
	public void setIncomeDtoList(List<DashboardResDTO> incomeDtoList) {
		this.incomeDtoList = incomeDtoList;
	}
	public List<DashboardResDTO> getExpenseDtoList() {
		return expenseDtoList;
	}
	public void setExpenseDtoList(List<DashboardResDTO> expenseDtoList) {
		this.expenseDtoList = expenseDtoList;
	}
}
