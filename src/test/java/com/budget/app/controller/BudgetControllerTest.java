package com.budget.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
import com.budget.app.dao.BudgetDao;
import com.budget.app.dao.UserDao;
import com.budget.app.entity.Budget;
import com.budget.app.entity.Category;
import com.budget.app.entity.User;
import com.budget.app.model.BudgetDTO;
import com.budget.app.model.BudgetTableResDTO;
import com.budget.app.service.BudgetService;
import com.budget.app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BudgetController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BudgetControllerTest {
	@Autowired
	  private MockMvc mockMvc;
	  
	  @MockBean private BudgetDao budgetDao;
	  
	  @MockBean private BudgetService budgetService;
	  
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
	  void validateAddBudget() throws Exception {
		  Budget budgetObject = getBudgetObject();
		  when(budgetService.saveBudget(any(BudgetDTO.class))).thenReturn(budgetObject); 
		  MvcResult mvcResult = mockMvc.perform(post("/budget")
				  	.contentType("application/json")
				  	.content(objectMapper.writeValueAsString(getBudgetDTOObject())))
				  	.andReturn();
		  String actualResponseBody = mvcResult.getResponse().getContentAsString();
		  assertThat(actualResponseBody).isEqualTo(objectMapper.writeValueAsString(budgetObject));
	  }
	  
	  @Test
	  void validateGetAllAccount() throws Exception {
		  BudgetTableResDTO budgetObject = getBudgetTableResDTO();
		  when(budgetService.getAllBudgets(any(Long.class), any(String.class), any(String.class))).thenReturn(budgetObject);
		  MvcResult mvcResult = mockMvc.perform(get("/budget/1/April/2022")
				  	.contentType("application/json"))
				  	.andReturn();
		  String actualResponseBody = mvcResult.getResponse().getContentAsString();
		  assertThat(actualResponseBody).isEqualTo(objectMapper.writeValueAsString(budgetObject));
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
	
	private Budget getBudgetObject() {
		Budget budget  = new Budget();
		budget.setId(200);
		budget.setCategory(getCategoryObject());
		budget.setEstimatedExpense(10.00);
		budget.setEstimatedIncome(200.00);
		budget.setMonth("April");
		budget.setYear("2022");
		budget.setUser(getUserObject().get());
		return budget;
	}
	

	private Category getCategoryObject() {
		return new Category(getUserObject().get(), "title","desc", "test","test", new Date());
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
	
	private BudgetTableResDTO getBudgetTableResDTO() {
		BudgetTableResDTO res = new BudgetTableResDTO();
		res.setBudgetTableResponse(new ArrayList());
		res.setEarnPercentage("50");
		res.setSpentPercentage("50");
		res.setTotalEarn(10.00);
		res.setTotalSpent(10.00);
		res.setTotalEstiEarn(10.00);
		res.setTotalEstiSpent(10.00);
		return res;
	}
}
