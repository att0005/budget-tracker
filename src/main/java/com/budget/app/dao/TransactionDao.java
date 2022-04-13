package com.budget.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.budget.app.constants.AppContants;
import com.budget.app.entity.Transaction;

@Repository
public interface TransactionDao extends CrudRepository<Transaction, Long> {
	
	List<Transaction> findAllByUserId(long userId);
	
	List<Transaction> findAllByUserIdAndType(long userId, String type);
	
	@Query(value = AppContants.SQL_TRANSACTION_USER_CATEGORY_DATE, nativeQuery = true)
	List<Transaction> findAllByUserIdAndCategoryId(long userId, long categoryId, String dateFormat);
	
	@Query(value = AppContants.SQL_TRANSACTION_USER_TYPE_DATE, nativeQuery = true)
	List<Transaction> findAllByUserIdAndType(long userId, String type, String dateFormat);
	
	
	@Query(value = AppContants.SQL_TRANSACTION_USER_DATE, nativeQuery = true)
	List<Transaction> findAllByUserIdAndTypeOrderByDateAsc(long userId, String dateFormat);

}
