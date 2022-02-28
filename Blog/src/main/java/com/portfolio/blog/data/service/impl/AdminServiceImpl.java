package com.portfolio.blog.data.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.data.service.AdminService;
import com.portfolio.blog.util.ReturnText;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Override
	public List<UserEntity> userlist(UserEntity userEntity) throws Exception {
		
		if (userEntity.getId() != null && !userEntity.getId().equals("")) {
			return userRepository.findByIdLike("%" + userEntity.getId() + "%");
		} else if (userEntity.getNickname() != null && !userEntity.getNickname().equals("")) {
			return userRepository.findByNicknameLike("%" + userEntity.getNickname() + "%");
		} else if (userEntity.getGrade() != null && !userEntity.getGrade().equals("")) {
			return userRepository.findByGrade(userEntity.getGrade());
		}
		return userRepository.findAll();
	}

	@Override
	public ResponseEntity<?> userselect(UserEntity userEntity) throws Exception {
		
		String id = userEntity.getId();
		id = id == null ? "" : id;
		
		if(id.equals("")) {
			return new ResponseEntity<>(ReturnText.SELECT_FAILE, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		UserEntity user = userRepository.findById(id);
		if(user == null) {
			return new ResponseEntity<>(ReturnText.SELECT_FAILE, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		// 변경한 계정 토큰 생성
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", Role.ROLE_USER);
		String token = jwtTokenUtil.generateToken(id, claims);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("data", user);
		map.put("usertoken", token);
		
		return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
	}

}
