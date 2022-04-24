package com.budget.app.service;

import java.text.DecimalFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budget.app.constants.AppContants;
import com.budget.app.dao.BudgetDao;
import com.budget.app.dao.CategoryDao;
import com.budget.app.dao.TransactionDao;
import com.budget.app.dao.UserDao;
import com.budget.app.entity.Budget;
import com.budget.app.entity.Category;
import com.budget.app.entity.Transaction;
import com.budget.app.entity.User;
import com.budget.app.exception.DataNotFoundException;
import com.budget.app.model.BudgetDTO;
import com.budget.app.model.BudgetTableResDTO;
import com.budget.app.model.BudgetTableResponseDto;

@Service
public class BudgetService {
private static final Logger logger = LoggerFactory.getLogger(BudgetService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private BudgetDao budgetDao;
	
	@Autowired
	private TransactionDao transactionDao;
	
	public Budget saveBudget(BudgetDTO budgetDto) throws DataNotFoundException {
		logger.info("Save budget triggered");
		Optional<User> userOptional = userDao.findById(budgetDto.getUserId());
		User user = userOptional.isPresent() ? userOptional.get() : userOptional.orElseThrow(DataNotFoundException::new);
		Optional<Category> categoryOptional = categoryDao.findById(budgetDto.getCategoryId());
		Category category = categoryOptional.isPresent() ? categoryOptional.get() : categoryOptional.orElseThrow(DataNotFoundException::new);
		logger.info("save budget for user : {}, with category : {}", user.getUsername(), category.getTitle());
		
		Budget budgetFromDB = budgetDao.findAllByUserIdAndCategoryIdAndMonthAndYear(budgetDto.getUserId(), budgetDto.getCategoryId(), budgetDto.getMonth(), budgetDto.getYear());
		
		if(budgetFromDB == null) {
			Budget budget = new Budget(user, category,budgetDto.getEstimatedExpense(),
					budgetDto.getEstimatedIncome(), budgetDto.getMonth(), budgetDto.getYear());
			logger.info("Save budget completed for user : {}", user.getUsername());
			return budgetDao.save(budget);
		} else {

			if(budgetDto.getEstimatedExpense() != null)
				budgetFromDB.setEstimatedExpense(Double.sum(budgetDto.getEstimatedExpense(), budgetFromDB.getEstimatedExpense()));
			if(budgetDto.getEstimatedIncome() != null)
				budgetFromDB.setEstimatedIncome(Double.sum(budgetDto.getEstimatedIncome(), budgetFromDB.getEstimatedIncome()));
			return budgetDao.save(budgetFromDB);
		}
	}
	
	
	
	public BudgetTableResDTO getAllBudgets(long userId, String month, String year) {
		logger.info("Get all budgets triggered");
		List<Budget> budgets = budgetDao.findAllByUserIdAndMonthAndYear(userId, month, year);
		List<BudgetTableResponseDto> arrList = new ArrayList<>();
		String sMonth  = "";
		if (Month.valueOf(month.toUpperCase()).getValue() < 10) {
		    sMonth = AppContants.ZERO+Month.valueOf(month.toUpperCase()).getValue();
		} else {
		    sMonth = AppContants.EMPTY+Month.valueOf(month.toUpperCase()).getValue();
		}
		String dateFormat = sMonth+AppContants.HYPHEN+Integer.parseInt(year);
		budgets.stream().forEach(each -> {
			BudgetTableResponseDto dto = new BudgetTableResponseDto();
			List<Transaction> transactions = transactionDao.findAllByUserIdAndCategoryId(userId, 
					each.getCategory().getId(), dateFormat);
			double total = 0.0;
			for(Transaction eachTran : transactions) {
				total = Double.sum(total, eachTran.getAmount());
			}
			if(each.getEstimatedExpense()==null) {
				dto.setPrice(total);
				dto.setType(AppContants.INCOME);
				dto.setEstimatedPrice(each.getEstimatedIncome());
			} else {
				dto.setPrice(total);
				dto.setType(AppContants.EXPENSE);
				dto.setEstimatedPrice(each.getEstimatedExpense());
			}
			dto.setBudgetId(each.getId());
			dto.setUserId(each.getUser().getId());
			dto.setCategoryId(each.getCategory().getId());
			dto.setCategoryName(each.getCategory().getTitle());
			dto.setMonth(each.getMonth());
			dto.setYear(each.getYear());
			arrList.add(dto);
		});
		
		return formBudgetResponseData(arrList);
	}

	public BudgetTableResDTO formBudgetResponseData(List<BudgetTableResponseDto> data) {
		 DecimalFormat df = new DecimalFormat(AppContants.DECIMAL_FORMAT);
		double totalSpent = 0.0;
		double totalEarn = 0.0;
		double totalEstiSpent = 0.0;
		double totalEstiEarn = 0.0;
		for(BudgetTableResponseDto each : data){
			if(each.getType().equals(AppContants.INCOME)) {
				totalEarn = Double.sum(totalEarn, each.getPrice());
				totalEstiEarn = Double.sum(totalEstiEarn, each.getEstimatedPrice());
			} else {
				totalSpent = Double.sum(totalSpent, each.getPrice());
				totalEstiSpent = Double.sum(totalEstiSpent, each.getEstimatedPrice());
			}
		}
		BudgetTableResDTO res = new BudgetTableResDTO();
		res.setBudgetTableResponse(data);
		res.setEarnPercentage(df.format((totalEarn  * 100 )/ totalEstiEarn));
		res.setSpentPercentage(df.format((totalSpent  * 100)/ totalEstiSpent));
		res.setTotalEarn(totalEarn);
		res.setTotalSpent(totalSpent);
		res.setTotalEstiEarn(totalEstiEarn);
		res.setTotalEstiSpent(totalEstiSpent);
		logger.info("Get all budgets completed");
		return res;
	}



	public Budget updateBudget(BudgetDTO budgetDto, long budgetId) {
		logger.info("Update budget triggered");
		Optional<User> userOptional = userDao.findById(budgetDto.getUserId());
		User user = userOptional.isPresent() ? userOptional.get() : userOptional.orElseThrow(DataNotFoundException::new);
		Optional<Category> categoryOptional = categoryDao.findById(budgetDto.getCategoryId());
		Category category = categoryOptional.isPresent() ? categoryOptional.get() : categoryOptional.orElseThrow(DataNotFoundException::new);
		Optional<Budget> budgetOptional = budgetDao.findById(budgetId);
		Budget budgetFromDB = budgetOptional.isPresent() ? budgetOptional.get() : budgetOptional.orElseThrow(DataNotFoundException::new);
		
		logger.info("Update budget for user : {}, with category : {}", user.getUsername(), category.getTitle());
		
		if(budgetFromDB.getCategory().getId() != budgetDto.getCategoryId()) {
			budgetFromDB.setCategory(category);
		} 
		
		if(!budgetFromDB.getMonth().equals(budgetDto.getMonth())) {
			budgetFromDB.setMonth(budgetDto.getMonth());
		} 
		
		if(!budgetFromDB.getYear().equals(budgetDto.getYear())) {
			budgetFromDB.setYear(budgetDto.getYear());
		} 
		
		if(budgetDto.getEstimatedExpense() != null) {
			budgetFromDB.setEstimatedExpense(budgetDto.getEstimatedExpense());
			budgetFromDB.setEstimatedIncome(null);
		}
		if(budgetDto.getEstimatedIncome() != null) {
			budgetFromDB.setEstimatedIncome(budgetDto.getEstimatedIncome());
			budgetFromDB.setEstimatedExpense(null);
		}
		
		return budgetDao.save(budgetFromDB);
	}

	public void deleteBudget(long budgetId) {
		budgetDao.deleteById(budgetId);
	}
	
	

}
