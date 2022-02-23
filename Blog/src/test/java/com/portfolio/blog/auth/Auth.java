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
import com.portfolio.blog.auth.dto.JwtRequest;
import com.portfolio.blog.auth.repositroy.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Auth {

	@Autowired
    MockMvc mockMvc;
	
	@Autowired
	UserRepository userRepository;
		
	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
		
	}
	
	@Test
	public void authController() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new JwtRequest("admin", "1234"));
		
		mockMvc.perform(post("/authenticate")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.servletPath("/authenticate"))
				.andDo(print())
		 		.andExpect(status().isOk());
	}
}
