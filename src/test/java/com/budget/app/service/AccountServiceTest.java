package com.budget.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.budget.app.dao.AccountDao;
import com.budget.app.dao.UserDao;
import com.budget.app.entity.Account;
import com.budget.app.entity.User;
import com.budget.app.exception.DataNotFoundException;
import com.budget.app.model.AccountDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

        @Autowired
        private AccountService accountService;
        
        
        @MockBean private AccountDao accountDao;
        @MockBean private UserDao userDao;
        
        @Before
        public void initiate() {
            System.out.println("Initiating the before steps");
        }
        
        @Test
  	  	void addAccount() throws Exception {
        	Mockito.when(accountDao.save(any(Account.class))).thenReturn(getAccountObject()); 
            Mockito.when(userDao.findById(any(Long.class))).thenReturn(getUserObject()); 
        	Account addAccount = accountService.addAccount(getAccountDTOObject());
  		  	assertThat(addAccount.getId()).isEqualTo(200);
  	  	}
        
        @Test
  	  	void addAccountValidation() {
        	Mockito.when(accountDao.save(any(Account.class))).thenReturn(getAccountObject()); 
            Mockito.when(userDao.findById(any(Long.class))).thenThrow(DataNotFoundException.class); 
        	assertThrows(DataNotFoundException.class, () -> accountService.addAccount(getAccountDTOObject()));
        }
        
        @Test
  	  	void getAllAccountValidation() {
        	List<Account> list = new ArrayList<>();
        	list.add(getAccountObject());
        	Mockito.when(accountDao.findAllByUserIdAndStatus(any(Long.class), any(String.class))).thenReturn(list); 
        	List<Account> addAccount = accountService.getAllAccounts(200);
        	assertThat(addAccount.size()).isEqualTo(1);
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
    	
    	private AccountDTO getAccountDTOObject() {
    		AccountDTO account  = new AccountDTO();
    		account.setName("test");
    		account.setType("card");
    		account.setUserId(200);
    		return account;
    	}
}
