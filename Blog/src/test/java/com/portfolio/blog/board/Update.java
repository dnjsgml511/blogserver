package com.portfolio.blog.board;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.portfolio.blog.board.controller.BoardController;
import com.portfolio.blog.board.entity.BoardEntity;
import com.portfolio.blog.board.repository.BoardRepository;
import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.util.MockPerform;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Update extends MockPerform{

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
		
		token = jwtTokenUtil.generateToken("ADMIN");
		
		List<BoardEntity> list = new ArrayList<>();
		for(int i = 0; i < 3; ++i) {
			BoardEntity set = new BoardEntity("title" + i, "content" + i, "writer" + i);
			list.add(set);
		}
		boardRepository.saveAll(list);
		
	}

	@After
	public void after() throws Exception {
		System.out.println(boardRepository.findById(1));
		boardRepository.deleteAll();
	}

	
	
	@Test
	public void boardUpdateController() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("idx", "1");
		params.add("title", "update title");
		params.add("content", "update content");
		params.add("writer", "update writer");
		
		patchMockMVC("/board/update", params, status().isOk(), token);
	}

	@Test
	public void boardHideController() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("idx", "1");
		params.add("hide", "1");
		
		patchMockMVC("/board/hide", params, status().isOk(), token);
	}
	
	@Test
	public void boardToppickController() throws Exception {
		
		Iterable<BoardEntity> alldata = boardRepository.findAll();
		int firstidx = 0;
		for (BoardEntity data : alldata) {
			firstidx = data.getIdx();
			break;
		}
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("idx", Integer.toString(firstidx));
		params.add("top", "1");
		
		patchMockMVC("/board/toppick", params, status().isOk(), token);
	}
}
