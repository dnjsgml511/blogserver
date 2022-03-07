package com.portfolio.blog.data.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.portfolio.blog.data.dto.auth.SigninResponse;
import com.portfolio.blog.data.dto.auth.SignupResponse;
import com.portfolio.blog.data.dto.auth.UserControllResponse;

public interface AuthService {
	// 회원가입
	public String signup(SignupResponse response) throws Exception;
	// 로그인
	public HashMap<String, Object> signin(SigninResponse response, HttpServletRequest request) throws Exception;
	// 회원 관리
	public String userControll(UserControllResponse response, boolean adminCheck) throws Exception;
}
