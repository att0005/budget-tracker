package com.budget.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.budget.app.dao.AccountDao;
import com.budget.app.dao.BudgetDao;
import com.budget.app.dao.CategoryDao;
import com.budget.app.dao.TransactionDao;
import com.budget.app.dao.UserDao;
import com.budget.app.entity.Account;
import com.budget.app.entity.Budget;
import com.budget.app.entity.Category;
import com.budget.app.entity.Transaction;
import com.budget.app.entity.User;
import com.budget.app.exception.DataNotFoundException;
import com.budget.app.model.BudgetDTO;
import com.budget.app.model.CategoryDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

	@Autowired
    private CategoryService categoryService;
    
    @MockBean private AccountDao accountDao;
    @MockBean private UserDao userDao;
    @MockBean private CategoryDao categoryDao;
    @MockBean private BudgetDao budgetDao;
    @MockBean private TransactionDao transactionDao;
    
    @Test
  	void addCategory() throws Exception {
		Mockito.when(categoryDao.findById(any(Long.class))).thenReturn(getCategoryObject()); 
	    Mockito.when(userDao.findById(any(Long.class))).thenReturn(getUserObject()); 
	    Mockito.when(categoryDao.save(any(Category.class))).thenReturn(getCategoryObject().get()); 
		Category category = categoryService.createCategory(getCategoryDTO());
		assertThat(category.getTitle()).isEqualTo("title");
    }
    
    @Test
  	void addCategoryValidation() throws Exception {
    	Mockito.when(userDao.findById(any(Long.class))).thenThrow(DataNotFoundException.class); 
    	assertThrows(DataNotFoundException.class, () -> categoryService.createCategory(getCategoryDTO())); 
    }
    
    @Test
  	void getAllCategory() throws Exception {
    	List<Category> cList = new ArrayList<>();
    	cList.add(getCategoryObject().get());
		Mockito.when(categoryDao.findAllByUserIdAndStatus(any(Long.class), any(String.class))).thenReturn(cList); 
		List<Category> category = categoryService.getAllCategory(200);
		assertThat(category.size()).isEqualTo(1);
    }
    
    private Optional<User> getUserObject() {
		User user  = new User();
		user.setId(200);
		user.setFirstName("test");
		user.setLastName("test");
		user.setUsername("test@test.com");
		user.setMobile("9999999999");
		user.setCreatedDate(new Date());
		user.setPassword("test");
		return Optional.of(user);
	}
	
	private Account getAccountObject() {
		Account account  = new Account();
		account.setId(200);
		account.setName("test");
		account.setStatus("active");
		account.setType("card");
		account.setDate(new Date());
		account.setUser(getUserObject().get());
		return account;
	}
	
	private Optional<Category> getCategoryObject() {
		return Optional.of(new Category(getUserObject().get(), "title","desc", "test","test", new Date()));
	}
	
	private BudgetDTO getBudgetDTOObject() {
		BudgetDTO budgetDto  = new BudgetDTO();
		budgetDto.setCategoryId(1);
		budgetDto.setEstimatedExpense(10.00);
		budgetDto.setEstimatedIncome(200.00);
		budgetDto.setMonth("April");
		budgetDto.setUserId(200);
		budgetDto.setYear("2022");
		return budgetDto;
	}
	
	private Budget getBudgetObject() {
		Budget budget  = new Budget();
		budget.setId(200);
		budget.setCategory(getCategoryObject().get());
		budget.setEstimatedExpense(10.00);
		budget.setEstimatedIncome(200.00);
		budget.setMonth("April");
		budget.setYear("2022");
		budget.setUser(getUserObject().get());
		return budget;
	}
	
	public CategoryDTO getCategoryDTO() {
		CategoryDTO dto = new CategoryDTO();
		dto.setDescription("desc");
		dto.setTitle("title");
		dto.setType("test");
		dto.setUserId(200);
		return dto;
	}
	
	private Transaction getTransactionObject() {
		return new Transaction(getUserObject().get(), getAccountObject(), 
				getCategoryObject().get(), "title","desc", 10.0, "4-2022");
	}
}
