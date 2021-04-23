package com.excilys.cdb.webapp.api;

import java.nio.file.AccessDeniedException;

import javax.naming.AuthenticationException;

import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import com.excilys.cdb.exception.CompanyNotFoundException;
import com.excilys.cdb.exception.ComputerNotFoundException;
import com.excilys.cdb.exception.InvalidComputerException;
import com.excylis.cdb.error.ApiErrorResponse;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<?> handleInvalidInputException(InvalidInputException exception) {
		ApiErrorResponse response = new ApiErrorResponse("400", "Invalid input " + exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(InvalidComputerException.class)
	public ResponseEntity<?> handleInvalidComputerException(InvalidComputerException exception) {
		ApiErrorResponse response = new ApiErrorResponse("400", "Invalid computer " + exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<?> handleUnauthorizedException() {
		//TODO 401
		return null;
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception) {
		//TODO 403
		return null;
	}
	
	@ExceptionHandler(ComputerNotFoundException.class)
	public ResponseEntity<?> handleComputerNotFound(ComputerNotFoundException exception) {
		ApiErrorResponse response = new ApiErrorResponse("404", "No computer found with id " + exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(CompanyNotFoundException.class)
	public ResponseEntity<?> handleCompanyNotFound(CompanyNotFoundException exception) {
		ApiErrorResponse response = new ApiErrorResponse("404", "No company found with id " + exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException exception) {
		ApiErrorResponse response = new ApiErrorResponse("429", "Too many requests " + exception.getMessage());
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleServerException(Exception exception) {
		ApiErrorResponse response = new ApiErrorResponse("500", "Internal Server Error " + exception.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	
}
