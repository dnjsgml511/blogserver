package com.portfolio.blog.data.service;

import org.springframework.http.ResponseEntity;

import com.portfolio.blog.data.entitiy.UserEntity;

public interface AuthService {
	public ResponseEntity<?> createAuthenticationToken(UserEntity userEntity) throws Exception;
	public ResponseEntity<?> signup(UserEntity userEntity) throws Exception;
	public ResponseEntity<?> useractive(UserEntity userEntity) throws Exception;
	public ResponseEntity<?> userblock(UserEntity userEntity) throws Exception;
}
