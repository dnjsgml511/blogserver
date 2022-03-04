package com.portfolio.blog.data.service;

import org.springframework.http.ResponseEntity;

import com.portfolio.blog.data.dto.UserMapper;
import com.portfolio.blog.data.entitiy.UserEntity;

public interface AuthService {
	public UserMapper createAuthenticationToken(UserEntity userEntity) throws Exception;
	public String signup(UserEntity userEntity) throws Exception;
	public String useractive(UserEntity userEntity) throws Exception;
	public String userblock(UserEntity userEntity) throws Exception;
}
