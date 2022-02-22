package com.portfolio.blog.config.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailService implements UserDetailsService {

	// 사용자 확인
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
		if (id.equalsIgnoreCase("admin")) {
			grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
		}
		
		return new User(id, "", grantedAuthorities);
	}
}
