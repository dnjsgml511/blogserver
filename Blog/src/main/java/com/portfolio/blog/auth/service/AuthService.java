package com.portfolio.blog.auth.service;

import org.springframework.http.ResponseEntity;

import com.portfolio.blog.auth.dto.JwtRequest;
import com.portfolio.blog.auth.entity.UserEntity;

public interface AuthService {
	public ResponseEntity<?> createAuthenticationToken(JwtRequest authenticationRequest) throws Exception;
	public ResponseEntity<?> signup(UserEntity userEntity) throws Exception;
}
