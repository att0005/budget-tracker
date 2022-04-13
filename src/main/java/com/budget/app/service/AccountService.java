package com.budget.app.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budget.app.constants.AppContants;
import com.budget.app.dao.AccountDao;
import com.budget.app.dao.UserDao;
import com.budget.app.entity.Account;
import com.budget.app.entity.User;
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
		Account account = new Account(user, accountDto.getName(), accountDto.getType(), AppContants.ACTIVE, new Date());
		logger.info("Account added successfully : {}, with account name : {}", user.getUsername(), accountDto.getName());
		return accountDao.save(account);
	}
	
	public List<Account> getAllAccounts(long userId) {
		return accountDao.findAllByUserIdAndStatus(userId, AppContants.ACTIVE);
	}
}
