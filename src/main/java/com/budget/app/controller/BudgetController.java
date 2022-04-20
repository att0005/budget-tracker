package com.budget.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.budget.app.entity.Budget;
import com.budget.app.model.BudgetDTO;
import com.budget.app.model.BudgetTableResDTO;
import com.budget.app.service.BudgetService;

@Controller
@CrossOrigin
@RequestMapping("/budget")
public class BudgetController {
	
private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);
	
	@Autowired
	private BudgetService budgetService;
	
	@PostMapping
	public ResponseEntity<Budget> addBudget(@RequestBody BudgetDTO budgetDto) {
		logger.info("Add budget triggered");
		return ResponseEntity.ok(budgetService.saveBudget(budgetDto));
	}
	
	@GetMapping(value = "/{userId}/{month}/{year}")
	public ResponseEntity<BudgetTableResDTO> getAllBudgets(@PathVariable long userId, @PathVariable String month,
			@PathVariable String year) {
		return ResponseEntity.ok(budgetService.getAllBudgets(userId, month, year));
	}
	
	@PutMapping(value = "/{budgetId}")
	public ResponseEntity<Budget> updateBudget(@RequestBody BudgetDTO budgetDto, @PathVariable long budgetId) {
		logger.info("Update Category triggered for : {}, with Budget Id : {}", budgetDto.getUserId(), budgetId);
		return ResponseEntity.ok(budgetService.updateBudget(budgetDto, budgetId));
	}
	
	@DeleteMapping(value = "/{budgetId}")
	public ResponseEntity<String> deleteCategory(@PathVariable long budgetId) {
		logger.info("Delete budget triggered with budgetId : {}",budgetId);
		budgetService.deleteBudget(budgetId);
		return ResponseEntity.ok("Successfully deleted");
	}
}
