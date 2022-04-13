package com.budget.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.budget.app.dao.UserDao;
import com.budget.app.entity.User;
import com.budget.app.model.UserDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	UserService userService;

	@MockBean private UserDao userDao;
	
	@Test
	void testgetUserByUsername() {
		Mockito.when(userDao.findByUsername(any(String.class))).thenReturn(getUserObject().get()); 
		assertThat(userService.getUserByUsername("test@test.com").getUsername()).isEqualTo("test@test.com");
	}
	
	@Test
  	void addgetUserByUsernameValidation() throws Exception {
		Mockito.when(userDao.findByUsername(any(String.class))).thenReturn(null); 
		assertThrows(UsernameNotFoundException.class, () -> userService.getUserByUsername("test@test.com"));
    }
	
	@Test
  	void saveUserTest() throws Exception {
		Mockito.when(userDao.save(any(User.class))).thenReturn(getUserObject().get()); 
		assertThat(userService.save(getUserDTO()).getUsername()).isEqualTo("test@test.com");
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
	
	private UserDTO getUserDTO() {
		UserDTO user  = new UserDTO();
		user.setFirstName("test");
		user.setLastName("test");
		user.setUsername("test@test.com");
		user.setMobile("9999999999");
		user.setPassword("test");
		return user;
	}
	
}
