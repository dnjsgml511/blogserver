package com.portfolio.blog.IntegrationTest.board;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.portfolio.blog.data.repository.BoardRepository;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@DisplayName("게시물 삭제 테스트")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class DeletePostTest extends ControllerMockPerform {

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
		URL = "/board/delete";
		ADMIN_TOKEN = jwtTokenUtil.createAdmintoken(1);
		MANAGER_TOKEN = jwtTokenUtil.createManagertoken(3);
		USER_TOKEN = jwtTokenUtil.createUsertoken(5);
	}

	@AfterAll
	void afterAll() {
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
		@DisplayName("관리자의 관리자 게시물 삭제")
		void adminDelToAdminPost() throws Exception {
			params.add("num", "1");			
			deleteMockMVC(URL, params, status().isOk(), ADMIN_TOKEN);
		}
		
		@Test
		@DisplayName("관리자의 매니저 게시물 삭제")
		void adminDelToManagerPost() throws Exception {
			params.add("num", "2");			
			deleteMockMVC(URL, params, status().isOk(), ADMIN_TOKEN);
		}
		
		@Test
		@DisplayName("관리자의 유저 게시물 삭제")
		void adminDelToUserPost() throws Exception {
			params.add("num", "3");			
			deleteMockMVC(URL, params, status().isOk(), ADMIN_TOKEN);
		}
		
		@Test
		@DisplayName("매니저의 매니저 게시물 삭제")
		void managerDelToManagerPost() throws Exception {
			params.add("num", "2");			
			deleteMockMVC(URL, params, status().isOk(), MANAGER_TOKEN);
		}
		
		@Test
		@DisplayName("매니저의 유저 게시물 삭제")
		void managerDelToUserPost() throws Exception {
			params.add("num", "3");			
			deleteMockMVC(URL, params, status().isOk(), MANAGER_TOKEN);
		}
		
	}

	@Nested
	@DisplayName("실패")
	class Fail {
		
		@Test
		@DisplayName("매니저의 관리자 게시물 삭제")
		void managerDelToAdmin() throws Exception {
			params.add("num", "1");			
			deleteMockMVC(URL, params, status().isForbidden(), MANAGER_TOKEN);
		}
		
		@Test
		@DisplayName("사용자의 관리자 게시물 삭제")
		void userDelToAdmin() throws Exception {
			params.add("num", "1");			
			deleteMockMVC(URL, params, status().isForbidden(), USER_TOKEN);
		}
		
		@Test
		@DisplayName("사용자의 매니저 게시물 삭제")
		void userDelToManager() throws Exception {
			params.add("num", "3");			
			deleteMockMVC(URL, params, status().isForbidden(), USER_TOKEN);
		}
		
		@Test
		@DisplayName("사용자의 다른 사용자 게시물 삭제")
		void userDelToOtherUser() throws Exception {
			params.add("num", "5");			
			deleteMockMVC(URL, params, status().isForbidden(), USER_TOKEN);
		}
		
		
	}
}