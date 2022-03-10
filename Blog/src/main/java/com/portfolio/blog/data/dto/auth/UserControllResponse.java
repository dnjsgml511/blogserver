package com.portfolio.blog.data.dto.auth;

import com.portfolio.blog.config.security.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserControllResponse {
	private int num;
	private Role grade;
	private Integer active;
	
	public UserControllResponse(int num, Integer active) {
		this.num = num;
		this.active = active;
	}
	
	public UserControllResponse(int num, Role grade) {
		this.num = num;
		this.grade = grade;
	}
}
