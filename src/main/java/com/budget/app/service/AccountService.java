package com.budget.app.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.budget.app.constants.AppContants;
import com.budget.app.dao.AccountDao;
import com.budget.app.dao.UserDao;
import com.budget.app.entity.Account;
import com.budget.app.entity.User;
import com.budget.app.exception.BudgetTrackerException;
import com.budget.app.exception.DataNotFoundException;
import com.budget.app.model.AccountDTO;

@Service
public class AccountService {
private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private UserDao userDao;
	
	public Account addAccount(AccountDTO accountDto) throws DataNotFoundException {
		Optional<User> userOptional = userDao.findById(accountDto.getUserId());
		User user = userOptional.isPresent() ? userOptional.get() : userOptional.orElseThrow(DataNotFoundException::new);
		
		Account accountFromDb = accountDao.findByNameAndUserId(accountDto.getName(), accountDto.getUserId());
		if(accountFromDb != null) {
			throw new BudgetTrackerException("Account name already exists");
		}
		
		Account account = new Account(user, accountDto.getName(), accountDto.getType(), AppContants.ACTIVE, new Date());
		logger.info("Account added successfully : {}, with account name : {}", user.getUsername(), accountDto.getName());
		return accountDao.save(account);
	}
	
	public Account updateAccount(AccountDTO accountDto, long accountId) throws DataNotFoundException {
		Optional<User> userOptional = userDao.findById(accountDto.getUserId());
		User user = userOptional.isPresent() ? userOptional.get() : userOptional.orElseThrow(DataNotFoundException::new);
		
		Optional<Account> accOptional = accountDao.findById(accountId);
		Account accFromDb = accOptional.isPresent() ? accOptional.get() : accOptional.orElseThrow(DataNotFoundException::new);
		
		if(!accountDto.getName().equals(accFromDb.getName())) {
			accFromDb.setName(accountDto.getName());
		}
		
		if(!accountDto.getType().equals(accFromDb.getType())) {
			accFromDb.setType(accountDto.getType());
		}
		
		logger.info("Account added successfully : {}, with account name : {}", user.getUsername(), accountDto.getName());
		return accountDao.save(accFromDb);
	}
	
	public List<Account> getAllAccounts(long userId) {
		return accountDao.findAllByUserIdAndStatus(userId, AppContants.ACTIVE);
	}
	
	public void deleteAccount(long accountId) {
		try {
		accountDao.deleteById(accountId);
		} catch (DataIntegrityViolationException e) {
			throw new BudgetTrackerException("Delete Account failed, This account is mapped to income/expense transactions already");
		}
	}
}
