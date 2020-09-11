package com.falvarez.marketplace.service;

import javax.servlet.http.HttpServletRequest;

import com.falvarez.marketplace.model.User;

public interface IUserService {
	String signin(String username, String password, HttpServletRequest req);

	String signup(User user, HttpServletRequest req);

	void delete(String username);

	User search(String username, HttpServletRequest req);

	User whoami(HttpServletRequest req);

	String refresh(String username);
}
