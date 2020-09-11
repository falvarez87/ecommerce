package com.falvarez.marketplace.security;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.falvarez.marketplace.exception.ErrorItem;
import com.falvarez.marketplace.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
	private static final long serialVersionUID = -7858869558953243875L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		ErrorResponse errors = new ErrorResponse();
		ErrorItem error = new ErrorItem("AUT-002", authException.getMessage(), HttpStatus.UNAUTHORIZED.value(),
				request.getSession().getId());
		errors.addError(error);

		ObjectMapper mapper = new ObjectMapper();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getOutputStream().println(mapper.writeValueAsString(errors));
	}
}
