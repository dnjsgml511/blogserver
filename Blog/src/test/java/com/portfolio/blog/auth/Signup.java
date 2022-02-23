package com.portfolio.blog.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.portfolio.blog.auth.entity.UserEntity;
import com.portfolio.blog.auth.repositroy.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Signup {

	@Autowired
    MockMvc mockMvc;
	
	@Autowired
	UserRepository userRepository;
	
	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
		UserEntity user = userRepository.findById("admin");
		System.out.println(user);
	}
	
	@Test
	public void authController() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity("admin", "관리자", "1234", "관리자"));
		
		mockMvc.perform(post("/signup")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.servletPath("/signup"))
				.andDo(print())
		 		.andExpect(status().isOk());
	}
	
	@Test
	public void authFailController() throws Exception {
		
		UserEntity user = new UserEntity("admin", "관리자", "1234", "관리자");
		userRepository.save(user);
		
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.registerModule(new JavaTimeModule()).writeValueAsString(user);
		
		mockMvc.perform(post("/signup")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.servletPath("/signup"))
				.andDo(print())
		 		.andExpect(status().isForbidden());
	}
	
}
