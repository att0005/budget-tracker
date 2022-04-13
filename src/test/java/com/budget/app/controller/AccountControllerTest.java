package com.budget.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.budget.app.config.JwtAuthenticationEntryPoint;
import com.budget.app.config.JwtRequestFilter;
import com.budget.app.config.JwtTokenUtil;
import com.budget.app.dao.AccountDao;
import com.budget.app.dao.UserDao;
import com.budget.app.entity.Account;
import com.budget.app.entity.User;
import com.budget.app.model.AccountDTO;
import com.budget.app.service.AccountService;
import com.budget.app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AccountControllerTest {
	@Autowired
	  private MockMvc mockMvc;
	  
	  @MockBean private AccountDao accountDao;
	  
	  @MockBean private AccountService accountService;
	  
	  @MockBean private UserService userService;
	  
	  @MockBean private JwtTokenUtil tokenUtils;
	  
	  @MockBean
		private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	  @MockBean
		private JwtRequestFilter jwtRequestFilter;
	  
	  @MockBean private UserDao userDao;

	  @Autowired
	  private ObjectMapper objectMapper;
	  
	  @Test
	  void validateSaveAccount() throws Exception {
		  Account accountObject = getAccountObject();
		  when(accountDao.save(any(Account.class))).thenReturn(getAccountObject()); 
		  when(userDao.findById(any(Long.class))).thenReturn(getUserObject()); 
		  when(accountService.addAccount(any(AccountDTO.class))).thenReturn(accountObject);
		  MvcResult mvcResult = mockMvc.perform(post("/account")
				  	.contentType("application/json")
				  	.content(objectMapper.writeValueAsString(getAccountDTOObject())))
				  	.andReturn();
		  String actualResponseBody = mvcResult.getResponse().getContentAsString();
		  assertThat(actualResponseBody).isEqualTo(objectMapper.writeValueAsString(accountObject));
	  }
	  
	  @Test
	  void validateGetAllAccount() throws Exception {
		  Account accountObject = getAccountObject();
		  List<Account> list = new ArrayList<Account>();
		  list.add(accountObject);
		  when(accountService.getAllAccounts(any(Long.class))).thenReturn(list);
		  MvcResult mvcResult = mockMvc.perform(get("/account/200")
				  	.contentType("application/json"))
				  	.andExpect(jsonPath("$[0].name", is("test")))
				  	.andReturn();
		  String actualResponseBody = mvcResult.getResponse().getContentAsString();
		  assertThat(actualResponseBody).isEqualTo(objectMapper.writeValueAsString(list));
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
