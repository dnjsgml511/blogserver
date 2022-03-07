package com.portfolio.blog.data.service.impl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.dto.auth.SigninResponse;
import com.portfolio.blog.data.dto.auth.SignupResponse;
import com.portfolio.blog.data.dto.auth.UserControllResponse;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.data.service.AuthService;
import com.portfolio.blog.util.ReturnText;

@Service
public class AuthServiceImpl implements AuthService{

	@Autowired UserRepository userRepository;
	@Autowired JwtTokenUtil jwtTokenUtil;
	
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
	public String userControll(UserControllResponse response, boolean adminCheck) throws Exception {

		// 아이디 체크
		if (response.getId() == null || response.getId().equals("") ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_NICKNAME.getValue());
		}
		
		UserEntity user = userRepository.findById(response.getId());
		if(user == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReturnText.CHECK_NICKNAME.getValue());
		}
		
		
		// 활성화 변경 있을 경우 수정
		if(response.getActive() != null) {
			user.setActive(response.getActive());
		}

		// 권한 변경 있을 경우 수정
		if(response.getGrade() != null) {
			if(!adminCheck) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, ReturnText.NOT_HAVE_GRADE.getValue());
			}
			user.setGrade(response.getGrade());
		}
		
		userRepository.save(user);
		return user.getNickname() + ReturnText.USER_UPDATE.getValue();
	}

	@Override
	public HashMap<String, Object> signin(SigninResponse response, HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		UserEntity user = userRepository.findById(response.getId());
		
		// 아이디 체크
		if(user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_ID.getValue());
		}
		
		// 비밀번호 체크
		if(!user.getPassword().equals(response.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_PASSWORD.getValue());
		}
		
		// 활성화 체크
		if(user.getActive() == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_ACTIVE.getValue());
		}
		
		String token = null;
		if(user.getGrade().equals(Role.ROLE_ADMIN)) {
			token = jwtTokenUtil.createAdmintoken(response.getId());
		}else if(user.getGrade().equals(Role.ROLE_MANAGER)) {
			token = jwtTokenUtil.createManagertoken(response.getId());
		}else if(user.getGrade().equals(Role.ROLE_USER)) {
			token = jwtTokenUtil.createUsertoken(response.getId());
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.NOT_HAVE_GRADE.getValue());
		}
		
		map.put("user", user);
		map.put("token", token);
		
		return map;
	}
}
