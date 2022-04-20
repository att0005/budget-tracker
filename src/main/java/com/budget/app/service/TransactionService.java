package com.budget.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budget.app.constants.AppContants;
import com.budget.app.dao.AccountDao;
import com.budget.app.dao.CategoryDao;
import com.budget.app.dao.TransactionDao;
import com.budget.app.dao.UserDao;
import com.budget.app.entity.Account;
import com.budget.app.entity.Category;
import com.budget.app.entity.Transaction;
import com.budget.app.entity.User;
import com.budget.app.exception.DataNotFoundException;
import com.budget.app.model.DashboardGraphDTO;
import com.budget.app.model.DashboardResDTO;
import com.budget.app.model.TransactionDTO;

@Service
public class TransactionService {
private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
	
	@Autowired
	private TransactionDao transactionDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	public Transaction saveTransaction(TransactionDTO transactionDto) throws DataNotFoundException {
		logger.info("save transaction triggered");
		Optional<User> userOptional = userDao.findById(transactionDto.getUserId());
		User user = userOptional.isPresent() ? userOptional.get() : userOptional.orElseThrow(DataNotFoundException::new);
		Optional<Account> accountOptional = accountDao.findById(transactionDto.getAccountId());
		Account account = accountOptional.isPresent() ? accountOptional.get() : accountOptional.orElseThrow(DataNotFoundException::new);
		Optional<Category> categoryOptional = categoryDao.findById(transactionDto.getCategoryId());
		Category category = categoryOptional.isPresent() ? categoryOptional.get() : categoryOptional.orElseThrow(DataNotFoundException::new);
		Transaction transaction = new Transaction(user, account,category,transactionDto.getTitle(),
				transactionDto.getType(), transactionDto.getAmount(), transactionDto.getDate());
		logger.info("save transaction completed");
		return transactionDao.save(transaction);
	}
	
	public List<Transaction> getAllTransactions(long userId) {
		return transactionDao.findAllByUserId(userId);
	}
	
	public List<Transaction> findAllByUserIdAndTypeOrderByDateAsc(long userId, String dateFormat) {
		return transactionDao.findAllByUserIdAndTypeOrderByDateAsc(userId, dateFormat);
	}
	
	public List<Transaction> getAllIncomeTransactions(long userId, String date) {
		String[] split = date.split("-");
		if(split[0].length()==0) {
			date= AppContants.ZERO+date;
		}
		System.out.println("-date-inc-->"+date);
		return transactionDao.findAllByUserIdAndType(userId, AppContants.INCOME,date);
	}
	
	public List<Transaction> getAllExpenseTransactions(long userId, String date) {
		String[] split = date.split("-");
		if(split[0].length()==0) {
			date= AppContants.ZERO+date;
		}
		System.out.println("-date-exp-->"+date);
		return transactionDao.findAllByUserIdAndType(userId, AppContants.EXPENSE,date);
	}
	
	public DashboardGraphDTO getAllTransaction(long userId, String date) {
		logger.info("getAllTransaction for home page");
		String[] split = date.split("-");
		if(split[0].length()==0) {
			date= AppContants.ZERO+date;
		}
		List<Transaction> incomeList = transactionDao.findAllByUserIdAndType(userId, AppContants.INCOME,date);
		List<Transaction> expenseList = transactionDao.findAllByUserIdAndType(userId, AppContants.EXPENSE,date);
		
		List<DashboardResDTO> incomeDtoList = new ArrayList<>();
		int countIncome = 1;
		double totalIncome = 0.0;
		for(Transaction each :incomeList){
			DashboardResDTO dto = new DashboardResDTO();
			dto.setTitle(each.getTitle());
			dto.setAmount(each.getAmount());
			if(countIncome==1) {
				dto.setSliced(true);
				dto.setSelected(true);
			}
			totalIncome = Double.sum(totalIncome, each.getAmount());
			countIncome = countIncome + 1;
			
			incomeDtoList.add(dto);
		}
		for(DashboardResDTO each :incomeDtoList){
			each.setY((each.getAmount() / totalIncome ) * 100);
		}
		
		
		List<DashboardResDTO> expenseDtoList = new ArrayList<>();
		int countExpense = 1;
		double totalExpense = 0.0;
		for(Transaction each :expenseList){
			DashboardResDTO dto = new DashboardResDTO();
			dto.setTitle(each.getTitle());
			dto.setAmount(each.getAmount());
			if(countExpense==1) {
				dto.setSliced(true);
				dto.setSelected(true);
			}
			totalExpense = Double.sum(totalExpense, each.getAmount());
			countExpense = countExpense + 1;
			
			expenseDtoList.add(dto);
		}
		for(DashboardResDTO each :expenseDtoList){
			each.setY((each.getAmount() / totalExpense ) * 100);
		}
		
		DashboardGraphDTO res = new DashboardGraphDTO();
		res.setExpenseDtoList(expenseDtoList);
		res.setIncomeDtoList(incomeDtoList);
		
		return res;
	}

	public Transaction updateTransaction(TransactionDTO transactionDto, long transId) {
		logger.info("update transaction triggered");
		Optional<User> userOptional = userDao.findById(transactionDto.getUserId());
		User user = userOptional.isPresent() ? userOptional.get() : userOptional.orElseThrow(DataNotFoundException::new);
		
		Optional<Account> accountOptional = accountDao.findById(transactionDto.getAccountId());
		Account account = accountOptional.isPresent() ? accountOptional.get() : accountOptional.orElseThrow(DataNotFoundException::new);
		
		Optional<Category> categoryOptional = categoryDao.findById(transactionDto.getCategoryId());
		Category category = categoryOptional.isPresent() ? categoryOptional.get() : categoryOptional.orElseThrow(DataNotFoundException::new);
		
		Optional<Transaction> transactionOptional = transactionDao.findById(transId);
		Transaction transactionFromDb = transactionOptional.isPresent() ? transactionOptional.get() : transactionOptional.orElseThrow(DataNotFoundException::new);
		
		if(transactionFromDb.getAccount().getId()!=transactionDto.getAccountId()) {
			transactionFromDb.setAccount(account);
		}
		
		if(transactionFromDb.getCategory().getId()!=transactionDto.getCategoryId()) {
			transactionFromDb.setCategory(category);
		}
		
		if(Double.compare(transactionFromDb.getAmount(), transactionDto.getAmount()) != 0) {
			transactionFromDb.setAmount(transactionDto.getAmount());
		}
		
		if(!transactionFromDb.getTitle().equals(transactionDto.getTitle())) {
			transactionFromDb.setTitle(transactionDto.getTitle());
		}
		
		if(!transactionFromDb.getType().equals(transactionDto.getType())) {
			transactionFromDb.setType(transactionDto.getType());
		}
		
		logger.info("update transaction completed");
		return transactionDao.save(transactionFromDb);
	}

	public void deleteTransactions(long transId) {
		transactionDao.deleteById(transId);
	}
}
