package com.portfolio.blog.config.security;

public class Paths {
	protected final static String SERVER_PATH = "/home/path/";
	public final static String[] JWT_FILTER_PATH = { "/authenticate", "/signup", "/loginform", "/findid", "/findpw",
			"/swagger-ui/**","/swagger-ui", 
			"/swagger-resources/**", "/swagger-resources",
			"/v3/api-docs"		
	};
}
