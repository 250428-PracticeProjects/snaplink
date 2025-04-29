package com.revature.exceptions.custom.users;

public class InvalidEmaiException extends RuntimeException {
	public InvalidEmaiException ( String message ) {
		super(message);
	}
}