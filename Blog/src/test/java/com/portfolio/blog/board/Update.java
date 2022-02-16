package com.portfolio.blog.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
public class Update {

	@Autowired
    MockMvc mockMvc;
	
	@Autowired
	BoardController boardController;
	
	@Autowired
	BoardRepository boardRepository;
	
	@Before
	public void before() throws Exception {
		
		List<BoardEntity> list = new ArrayList<>();
		for(int i = 0; i < 3; ++i) {
			BoardEntity set = new BoardEntity("title" + i, "content" + i, "writer" + i);
			list.add(set);
		}
		boardRepository.saveAll(list);
		
	}

	@After
	public void after() throws Exception {
		System.out.println(boardRepository.findAll());
	}

	
	
	@Test
	public void boardUpdateController() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("idx", "1");
		params.add("title", "update title");
		params.add("content", "update content");
		params.add("writer", "update writer");
		
		mockMvc.perform(patch("/board/update")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.params(params))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
