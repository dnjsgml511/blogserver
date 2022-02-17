package com.portfolio.blog.auth.service;

import org.springframework.http.ResponseEntity;

import com.portfolio.blog.auth.dto.JwtRequest;

public interface AuthService {
	public ResponseEntity<?> createAuthenticationToken(JwtRequest authenticationRequest) throws Exception;
}
