package com.portfolio.blog.data.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.data.dto.JwtRequest;
import com.portfolio.blog.data.dto.UserMapper;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.data.service.AuthService;
import com.portfolio.blog.util.RetrunText;

@Service
public class AuthServiceImpl implements AuthService{

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ResponseEntity<?> createAuthenticationToken(JwtRequest authenticationRequest) throws Exception {

		UserEntity check = userRepository.findById(authenticationRequest.getId());
		if(check == null) {
			return new ResponseEntity<>(RetrunText.CHECK_ID.getValue(), HttpStatus.FORBIDDEN);
		}
		
		if(!check.getPassword().equals(authenticationRequest.getPassword())) {
			return new ResponseEntity<>(RetrunText.CHECK_PASSWORD.getValue(), HttpStatus.FORBIDDEN);
		}
		
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", check.getGrade());
		
		final String token = jwtTokenUtil.generateToken(authenticationRequest.getId(), claims);
		
		return new ResponseEntity<>(new UserMapper(check, token), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> signup(UserEntity userEntity) throws Exception {
		
		if(userEntity.getId() == null || userEntity.getId().equals("")) {
			return new ResponseEntity<>(RetrunText.CHECK_ID.getValue(), HttpStatus.FORBIDDEN);
		}
		
		if(userEntity.getPassword() == null || userEntity.getPassword().equals("")) {	
			return new ResponseEntity<>(RetrunText.CHECK_PASSWORD.getValue(), HttpStatus.FORBIDDEN);
		}
		
		if(userEntity.getNickname() == null || userEntity.getNickname().equals("")) {
			return new ResponseEntity<>(RetrunText.CHECK_NICKNAME, HttpStatus.FORBIDDEN);
		}
		
		UserEntity check = userRepository.findById(userEntity.getId());
		if(check != null) {
			return new ResponseEntity<>(RetrunText.ALREADY.getValue(), HttpStatus.FORBIDDEN);
		}
		
		Map<String, Object> claims = new HashMap<String, Object>();
		userEntity = new UserEntity(userEntity);
		claims.put("role", userEntity.getGrade());
		
		userRepository.save(userEntity);
		return new ResponseEntity<>(RetrunText.SIGN_SUCCESS.getValue(), HttpStatus.OK);
	}

}
