package com.revature.exceptions.handlers;

import com.revature.exceptions.custom.users.*;
import com.revature.utils.ExceptionResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class UserExceptionHandler {
	@ExceptionHandler(EmailAlreadyRegisteredException.class)
	public ResponseEntity<Map<String, String>> handleEmailAlreadyRegisteredException( EmailAlreadyRegisteredException e ) {
		return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleIdNotFoundException( IdNotFoundException e ) {
		return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidEmaiException.class)
	public ResponseEntity<Map<String, String>> handleInvalidEmaiException( InvalidEmaiException e ) {
		return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<Map<String, String>> handleInvalidPasswordException( InvalidPasswordException e ) {
		return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	@ExceptionHandler(UnauthenticatedException.class)
	public ResponseEntity<Map<String, String>> handleUnauthenticatedException( UnauthenticatedException e ) {
		return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}
}