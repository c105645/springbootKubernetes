package com.stackroute.newz.util.exception;

public class NewsSourceAlreadyExists extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NewsSourceAlreadyExists(String message) {
		super(message);
	}
}
