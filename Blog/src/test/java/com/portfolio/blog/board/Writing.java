package com.portfolio.blog.board;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.portfolio.blog.board.controller.BoardController;
import com.portfolio.blog.util.DateSetting;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Writing {

	@Autowired
    MockMvc mockMvc;
	
	@Autowired
	BoardController boardController;
	
	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	
	
	@Test
	public void boardInsertController() throws Exception {
		
		DateSetting ds = new DateSetting();
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("title", "title");
		params.add("content", "content");
		params.add("writer", "writer");
		
		mockMvc.perform(post("/board/insert")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.params(params))
				.andDo(print())
		 		.andExpect(status().isOk());
	}

}
