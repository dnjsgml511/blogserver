package com.portfolio.blog.data.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.blog.data.dto.JwtRequest;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.service.AuthService;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService service;

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,
			HttpSession session) throws Exception {
		return service.createAuthenticationToken(authenticationRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserEntity userEntity) throws Exception {
		return service.signup(userEntity);
	}

}
