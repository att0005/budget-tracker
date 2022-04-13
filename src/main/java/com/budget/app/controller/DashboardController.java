package com.budget.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budget.app.model.DashboardGraphDTO;
import com.budget.app.service.TransactionService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
	private TransactionService transactionService;

	@GetMapping(value = "/{userId}/{date}")
	public  ResponseEntity<DashboardGraphDTO> getDashboardData(@PathVariable long userId, @PathVariable String date ) {
		return ResponseEntity.ok(transactionService.getAllTransaction(userId, date));
	}

}