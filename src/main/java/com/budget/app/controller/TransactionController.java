package com.budget.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.budget.app.entity.Transaction;
import com.budget.app.model.TransactionDTO;
import com.budget.app.service.TransactionService;

@Controller
@CrossOrigin
@RequestMapping("/transaction")
public class TransactionController {

	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping
	public ResponseEntity<Transaction> addTransactions(@RequestBody TransactionDTO transactionDTO) {
		logger.info("add transactions triggered  for user : {}", transactionDTO.getUserId());
		return ResponseEntity.ok(transactionService.saveTransaction(transactionDTO));
	}
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<List<Transaction>> getAllTransactions(@PathVariable long userId) {
		logger.info("Get all transactions triggered  for user : {}", userId);
		return ResponseEntity.ok(transactionService.getAllTransactions(userId));
	}
	
	@GetMapping(value = "/income/{userId}")
	public ResponseEntity<List<Transaction>> getAllIncomeTransactions(@PathVariable long userId) {
		logger.info("Get all income transactions triggered  for user : {}", userId);
		return ResponseEntity.ok(transactionService.getAllIncomeTransactions(userId));
	}
	
	@GetMapping(value = "/expense/{userId}")
	public ResponseEntity<List<Transaction>> getAllExpenseTransactions(@PathVariable long userId) {
		logger.info("Get all expense transactions triggered  for user : {}", userId);
		return ResponseEntity.ok(transactionService.getAllExpenseTransactions(userId));
	}
	
	@GetMapping(value = "/{userId}/{date}")
	public ResponseEntity<List<Transaction>> getAllExpenseTransactions(@PathVariable long userId, @PathVariable String date ) {
		logger.info("Get all transactions triggered  for user : {} with date range : {}", userId, date);
		return ResponseEntity.ok(transactionService.findAllByUserIdAndTypeOrderByDateAsc(userId, date));
	}
	
	@GetMapping(value = "/currentBalance/{userId}")
	public ResponseEntity<Double> getCurrentBalance(@PathVariable long userId ) {
		List<Transaction> transactions = transactionService.getAllTransactions(userId);
		double income = transactions.stream().filter(e -> e.getType().equals("income")).mapToDouble(e -> e.getAmount()).sum();
		double expense = transactions.stream().filter(e -> e.getType().equals("expense")).mapToDouble(e -> e.getAmount()).sum();
		System.out.println("--1->"+income);
		System.out.println("--2->"+expense);
		return ResponseEntity.ok(income-expense);
	}
	
	
}
