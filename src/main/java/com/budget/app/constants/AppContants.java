package com.budget.app.constants;

public class AppContants {
	
	public static final String INCOME = "income";
	public static final String EXPENSE = "expense";
	public static final String ACTIVE = "active";
	public static final String DECIMAL_FORMAT = "0.00";
	public static final String ZERO = "0";
	public static final String EMPTY = "";
	public static final String HYPHEN = "-";
	
	public static final String SQL_TRANSACTION_USER_CATEGORY_DATE = "SELECT * FROM transaction WHERE user_id = ?1 and category_id = ?2 and date Like (%?3%)";
	public static final String SQL_TRANSACTION_USER_TYPE_DATE = "SELECT * FROM transaction WHERE user_id = ?1 and type = ?2 and date Like (%?3%)";
	public static final String SQL_TRANSACTION_USER_DATE = "SELECT * FROM transaction WHERE user_id = ?1 and date Like (%?2%) ORDER BY date ASC";
}
