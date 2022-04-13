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
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@Column(name="title")
	private String title;
	
	@Column(name="type")
	private String type;
	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="date")
	private String date;

	public long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Account getAccount() {
		return account;
	}

	public Category getCategory() {
		return category;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public Double getAmount() {
		return amount;
	}

	public String getDate() {
		return date;
	}

	public Transaction(User user, Account account, Category category, String title, String type, Double amount,
			String date) {
		super();
		this.user = user;
		this.account = account;
		this.category = category;
		this.title = title;
		this.type = type;
		this.amount = amount;
		this.date = date;
	}

	public Transaction() {
		super();
	}

	
}
