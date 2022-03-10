package com.portfolio.blog.data.service;

import javax.servlet.http.HttpServletRequest;

import com.portfolio.blog.data.dto.auth.UserControllResponse;

public interface AdminService {

	public String userControll(UserControllResponse response, HttpServletRequest request) throws Exception;
}
