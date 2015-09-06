package com.masiya.topup.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class InvalidTokenException extends WebApplicationException {

	private static final long serialVersionUID = 7296601794665286880L;

	public InvalidTokenException() {
		super("Invalid Token", Status.UNAUTHORIZED);
	}

	public InvalidTokenException(String message) {
		super(message, Status.UNAUTHORIZED);
	}
}
