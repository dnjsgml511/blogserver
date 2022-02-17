package com.portfolio.blog.auth.dto;

import lombok.Data;

@Data
public class JwtRequest {
	private String id;
	private String password;
	
	public JwtRequest(){
		
	}
	
	public JwtRequest(String id, String password){
		this.id = id;
		this.password = password;
	}
}
