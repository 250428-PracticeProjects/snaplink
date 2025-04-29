package com.revature.exceptions.custom.users;

public class InvalidPasswordException extends RuntimeException {
	public InvalidPasswordException ( String message ) {
		super(message);
	}
}