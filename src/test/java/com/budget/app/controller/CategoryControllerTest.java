package com.budget.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
import com.budget.app.dao.BudgetDao;
import com.budget.app.dao.UserDao;
import com.budget.app.entity.Category;
import com.budget.app.entity.User;
import com.budget.app.model.CategoryDTO;
import com.budget.app.service.CategoryService;
import com.budget.app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {

	@Autowired
	  private MockMvc mockMvc;
	  
	  @MockBean private BudgetDao budgetDao;
	  
	  @MockBean private CategoryService categoryService;
	  
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
	  void createCategoryTest() throws Exception {
		  when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(getCategoryObject().get()); 
		  MvcResult mvcResult = mockMvc.perform(post("/category")
				  	.contentType("application/json")
				  	.content(objectMapper.writeValueAsString(getCategoryDTO())))
				  	.andReturn();
		  String actualResponseBody = mvcResult.getResponse().getContentAsString();
		  assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
	  }
	  
	  @Test
	  void getCategoryTest() throws Exception {
		  List<Category> list = new ArrayList<Category>();
		  list.add(getCategoryObject().get());
		  when(categoryService.getAllCategory(any(Long.class))).thenReturn(list); 
		  MvcResult mvcResult = mockMvc.perform(get("/category/100")
				  	.contentType("application/json"))
				  	.andReturn();
		  assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
	  }
	  
	  @Test
	  void getAllCategoryTest() throws Exception {
		  Category category = getCategoryObject().get();
		  when(categoryService.getCategoryById(any(Long.class))).thenReturn(category); 
		  MvcResult mvcResult = mockMvc.perform(get("/category/100/100")
				  	.contentType("application/json"))
				  	.andReturn();
		  assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
	  }
	  
	  public CategoryDTO getCategoryDTO() {
			CategoryDTO dto = new CategoryDTO();
			dto.setDescription("desc");
			dto.setTitle("title");
			dto.setType("test");
			dto.setUserId(200);
			return dto;
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
		
	  private Optional<Category> getCategoryObject() {
			return Optional.of(new Category(getUserObject().get(), "title","desc", "test","test", new Date()));
		}
}
