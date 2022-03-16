package com.portfolio.blog.data.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponse {
	private String id;
	private String password;
	private String nickname;
	private String phone;
	private String email;
	
	public SignupResponse(String email) {
		this.email = email;
	}
}
