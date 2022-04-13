package com.budget.app.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final long userId;
	private final String firstName;
	private final String lastName;
	private final String email;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getJwttoken() {
		return jwttoken;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}
	
	public long getUserId() {
		return userId;
	}

	public JwtResponse(String jwttoken, long userId, String firstName, String lastName, String email) {
		super();
		this.userId=userId;
		this.jwttoken = jwttoken;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
}