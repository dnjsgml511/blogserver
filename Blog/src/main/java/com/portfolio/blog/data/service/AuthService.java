package com.portfolio.blog.data.service;

import com.portfolio.blog.data.dto.SignupResponse;
import com.portfolio.blog.data.dto.UserResponse;

public interface AuthService {
	// 회원가입
	public String signup(SignupResponse response) throws Exception;
	// 로그인
	public String signin(UserResponse response) throws Exception;
	// 회원승인
	public String accessUser(String num) throws Exception;
	// 회원블락
	public String blockUser(String num) throws Exception;
}
