package com.falvarez.marketplace.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;
	private final HttpStatus httpStatus;
	private final String code;
	private String sesion_id;

	public String getSesion_id() {
		return sesion_id;
	}

	public CustomException(String code, String message, HttpStatus httpStatus, String sesion_id) {
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
		this.sesion_id = sesion_id;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getCode() {
		return code;
	}

}