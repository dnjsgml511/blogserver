//package com.portfolio.blog.data.service.impl;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.server.ResponseStatusException;
//
//import com.portfolio.blog.config.security.JwtTokenUtil;
//import com.portfolio.blog.data.dto.UserMapper;
//import com.portfolio.blog.data.entitiy.UserEntity;
//import com.portfolio.blog.data.repository.UserRepository;
//import com.portfolio.blog.data.service.AuthService;
//import com.portfolio.blog.util.ReturnText;
//
//@Service
//public class AuthServiceImpl implements AuthService{
//
//	@Autowired
//	private JwtTokenUtil jwtTokenUtil;
//	
//	@Autowired
//	private UserRepository userRepository;
//	
//	@Override
//	public UserMapper createAuthenticationToken(UserEntity userEntity) throws Exception {
//		
//		UserEntity check = userRepository.findById(userEntity.getId());
//		if(check == null) {
//			throw new ResponseStatusException(HttpStatus.FORBIDDEN, ReturnText.CHECK_ID.getValue());
//		}
//		
//		if(check.getActive() == 0) {
//			throw new ResponseStatusException(HttpStatus.FORBIDDEN, ReturnText.CHECK_ACTIVE.getValue());
//		}
//		
//		if(!check.getPassword().equals(userEntity.getPassword())) {
//			throw new ResponseStatusException(HttpStatus.FORBIDDEN, ReturnText.CHECK_PASSWORD.getValue());
//		}
//		
//		Map<String, Object> claims = new HashMap<String, Object>();
//		claims.put("role", check.getGrade());
//		
//		final String token = jwtTokenUtil.generateToken(userEntity.getId(), claims);
//		
//		return new UserMapper(check, token);
//	}
//
//	@Override
//	@Transactional
//	public String signup(UserEntity userEntity) throws Exception {
//		
//		if(userEntity.getId() == null || userEntity.getId().equals("")) {
//			throw new ResponseStatusException(HttpStatus.FORBIDDEN, ReturnText.CHECK_ID.getValue());
//		}
//		
//		if(userEntity.getPassword() == null || userEntity.getPassword().equals("")) {	
//			throw new ResponseStatusException(HttpStatus.FORBIDDEN, ReturnText.CHECK_PASSWORD.getValue());
//		}
//		
//		if(userEntity.getNickname() == null || userEntity.getNickname().equals("")) {
//			throw new ResponseStatusException(HttpStatus.FORBIDDEN, ReturnText.CHECK_NICKNAME.getValue());
//		}
//		
//		UserEntity check = userRepository.findById(userEntity.getId());
//		if(check != null) {
//			throw new ResponseStatusException(HttpStatus.FORBIDDEN, ReturnText.ALREADY_ID.getValue());
//		}
//		check = userRepository.findByNickname(userEntity.getNickname());
//		if(check != null) {
//			throw new ResponseStatusException(HttpStatus.FORBIDDEN, ReturnText.ALREADY_NICKNAME.getValue());
//		}
//		
//		Map<String, Object> claims = new HashMap<String, Object>();
//		userEntity = new UserEntity(userEntity);
//		claims.put("role", userEntity.getGrade());
//		
//		userRepository.save(userEntity);
//		
//		return ReturnText.SIGN_SUCCESS.getValue();
//	}
//
//	@Override
//	public String useractive(UserEntity userEntity) throws Exception {
//		UserEntity user = userRepository.findById(userEntity.getId());
//		user.setActive(1);
//		userRepository.save(user);
//		
//		return user.getNickname() + ReturnText.USER_ACTIVE.getValue();
//	}
//	
//	@Override
//	public String userblock(UserEntity userEntity) throws Exception {
//		UserEntity user = userRepository.findById(userEntity.getId());
//		user.setActive(0);
//		userRepository.save(user);
//		
//		return user.getNickname() + ReturnText.USER_BLOCK.getValue();
//	}
//
//}
