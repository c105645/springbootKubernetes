package com.stackroute.user.util.exception;

public class UserIdAndPasswordMismatchException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public UserIdAndPasswordMismatchException(String message) {
        super(message);
    }
}
