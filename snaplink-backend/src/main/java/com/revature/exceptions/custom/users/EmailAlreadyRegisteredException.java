package com.revature.exceptions.custom.users;

public class EmailAlreadyRegisteredException extends RuntimeException {
	public EmailAlreadyRegisteredException ( String message ) {
		super(message);
	}
}