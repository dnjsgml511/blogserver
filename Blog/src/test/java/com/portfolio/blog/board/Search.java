package com.portfolio.blog.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.portfolio.blog.board.controller.BoardController;
import com.portfolio.blog.board.entity.BoardEntity;
import com.portfolio.blog.board.repository.BoardRepository;
import com.portfolio.blog.util.DateSetting;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Search {

	@Autowired
    MockMvc mockMvc;
	
	@Autowired
	BoardController boardController;
	
	@Autowired
	BoardRepository BoardRepository;
	
	@Before
	public void before() throws Exception {
		
		List<BoardEntity> list = new ArrayList<>();
		for(int i = 0; i < 100; ++i) {
			BoardEntity set = new BoardEntity("title" + i, "content" + i, "writer" + i);
			list.add(set);
		}
		for(int i = 0; i < 100; ++i) {
			BoardEntity set = new BoardEntity("nexttitle" + i, "content" + i, "writer" + i);
			list.add(set);
		}
		BoardRepository.saveAll(list);
		
	}

	@After
	public void after() throws Exception {
	}

	
	
	@Test
	public void boardSearchController() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", "0");
		params.add("size", "15");
		params.add("search", "next");
		
		mockMvc.perform(post("/board/search")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.params(params))
				.andDo(print())
		 		.andExpect(status().isOk());
	}

}
