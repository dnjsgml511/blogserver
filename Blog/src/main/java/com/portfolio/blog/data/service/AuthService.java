package com.portfolio.blog.data.service;

import javax.servlet.http.HttpServletRequest;

import com.portfolio.blog.data.dto.auth.SigninResponse;
import com.portfolio.blog.data.dto.auth.SignupResponse;
import com.portfolio.blog.data.dto.auth.UserMapper;

public interface AuthService {
	// 회원가입
	public String signup(SignupResponse response) throws Exception;
	// 로그인
	public UserMapper signin(SigninResponse response, HttpServletRequest request) throws Exception;
}
