package com.portfolio.blog.admin;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.MockPerform;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Admin extends MockPerform {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	String token;
	UserEntity user;

	@Before
	public void before() throws Exception {
		
		token = jwtTokenUtil.generateToken("ADMIN");
		
		user = new UserEntity("admin", "관리자", "1234");
		userRepository.save(user);
	}

	@After
	public void after() throws Exception {
		userRepository.delete(user);
	}


	@Test
	public void userlistController() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("id", "admin");
		params.add("password", "12345");

		getMockMVC("/admin/userlist", params, status().isForbidden(), token);
	}
}
