package com.portfolio.blog.IntegrationTest.auth;

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
import com.portfolio.blog.data.dto.auth.SignupResponse;
import com.portfolio.blog.data.repository.BoardRepository;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@DisplayName("아이디 찾기")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class FindidTest extends ControllerMockPerform {

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

	String URL;

	@BeforeAll
	@Transactional
	void beforeAll() {
		URL = "/findid";
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
		@DisplayName("아이디 찾기 성공")
		void findid() throws Exception {
			
			String email = "lwh@koreapetroleum.com";
			
			String body = mapper.writeValueAsString(new SignupResponse(email));
			postMockMVC(URL, body, status().isOk());
		}
		
	}

	@Nested
	@DisplayName("실패")
	class Fail {
		
		@Test
		@DisplayName("이메일이 null 경우")
		void nullEMail() throws Exception {
			
			String email = null;
			
			String body = mapper.writeValueAsString(new SignupResponse(email));
			postMockMVC(URL, body, status().isBadRequest());
		}
		
		@Test
		@DisplayName("이메일이 비었을 경우")
		void emptyEmail() throws Exception {
			
			String email = "";
			
			String body = mapper.writeValueAsString(new SignupResponse(email));
			postMockMVC(URL, body, status().isBadRequest());
		}
		
		@Test
		@DisplayName("잘못된 이메일일 경우")
		void wrongEmail() throws Exception {
			
			String email = "wrong";
			
			String body = mapper.writeValueAsString(new SignupResponse(email));
			postMockMVC(URL, body, status().isBadRequest());
		}
		
		
		
	}
}