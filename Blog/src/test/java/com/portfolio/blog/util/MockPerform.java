package com.portfolio.blog.util;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.MultiValueMap;

public class MockPerform {

	@Autowired
    MockMvc mockMvc;
	
	public void postMockMVC(String url, String body, ResultMatcher matcher) throws Exception {
		mockMvc.perform(post(url)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.servletPath(url))
		.andDo(print())
		.andExpect(matcher);
	}
	
	public void postMockMVC(String url, String body, ResultMatcher matcher, String token) throws Exception {
		mockMvc.perform(post(url)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.servletPath(url))
				.andDo(print())
		 		.andExpect(matcher);
	}
	
	public void getMockMVC(String url, MultiValueMap<String, String> params, ResultMatcher matcher) throws Exception {
		mockMvc.perform(get(url)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.params(params))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	public void getMockMVC(String url, MultiValueMap<String, String> params, ResultMatcher matcher, String token) throws Exception {
		mockMvc.perform(get(url)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header("authorization", "Bearer " + token)
				.params(params))
				.andDo(print())
		 		.andExpect(status().isOk());
	}
	
}
