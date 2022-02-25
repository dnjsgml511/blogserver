package com.portfolio.blog.IntegrationTest.auth;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Signup extends ControllerMockPerform{

	@Autowired
    MockMvc mockMvc;
	
	@Autowired
	UserRepository userRepository;
	
	UserEntity user;
	String url;
	
	@Before
	public void before() throws Exception {
		user = new UserEntity("admin", "관리자", "1234");
		url = "/signup";
	}

	@After
	public void after() throws Exception {
		UserEntity user = userRepository.findById("admin");
		System.out.println(user);
		
		UserEntity userdata = userRepository.findById(user.getId());
		userRepository.delete(userdata);
	}
	
	@Test
	public void authController() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(user);
		
		postMockMVC(url, body, status().isOk());
	}
	
	@Test
	public void authFailOverlapController() throws Exception {
		
		userRepository.save(user);
		
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.registerModule(new JavaTimeModule()).writeValueAsString(user);
		
		postMockMVC(url, body, status().isForbidden());
	}
	
	@Test
	public void authFailNotIdController() throws Exception {
		
		userRepository.save(user);
		
		user = new UserEntity(null, "관리자", "1234", Role.ROLE_ADMIN);
		
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.registerModule(new JavaTimeModule()).writeValueAsString(user);
		
		postMockMVC(url, body, status().isForbidden());
	}
	
	@Test
	public void authFailNotNicknameController() throws Exception {
		
		userRepository.save(user);
		
		user = new UserEntity("admin", null, "1234", Role.ROLE_ADMIN);
		
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.registerModule(new JavaTimeModule()).writeValueAsString(user);
		
		postMockMVC(url, body, status().isForbidden());
	}
	
	@Test
	public void authFailNotPasswordController() throws Exception {
		
		userRepository.save(user);
		
		user = new UserEntity("admin", "ad", null, Role.ROLE_ADMIN);
		
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.registerModule(new JavaTimeModule()).writeValueAsString(user);
		
		postMockMVC(url, body, status().isForbidden());
	}
	
}
