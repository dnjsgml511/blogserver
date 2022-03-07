package com.portfolio.blog.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.portfolio.blog.data.dto.SignupResponse;
import com.portfolio.blog.data.dto.UserResponse;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.data.service.AuthService;
import com.portfolio.blog.util.ReturnText;

@Service
public class AuthServiceImpl implements AuthService{

	@Autowired UserRepository userRepository;
	
	@Override
	public String signup(SignupResponse response) throws Exception {
		
		// 아이디 값 체크
		if (response.getId() == null || response.getId().equals("") ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_ID.getValue());
		}
		// 비밀번호 값 체크
		if (response.getPassword() == null || response.getPassword().equals("") ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_PASSWORD.getValue());
		}
		// 닉네임 값 체크
		if (response.getNickname() == null || response.getNickname().equals("") ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_NICKNAME.getValue());
		}
		
		// 아이디 길이 체크
		if (response.getId().length() < 4 || response.getId().length() > 20) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, ReturnText.CHECK_ID_LENGTH.getValue());
		}
		// 비밀번호 길이 체크
		if (response.getPassword().length() < 4 || response.getPassword().length() > 20) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, ReturnText.CHECK_PASSWORD_LENGTH.getValue());
		}
		// 닉네임 길이 체크
		if (response.getNickname().length() < 4 || response.getNickname().length() > 20) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, ReturnText.CHECK_NICKNAME_LENGTH.getValue());
		}
		
		// 아이디 중복 체크
		UserEntity userCheck = userRepository.findById(response.getId());
		if(userCheck != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ReturnText.ALREADY_ID.getValue());
		}
		
		// 회원가입
		userRepository.save(new UserEntity(response));
		return ReturnText.SIGN_SUCCESS.getValue();
	}

	@Override
	public String signin(UserResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String accessUser(String num) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String blockUser(String num) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
