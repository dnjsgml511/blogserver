package com.portfolio.blog.data.dto.auth;

import com.portfolio.blog.config.security.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserControllResponse {
	private String id;
	private Role grade;
	private Integer active;
	
	public UserControllResponse(String id, Integer active) {
		this.id = id;
		this.active = active;
	}
	
	public UserControllResponse(String id, Role grade) {
		this.id = id;
		this.grade = grade;
	}
}
