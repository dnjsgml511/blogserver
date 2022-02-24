package com.portfolio.blog.auth;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.dto.JwtRequest;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.MockPerform;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Auth extends MockPerform {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	UserRepository userRepository;

	String url;
	UserEntity admin;
	UserEntity user;

	@Before
	public void before() throws Exception {
		url = "/authenticate";
		List<UserEntity> list = new ArrayList<UserEntity>();
		admin = new UserEntity("admin", "관리자", "1234", Role.ROLE_ADMIN);
		list.add(admin);
		user = new UserEntity("user", "사용자", "1234", Role.ROLE_USER);
		list.add(user);
		userRepository.saveAll(list);
	}

	@After
	public void after() throws Exception {
		userRepository.delete(admin);
		userRepository.delete(user);
	}

	@Test
	public void authAdminController() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity("admin", "1234"));

		postMockMVC(url, body, status().isOk());
	}
	
	@Test
	public void authUserController() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity("user", "1234"));
		
		postMockMVC(url, body, status().isOk());
	}

	@Test
	public void authFailIDController() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new JwtRequest("admin1", "1234"));

		postMockMVC(url, body, status().isForbidden());
	}

	@Test
	public void authFailPasswordController() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new JwtRequest("admin", "12345"));

		postMockMVC(url, body, status().isForbidden());
	}
}
