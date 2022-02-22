package com.portfolio.blog.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.portfolio.blog.auth.dto.JwtRequest;
import com.portfolio.blog.auth.entity.UserEntity;
import com.portfolio.blog.auth.repositroy.UserRepository;
import com.portfolio.blog.config.security.JwtTokenUtil;

@Service
public class AuthServiceImpl implements AuthService{

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ResponseEntity<?> createAuthenticationToken(JwtRequest authenticationRequest) throws Exception {
		final String token = jwtTokenUtil.generateToken(authenticationRequest.getId());
		return new ResponseEntity<>(token, HttpStatus.OK);
	}

	@Override
	public HttpStatus signup(UserEntity userEntity) throws Exception {
		userRepository.save(userEntity);
		
		return HttpStatus.OK;
	}

}
