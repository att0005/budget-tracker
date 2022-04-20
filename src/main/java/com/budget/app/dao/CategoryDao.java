package com.budget.app.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.budget.app.entity.Category;

@Repository
public interface CategoryDao extends CrudRepository<Category, Long> {
	
	List<Category> findAllByUserId(long userId);
	
	Category findByTitle(String title);
	
	List<Category> findAllByUserIdAndStatus(long userId, String status);
	
}
