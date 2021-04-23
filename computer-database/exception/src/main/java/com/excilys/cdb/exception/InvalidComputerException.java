package com.excilys.cdb.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid computer")
public class InvalidComputerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private List<String> errors = null;

	public InvalidComputerException() {
		super();
	}
	
	public InvalidComputerException(final Throwable cause) {
		super(cause);
	}
	
	public InvalidComputerException(final String message) {
		super(message);
	}
	
	public InvalidComputerException(final List<String> errors) {
		super();
		this.errors = errors;
	}
	
	public InvalidComputerException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
}
