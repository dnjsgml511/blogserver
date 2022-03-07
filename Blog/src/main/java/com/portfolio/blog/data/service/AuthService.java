package com.portfolio.blog.data.service;

import com.portfolio.blog.data.dto.auth.SignupResponse;
import com.portfolio.blog.data.dto.auth.UserControllResponse;
import com.portfolio.blog.data.dto.auth.UserResponse;

public interface AuthService {
	// 회원가입
	public String signup(SignupResponse response) throws Exception;
	// 로그인
	public String signin(UserResponse response) throws Exception;
	// 회원 관리
	public String userControll(UserControllResponse response, boolean adminCheck) throws Exception;
}
