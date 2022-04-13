package com.budget.app.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.budget.app.entity.Budget;

@Repository
public interface BudgetDao extends CrudRepository<Budget, Long> {
	
	List<Budget> findAllByUserIdAndMonthAndYear(long userId, String month, String year);

}
