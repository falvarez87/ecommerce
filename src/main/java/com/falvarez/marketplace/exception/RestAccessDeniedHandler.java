package com.falvarez.marketplace.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AccessDeniedException e) throws IOException, ServletException {

		ErrorResponse errors = new ErrorResponse();
		ErrorItem error = new ErrorItem("AUT-001", e.getMessage(), HttpStatus.FORBIDDEN.value(),
				httpServletRequest.getSession().getId());
		errors.addError(error);

		ObjectMapper mapper = new ObjectMapper();
		httpServletResponse.setContentType("application/json");
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
		httpServletResponse.getOutputStream().println(mapper.writeValueAsString(errors));
	}
}
