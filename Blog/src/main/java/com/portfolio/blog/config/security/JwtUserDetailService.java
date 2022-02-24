package com.portfolio.blog.config.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class JwtUserDetailService implements UserDetailsService {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	// 사용자 확인
	@Override
	public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		String username = "";
		
		try {
			String role = jwtTokenUtil.popJWTData(token, "role");
			username = jwtTokenUtil.popJWTData(token, "jti");
			
			if(role.equals("ROLE_ADMIN")) {
				grantedAuthorities.add(new SimpleGrantedAuthority(Role.ROLE_ADMIN.toString()));
			}else if(role.equals("ROLE_MANAGER")) {
				grantedAuthorities.add(new SimpleGrantedAuthority(Role.ROLE_MANAGER.toString()));
			}else if(role.equals("ROLE_USER")) {
				grantedAuthorities.add(new SimpleGrantedAuthority(Role.ROLE_USER.toString()));
			}
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return new User(username, "", grantedAuthorities);
	}
}
