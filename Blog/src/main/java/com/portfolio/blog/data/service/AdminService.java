package com.portfolio.blog.data.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.portfolio.blog.data.entitiy.UserEntity;

public interface AdminService {
	public List<UserEntity> userlist(UserEntity userEntity) throws Exception;
	public ResponseEntity<?> userselect(UserEntity userEntity) throws Exception;
}
