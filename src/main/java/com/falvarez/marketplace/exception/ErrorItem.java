package com.falvarez.marketplace.exception;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ErrorItem {

	public ErrorItem(String code, String message, int status, String sesion_id) {
		super();
		this.code = code;
		this.message = message;
		this.status = status;
		this.sesion_id = sesion_id;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String code;
	private String message;
	private int status;
	private String sesion_id;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimestamp() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSesion_id() {
		return sesion_id;
	}

	public void setSesion_id(String sesion_id) {
		this.sesion_id = sesion_id;
	}

}