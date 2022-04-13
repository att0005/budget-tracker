package com.budget.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GloabalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(BudgetTrackerException.class)
	public final ResponseEntity<ApiError> handleJwtException(BudgetTrackerException ex, WebRequest req) {
		ApiError error = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), "");
		return new ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	public final ResponseEntity<ApiError> handleJwtException(DataNotFoundException ex, WebRequest req) {
		ApiError error = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), "");
		return new ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST);
	}

}