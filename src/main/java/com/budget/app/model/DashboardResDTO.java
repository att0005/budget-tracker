package com.budget.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DashboardResDTO {
	@JsonProperty("name")
	private String title;
	private Double amount;
	private Double y;
	private boolean sliced;
	private boolean selected;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	public boolean getSliced() {
		return sliced;
	}
	public void setSliced(boolean sliced) {
		this.sliced = sliced;
	}
	public boolean getSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
}
