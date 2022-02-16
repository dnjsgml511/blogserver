package com.portfolio.blog.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Delete {

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
			BoardEntity set = new BoardEntity("title", "content", "writer");
			list.add(set);
		}
		boardRepository.saveAll(list);
	}

	@After
	public void after() throws Exception {
		System.out.println(boardRepository.findAll());
	}

	@Test
	public void boardDeleteController() throws Exception {

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("idx", "1");

		mockMvc.perform(delete("/board/delete")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.params(params))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
