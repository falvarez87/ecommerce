package com.falvarez.marketplace.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.falvarez.marketplace.exception.CustomException;
import com.falvarez.marketplace.model.Role;
import com.falvarez.marketplace.model.User;
import com.falvarez.marketplace.repository.UserRepository;
import com.falvarez.marketplace.security.JwtTokenProvider;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	@Transactional
	public String signin(String username, String password, HttpServletRequest req) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
		} catch (AuthenticationException e) {
			throw new CustomException("USR-001", "Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY,
					req.getSession().getId());
		}
	}

	@Override
	@Transactional
	public String signup(User user, HttpServletRequest req) {
		if (!userRepository.existsByUsername(user.getUsername())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			List<Role> roles = new ArrayList<Role>();
			roles.add(Role.ROLE_CLIENT);
			user.setRoles(roles);
			userRepository.save(user);
			return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
		} else {
			throw new CustomException("USR-002", "Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY,
					req != null ? req.getSession().getId() : null);
		}
	}

	@Override
	@Transactional
	public void delete(String username) {
		userRepository.deleteByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public User search(String username, HttpServletRequest req) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new CustomException("USR-003", "The user doesn't exist", HttpStatus.NOT_FOUND,
					req.getSession().getId());
		}
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public User whoami(HttpServletRequest req) {
		return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	}

	@Override
	@Transactional(readOnly = true)
	public String refresh(String username) {
		return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
	}
}
