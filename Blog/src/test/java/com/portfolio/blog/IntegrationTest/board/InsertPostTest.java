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
import com.portfolio.blog.data.dto.board.InsertBoardResponse;
import com.portfolio.blog.data.repository.BoardRepository;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@DisplayName("게시물 작성 테스트")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class InsertPostTest extends ControllerMockPerform {

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

	String URL, ADMIN_TOKEN, MANAGER_TOKEN, USER_TOKEN, BLOCK_USER_TOKEN;

	@BeforeAll
	@Transactional
	void beforeAll() {
		URL = "/board/insert";
		ADMIN_TOKEN = jwtTokenUtil.createAdmintoken(1);
		MANAGER_TOKEN = jwtTokenUtil.createManagertoken(3);
		USER_TOKEN = jwtTokenUtil.createUsertoken(5);
		BLOCK_USER_TOKEN = jwtTokenUtil.createUsertoken(6);
	}

	@AfterAll
	void afterAll() {
	}

	@BeforeEach
	void beforeEach() {
		mapper = new ObjectMapper();
		params = new LinkedMultiValueMap<>();

		// getMockMVC(URL, params, status().isOk(), TOKEN);

		// String body = mapper.writeValueAsString(new DTO());
		// postMockMVC(URL, body, status().isBadRequest(), TOKEN);

	}

	@AfterEach
	void afterEach() {
	}

	@Nested
	@DisplayName("성공")
	class Success {
		
		@Test
		@DisplayName("관리자의 게시물 등록")
		void insertAdminPost() throws Exception {
			
			String title = "새로운 게시물";
			String content = "등록입니다";
			
			String body = mapper.writeValueAsString(new InsertBoardResponse(title, content));
			postMockMVC(URL, body, status().isOk(), ADMIN_TOKEN);
		}
		
		@Test
		@DisplayName("매니저의 게시물 등록")
		void insertManagerPost() throws Exception {
			
			String title = "새로운 게시물";
			String content = "등록입니다";
			
			String body = mapper.writeValueAsString(new InsertBoardResponse(title, content));
			postMockMVC(URL, body, status().isOk(), MANAGER_TOKEN);
		}
		
		@Test
		@DisplayName("유저의 게시물 등록")
		void insertUserPost() throws Exception {
			
			String title = "새로운 게시물";
			String content = "등록입니다";
			
			String body = mapper.writeValueAsString(new InsertBoardResponse(title, content));
			postMockMVC(URL, body, status().isOk(), USER_TOKEN);
		}
		
	}

	@Nested
	@DisplayName("실패")
	class Fail {
		
		@Nested
		@DisplayName("타이틀 관련")
		class Title{
			
			@Test
			@DisplayName("타이틀이 null일 경우")
			void nullTitle() throws Exception{
				
				String title = null;
				String content = "등록입니다";
				
				String body = mapper.writeValueAsString(new InsertBoardResponse(title, content));
				postMockMVC(URL, body, status().isBadRequest(), USER_TOKEN);
			}
			
			@Test
			@DisplayName("타이틀이 비어있을 경우")
			void emptyTitle() throws Exception{
				
				String title = "";
				String content = "등록입니다";
				
				String body = mapper.writeValueAsString(new InsertBoardResponse(title, content));
				postMockMVC(URL, body, status().isBadRequest(), USER_TOKEN);
			}
			
		}
		
		@Nested
		@DisplayName("사용자 관련")
		class users{
			
			@Test
			@DisplayName("토큰이 없을 경우")
			void emptyTitle() throws Exception{
				
				String title = "새로운 게시물";
				String content = "등록입니다";
				
				String body = mapper.writeValueAsString(new InsertBoardResponse(title, content));
				postMockMVC(URL, body, status().isUnauthorized());
			}
			
			@Test
			@DisplayName("블락된 계정일 경우")
			void blockUser() throws Exception{
				
				String title = "새로운 게시물";
				String content = "등록입니다";
				
				String body = mapper.writeValueAsString(new InsertBoardResponse(title, content));
				postMockMVC(URL, body, status().isBadRequest(), BLOCK_USER_TOKEN);
			}
			
		}
		
	}
}