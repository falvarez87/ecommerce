package com.falvarez.marketplace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.io.IOException;

@RestControllerAdvice
public class ApiExceptionHandler {

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handle(ConstraintViolationException e) {
		ErrorResponse errors = new ErrorResponse();
		for (ConstraintViolation violation : e.getConstraintViolations()) {
			ErrorItem error = new ErrorItem(violation.getMessageTemplate(), violation.getMessage(),
					HttpStatus.BAD_REQUEST.value(), null);
			errors.addError(error);
		}
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(HttpServletResponse res, CustomException ex)
			throws IOException {
		ErrorResponse errors = new ErrorResponse();
		ErrorItem error = new ErrorItem(ex.getCode(), ex.getMessage(), ex.getHttpStatus().value(), ex.getSesion_id());
		errors.addError(error);

		return new ResponseEntity<>(errors, ex.getHttpStatus());
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handle(ResourceNotFoundException e, HttpServletRequest req) {
		ErrorResponse errors = new ErrorResponse();
		ErrorItem error = new ErrorItem("RNF-001", e.getMessage(), HttpStatus.NOT_FOUND.value(),
				req.getSession().getId());
		errors.addError(error);
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(HttpServletResponse res, HttpServletRequest req)
			throws IOException {
		ErrorResponse errors = new ErrorResponse();
		ErrorItem error = new ErrorItem("EXC-001", "Something went wrong", HttpStatus.BAD_REQUEST.value(),
				req.getSession().getId());
		errors.addError(error);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

}
