package com.portfolio.blog.data.dto.auth;

import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.entitiy.UserEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserMapper {

	@Schema(description = "ID")
	private String id;
	@Schema(description = "Grade")
	private Role grade;
	@Schema(description = "NickName")
	private String nickname;
	@Schema(description = "JWT Token")
	private String token;

	public UserMapper(UserEntity userEntity, String token) {
		this.id = userEntity.getId();
		this.grade = userEntity.getGrade();
		this.nickname = userEntity.getNickname();
		this.token = token;
	}
	
}
