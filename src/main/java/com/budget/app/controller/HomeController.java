package com.budget.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping(value = "/home")
	public String showHomeForm() {
		return "home";
	}
	
	@GetMapping(value = "/expense")
	public String showExpenseForm() {
		return "expense";
	}
	
	@GetMapping(value = "/income")
	public String showIncomeForm() {
		return "income";
	}
	
	@GetMapping(value = "/budget")
	public String showBudget() {
		return "budget";
	}
}
