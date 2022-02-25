package com.portfolio.blog.board;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

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
import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.controller.BoardController;
import com.portfolio.blog.data.entitiy.BoardEntity;
import com.portfolio.blog.data.repository.BoardRepository;
import com.portfolio.blog.util.MockPerform;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Writing extends MockPerform{

	@Autowired
    MockMvc mockMvc;
	
	@Autowired
	BoardController boardController;
	
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	String token;
	
	@Before
	public void before() throws Exception {
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", Role.ROLE_ADMIN);
		token = jwtTokenUtil.generateToken("ADMIN", claims);
	}

	@After
	public void after() throws Exception {
		System.out.println(boardRepository.findAll());
		boardRepository.deleteAll();
	}
	
	@Test
	public void boardInsertController() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new BoardEntity("title", "content", "writer"));
		
		postMockMVC("/board/insert", body, status().isOk(), token);
	}

}
