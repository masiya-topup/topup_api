package com.masiya.topup.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class GenericTopupException extends WebApplicationException {

	private static final long serialVersionUID = 7296601794665286880L;

	public GenericTopupException() {
		super("Generic Error", Status.BAD_REQUEST);
	}

	public GenericTopupException(String message) {
		super(message, Status.BAD_REQUEST);
	}
	
	public GenericTopupException(String message, Status sts) {
		super(message, sts);
	}
}
