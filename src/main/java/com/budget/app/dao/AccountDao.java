package com.budget.app.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.budget.app.entity.Account;

@Repository
public interface AccountDao extends CrudRepository<Account, Long> {
	
	List<Account> findAllByUserId(long userId);
	
	List<Account> findAllByUserIdAndStatus(long userId, String status);

}
