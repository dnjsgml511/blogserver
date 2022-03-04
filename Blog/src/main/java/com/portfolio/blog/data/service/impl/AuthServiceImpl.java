package com.portfolio.blog.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.blog.data.dto.SignupResponse;
import com.portfolio.blog.data.dto.UserResponse;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.data.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

	@Autowired UserRepository userRepository;
	
	@Override
	public String signup(SignupResponse response) throws Exception {
		
		
		UserEntity save = userRepository.save(new UserEntity(response));
		System.out.println(save);
		
		return "";
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
