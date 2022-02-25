package com.portfolio.blog.controllerTest.board;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.controller.BoardController;
import com.portfolio.blog.data.entitiy.BoardEntity;
import com.portfolio.blog.data.repository.BoardRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Delete extends ControllerMockPerform{

	@Autowired
	MockMvc mockMvc;

	@Autowired
	BoardController boardController;

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	String token;
	String url;
	
	@Before
	public void before() throws Exception {
		
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", Role.ROLE_ADMIN);
		token = jwtTokenUtil.generateToken("ADMIN", claims);
		url = "/board/delete";
		
		List<BoardEntity> list = new ArrayList<>();
		for(int i = 0; i < 3; ++i) {
			BoardEntity set = new BoardEntity("title", "content", "writer");
			list.add(set);
		}
		boardRepository.saveAll(list);
	}

	@After
	public void after() throws Exception {
		System.out.println(boardRepository.findAll());
		
		boardRepository.deleteAll();
	}

	@Test
	public void boardDeleteController() throws Exception {

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("idx", "1");

		deleteMockMVC(url, params, status().isOk(), token);
	}
}
