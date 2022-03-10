package com.portfolio.blog;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.data.entitiy.BoardEntity;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.BoardRepository;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.BeforeAllSetting;
import com.portfolio.blog.util.ControllerMockPerform;

@DisplayName("테스팅 양식")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataSetting extends ControllerMockPerform {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	@Autowired
	UserRepository userRepository;
	@Autowired
	BoardRepository boardRepository;

	ObjectMapper mapper;
	MultiValueMap<String, String> params;

	String URL, ADMIN_TOKEN, MANAGER_TOKEN, USER_TOKEN;

	@BeforeAll
	@Transactional
	void beforeAll() {
		BeforeAllSetting beforeAllSetting = new BeforeAllSetting();
		userRepository.saveAll(beforeAllSetting.userSetting());
		boardRepository.saveAll(beforeAllSetting.boardSetting());
	}

	@AfterAll
	void afterAll() {
		
    	List<UserEntity> check = userRepository.findAll();
    	for (UserEntity data : check) {
			System.out.println(data);
		}
    	
    	List<BoardEntity> check2 = boardRepository.findAll();
    	for (BoardEntity data : check2) {
			System.out.println(data);
		}
		
	}

	@BeforeEach
	void beforeEach() {
		mapper = new ObjectMapper();
		params = new LinkedMultiValueMap<>();
	}

	@AfterEach
	void afterEach() {
	}

	@Nested
	@DisplayName("성공")
	class Success {
		
		@Test
		void Test() throws Exception {}
		
	}

	@Nested
	@DisplayName("실패")
	class Fail {
	}
}