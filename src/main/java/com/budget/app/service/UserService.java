package com.budget.app.service;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.budget.app.dao.UserDao;
import com.budget.app.entity.User;
import com.budget.app.model.UserDTO;

@Service
public class UserService implements UserDetailsService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	public User getUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Check the username is already exist in db : {}",username);
		User user = userDao.findByUsername(username);
		if (user == null) {
			logger.error("Username is not found in db : {}",username);
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return user;
	}
	
	public User save(UserDTO user) {
		logger.info("Creating new user in db : {}",user.getUsername());
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setMobile(user.getMobile());
		newUser.setCreatedDate(new Date());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userDao.save(newUser);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
}