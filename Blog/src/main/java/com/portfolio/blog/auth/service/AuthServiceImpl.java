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

		UserEntity check = userRepository.findById(authenticationRequest.getId());
		if(check == null) {
			return new ResponseEntity<>("아이디를 다시 입력해주세요", HttpStatus.FORBIDDEN);
		}
		
		if(!check.getPassword().equals(authenticationRequest.getPassword())) {
			return new ResponseEntity<>("비밀번호가 틀렸습니다", HttpStatus.FORBIDDEN);
		}
		
		final String token = jwtTokenUtil.generateToken(authenticationRequest.getId());
		return new ResponseEntity<>(token, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> signup(UserEntity userEntity) throws Exception {
		
		if(userEntity.getId() == null || userEntity.getId().equals("")) {
			return new ResponseEntity<>("아이디를 입력해주세요", HttpStatus.FORBIDDEN);
		}
		
		if(userEntity.getPassword() == null || userEntity.getPassword().equals("")) {
			return new ResponseEntity<>("비밀번호를 입력해주세요", HttpStatus.FORBIDDEN);
		}
		
		if(userEntity.getNickname() == null || userEntity.getNickname().equals("")) {
			return new ResponseEntity<>("닉네임을 입력해주세요", HttpStatus.FORBIDDEN);
		}
		
		UserEntity check = userRepository.findById(userEntity.getId());
		if(check != null) {
			return new ResponseEntity<>("이미 가입 된 아이디입니다", HttpStatus.FORBIDDEN);
		}
		
		userRepository.save(userEntity);
		return new ResponseEntity<>("회원가입이 완료되었습니다", HttpStatus.OK);
	}

}
