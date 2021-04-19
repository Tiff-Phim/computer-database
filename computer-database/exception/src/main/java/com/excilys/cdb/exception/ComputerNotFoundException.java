package com.excilys.cdb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Computer")
public class ComputerNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ComputerNotFoundException(String message) {
		super("Computer not found");
	}

}
