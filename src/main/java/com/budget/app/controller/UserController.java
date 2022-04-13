package com.budget.app.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.budget.app.config.JwtTokenUtil;
import com.budget.app.entity.User;
import com.budget.app.exception.BudgetTrackerException;
import com.budget.app.model.JwtRequest;
import com.budget.app.model.JwtResponse;
import com.budget.app.model.UserDTO;
import com.budget.app.service.UserService;

@Controller
@CrossOrigin
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userDetailsService;

	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		
		logger.info("Usercontroller: login API triggered - username: {}",authenticationRequest.getUsername());
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		UserDetails userFromDb = null;
		try {
			userFromDb = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		} catch(Exception e) {
			throw new BadCredentialsException("Bad Credentials");
		}
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(userFromDb.getUsername(), userFromDb.getPassword(),
				new ArrayList<>());
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		User user = userDetailsService.getUserByUsername(authenticationRequest.getUsername());
		logger.info("Usercontroller: login API completed - username: {}",authenticationRequest.getUsername());
		
		return ResponseEntity.ok(new JwtResponse(token, user.getId(), user.getFirstName(), user.getLastName(), user.getUsername()));
	}
	
	@PostMapping(value = "/register")
	public String saveUser(@ModelAttribute("user") UserDTO user) throws Exception {
		logger.info("Usercontroller: register API triggered - username: {}",user.getUsername());
		userDetailsService.save(user);
		return "redirect:/register?success";
	}
	
	@GetMapping(value = {"/","/login"})
	public String showLoginForm() {
		return "/login";
	}
	
	@GetMapping(value = "/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new UserDTO());
		return "register";
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
			throw new BudgetTrackerException("Bad Credentials");
		}
	}
}