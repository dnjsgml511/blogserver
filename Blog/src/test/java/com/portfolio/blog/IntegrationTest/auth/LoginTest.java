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
import com.portfolio.blog.data.dto.auth.SigninResponse;
import com.portfolio.blog.data.repository.BoardRepository;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@DisplayName("로그인 테스트")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class LoginTest extends ControllerMockPerform {

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
	void beforeAll() {
		URL = "/authenticate";
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
		@DisplayName("사용자 로그인")
		void userLogin() throws Exception {

			String id = "userActive";
			String password = "1234";

			String body = mapper.writeValueAsString(new SigninResponse(id, password));
			postMockMVC(URL, body, status().isOk());
		}

		@Test
		@DisplayName("매니저 로그인")
		void managerLogin() throws Exception {

			String id = "managerActive";
			String password = "1234";

			String body = mapper.writeValueAsString(new SigninResponse(id, password));
			postMockMVC(URL, body, status().isOk());
		}

		@Test
		@DisplayName("관리자 로그인")
		void adminLogin() throws Exception {

			String id = "adminActive";
			String password = "1234";

			String body = mapper.writeValueAsString(new SigninResponse(id, password));
			postMockMVC(URL, body, status().isOk());
		}

	}

	@Nested
	@DisplayName("실패")
	class Fail {

		@Test
		@DisplayName("존재하지 않은 아이디")
		void notExistId() throws Exception {
			String id = "notExist";
			String password = "1234";

			String body = mapper.writeValueAsString(new SigninResponse(id, password));
			postMockMVC(URL, body, status().isBadRequest());
		}

		@Test
		@DisplayName("틀린 비밀번호")
		void wrongPassword() throws Exception {
			String id = "userActive";
			String password = "12345";

			String body = mapper.writeValueAsString(new SigninResponse(id, password));
			postMockMVC(URL, body, status().isBadRequest());
		}
		
		@Test
		@DisplayName("블락된 사용자")
		void blockUser() throws Exception {
			String id = "userBlock";
			String password = "1234";

			String body = mapper.writeValueAsString(new SigninResponse(id, password));
			postMockMVC(URL, body, status().isBadRequest());
		}
	}
}