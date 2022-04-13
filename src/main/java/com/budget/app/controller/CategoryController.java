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

import com.budget.app.entity.Category;
import com.budget.app.model.CategoryDTO;
import com.budget.app.service.CategoryService;

@Controller
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {

	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping
	public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDto) {
		logger.info("Create category triggered : {} for the userId : {}", categoryDto.getTitle(), categoryDto.getUserId());
		return ResponseEntity.ok(categoryService.createCategory(categoryDto));
	}
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<List<Category>> getAllCategory(@PathVariable long userId) {
		logger.info("Get all category triggered for the userId : {}", userId);
		return ResponseEntity.ok(categoryService.getAllCategory(userId));
	}
	
	@GetMapping(value = "/{userId}/{categoryId}")
	public ResponseEntity<Category> getCategoryById(@PathVariable long userId, @PathVariable long categoryId) {
		logger.info("Get category by userId and categoryId triggered for the userId : {} and categoryId : {}", userId, categoryId);
		return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
	}
}
