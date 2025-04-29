package com.revature.exceptions.handlers;

import com.revature.utils.ExceptionResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(UserExceptionHandler.class);

	public ResponseEntity<Map<String, String>> handleException(Exception e) {
		logger.error("Unexpected error: {}", e.getMessage(), e);
		return ExceptionResponseBuilder.buildResponse(
						"An unexpected error occurred: " + e.getMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handleIllegalArgumentException (IllegalArgumentException e){
		logger.error("Unexpected error: {}", e.getMessage(), e);
		return ExceptionResponseBuilder.buildResponse(
						e.getMessage(),
						HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Map<String, String>> handleNullPointerException(NullPointerException e) {
		logger.error("NullPointerException: {}", e.getMessage(), e);
		return ExceptionResponseBuilder.buildResponse(
						"Null pointer exception occurred: " + e.getMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
	}

}