package com.portfolio.blog.data.dto;

import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.entitiy.UserEntity;

import lombok.Getter;

@Getter
public class UserMapper {

	private String id;
	private Role grade;
	private String nickname;
	private String token;

	public UserMapper(UserEntity userEntity, String token) {
		this.id = userEntity.getId();
		this.grade = userEntity.getGrade();
		this.nickname = userEntity.getNickname();
		this.token = token;
	}
	
}
