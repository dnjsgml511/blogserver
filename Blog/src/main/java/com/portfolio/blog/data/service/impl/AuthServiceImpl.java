package com.portfolio.blog.data.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.data.dto.UserMapper;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.data.service.AuthService;
import com.portfolio.blog.util.ReturnText;

@Service
public class AuthServiceImpl implements AuthService{

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ResponseEntity<?> createAuthenticationToken(UserEntity userEntity) throws Exception {
		
		UserEntity check = userRepository.findById(userEntity.getId());
		if(check == null) {
			return new ResponseEntity<>(ReturnText.CHECK_ID.getValue(), HttpStatus.FORBIDDEN);
		}
		
		if(check.getActive() == 0) {
			return new ResponseEntity<>(ReturnText.CHECK_ACTIVE.getValue(), HttpStatus.FORBIDDEN);
		}
		
		if(!check.getPassword().equals(userEntity.getPassword())) {
			return new ResponseEntity<>(ReturnText.CHECK_PASSWORD.getValue(), HttpStatus.FORBIDDEN);
		}
		
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", check.getGrade());
		
		final String token = jwtTokenUtil.generateToken(userEntity.getId(), claims);
		
		return new ResponseEntity<>(new UserMapper(check, token), HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> signup(UserEntity userEntity) throws Exception {
		
		if(userEntity.getId() == null || userEntity.getId().equals("")) {
			return new ResponseEntity<>(ReturnText.CHECK_ID.getValue(), HttpStatus.FORBIDDEN);
		}
		
		if(userEntity.getPassword() == null || userEntity.getPassword().equals("")) {	
			return new ResponseEntity<>(ReturnText.CHECK_PASSWORD.getValue(), HttpStatus.FORBIDDEN);
		}
		
		if(userEntity.getNickname() == null || userEntity.getNickname().equals("")) {
			return new ResponseEntity<>(ReturnText.CHECK_NICKNAME, HttpStatus.FORBIDDEN);
		}
		
		UserEntity check = userRepository.findById(userEntity.getId());
		if(check != null) {
			return new ResponseEntity<>(ReturnText.ALREADY.getValue(), HttpStatus.FORBIDDEN);
		}
		
		Map<String, Object> claims = new HashMap<String, Object>();
		userEntity = new UserEntity(userEntity);
		claims.put("role", userEntity.getGrade());
		
		userRepository.save(userEntity);
		
		return new ResponseEntity<>(ReturnText.SIGN_SUCCESS.getValue(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> useractive(UserEntity userEntity) throws Exception {
		UserEntity user = userRepository.findById(userEntity.getId());
		user.setActive(1);
		userRepository.save(user);
		
		return new ResponseEntity<>(user.getNickname() + ReturnText.USER_ACTIVE.getValue(), HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> userblock(UserEntity userEntity) throws Exception {
		UserEntity user = userRepository.findById(userEntity.getId());
		user.setActive(0);
		userRepository.save(user);
		
		return new ResponseEntity<>(user.getNickname() + ReturnText.USER_BLOCK.getValue(), HttpStatus.OK);
	}

}
