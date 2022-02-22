package com.portfolio.blog.streaming;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.portfolio.blog.config.security.JwtTokenUtil;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Streaming {

	@Autowired
    MockMvc mockMvc;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	String token;
	
	@Before
	public void before() throws Exception{
		token = jwtTokenUtil.generateToken("ADMIN");
	}
	
	@After
	public void after() throws Exception {
	}
	
	@Test
	public void streamingController() throws Exception{
		
		mockMvc.perform(get("/video/test")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("authorization", "Bearer " + token))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
}
