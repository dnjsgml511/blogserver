package com.portfolio.blog.util;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
				.header("authorization", "Bearer " + token)
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
		.andExpect(matcher);
	}
	
	public void getMockMVC(String url, MultiValueMap<String, String> params, ResultMatcher matcher, String token) throws Exception {
		mockMvc.perform(get(url)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header("authorization", "Bearer " + token)
				.params(params))
				.andDo(print())
		 		.andExpect(matcher);
	}
	
	public void patchMockMVC(String url, MultiValueMap<String, String> params, ResultMatcher matcher) throws Exception {
		mockMvc.perform(patch(url)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.params(params))
		.andDo(print())
		.andExpect(matcher);
	}
	
	public void patchMockMVC(String url, MultiValueMap<String, String> params, ResultMatcher matcher, String token) throws Exception {
		mockMvc.perform(patch(url)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header("authorization", "Bearer " + token)
				.params(params))
		.andDo(print())
		.andExpect(matcher);
	}
	
	public void deleteMockMVC(String url, MultiValueMap<String, String> params, ResultMatcher matcher) throws Exception {
		mockMvc.perform(delete(url)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.params(params))
		.andDo(print())
		.andExpect(matcher);
	}
	
	public void deleteMockMVC(String url, MultiValueMap<String, String> params, ResultMatcher matcher, String token) throws Exception {
		mockMvc.perform(delete(url)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header("authorization", "Bearer " + token)
				.params(params))
		.andDo(print())
		.andExpect(matcher);
	}
	
}
