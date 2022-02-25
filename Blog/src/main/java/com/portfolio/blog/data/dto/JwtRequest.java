package com.portfolio.blog.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {
	
	@Schema(description = "id")
	private String id;
	
	@Schema(description = "password")
	private String password;

	public JwtRequest() {

	}

	public JwtRequest(String id, String password) {
		this.id = id;
		this.password = password;
	}
}
