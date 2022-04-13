package com.budget.app.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budget.app.constants.AppContants;
import com.budget.app.dao.CategoryDao;
import com.budget.app.dao.UserDao;
import com.budget.app.entity.Category;
import com.budget.app.entity.User;
import com.budget.app.exception.DataNotFoundException;
import com.budget.app.model.CategoryDTO;

@Service
public class CategoryService {
private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private UserDao userDao;
	
	public Category createCategory(CategoryDTO categoryDto) throws DataNotFoundException {
		logger.info("Adding categorey triggered");
		Optional<User> userOptional = userDao.findById(categoryDto.getUserId());
		User user = userOptional.isPresent() ? userOptional.get() : userOptional.orElseThrow(DataNotFoundException::new);
		Category category = new Category(user, categoryDto.getTitle(), 
				categoryDto.getDescription(), categoryDto.getType(), AppContants.ACTIVE, new Date());
		logger.info("Adding categorey completed");
		return categoryDao.save(category);
	}
	
	public List<Category> getAllCategory(long userId) {
		return categoryDao.findAllByUserIdAndStatus(userId, AppContants.ACTIVE);
	}
	
	public Category getCategoryById(long categoryId) throws DataNotFoundException {
		Optional<Category> categoryOptional = categoryDao.findById(categoryId);
		return categoryOptional.isPresent() ? categoryOptional.get() : categoryOptional.orElseThrow(DataNotFoundException::new);
	}
}
