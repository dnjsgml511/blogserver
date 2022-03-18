package com.portfolio.blog.data.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
@NoArgsConstructor @AllArgsConstructor
public class SigninResponse {
	String id;
	String password;
	
	public SigninResponse(SigninResponse response) {
		this.id = response.id.toLowerCase();
		this.password = response.getPassword();
	}
}
