package com.portfolio.blog.data.service;

import java.util.List;

import com.portfolio.blog.data.entitiy.UserEntity;

public interface AdminService {
	public List<UserEntity> userlist(UserEntity userEntity) throws Exception;
	public UserEntity userselect(UserEntity userEntity) throws Exception;
}
