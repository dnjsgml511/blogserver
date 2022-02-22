package com.portfolio.blog.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {
	private String id;
	private String password;

	public JwtRequest() {

	}

	public JwtRequest(String id, String password) {
		this.id = id;
		this.password = password;
	}
}
