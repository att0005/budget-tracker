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

import com.budget.app.entity.Account;
import com.budget.app.model.AccountDTO;
import com.budget.app.service.AccountService;

@Controller
@CrossOrigin
@RequestMapping("/account")
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping
	public ResponseEntity<Account> createAccount(@RequestBody AccountDTO accountDto) {
		logger.info("Create Account triggered for : {}, with account name : {}", accountDto.getUserId(), accountDto.getName());
		return ResponseEntity.ok(accountService.addAccount(accountDto));
	}
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<List<Account>> getAllAccount(@PathVariable long userId) {
		logger.info("Get all Accounts triggered for : {}", userId);
		return ResponseEntity.ok(accountService.getAllAccounts(userId));
	}
}
