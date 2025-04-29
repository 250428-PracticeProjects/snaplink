package com.revature.exceptions.custom.users;

public class UnauthenticatedException extends RuntimeException {
	public UnauthenticatedException ( String message ) {
		super(message);
	}
}