package com.portfolio.blog.IntegrationTest.admin;

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
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.dto.auth.UserControllResponse;
import com.portfolio.blog.data.repository.BoardRepository;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@DisplayName("등급변경 테스트")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class GradeChangeTest extends ControllerMockPerform {

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
		URL = "/admin/controll";
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
		
		@Nested
		@DisplayName("관리자가 변경")
		class admin {
			
			@Test
			@DisplayName("관리자로 변경")
			void changeAdmin() throws Exception {
				int num = 1;
				Role grade = Role.ROLE_ADMIN;
				
				String body = mapper.writeValueAsString(new UserControllResponse(num, grade));
				postMockMVC(URL, body, status().isOk(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("매니저로 변경")
			void changeManager() throws Exception {
				int num = 1;
				Role grade = Role.ROLE_MANAGER;
				
				String body = mapper.writeValueAsString(new UserControllResponse(num, grade));
				postMockMVC(URL, body, status().isOk(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("사용자로 변경")
			void changeUser() throws Exception {
				int num = 3;
				Role grade = Role.ROLE_MANAGER;
				
				String body = mapper.writeValueAsString(new UserControllResponse(num, grade));
				postMockMVC(URL, body, status().isOk(), ADMIN_TOKEN);
			}
		}
		

	}

	@Nested
	@DisplayName("실패")
	class Fail {
		
		@Nested
		@DisplayName("매니저가 변경")
		class manager {
			
			@Test
			@DisplayName("관리자로 변경")
			void changeAdmin() throws Exception {
				int num = 1;
				Role grade = Role.ROLE_ADMIN;
				
				String body = mapper.writeValueAsString(new UserControllResponse(num, grade));
				postMockMVC(URL, body, status().isForbidden(), MANAGER_TOKEN);
			}
			
			@Test
			@DisplayName("매니저로 변경")
			void changeManager() throws Exception {
				int num = 1;
				Role grade = Role.ROLE_MANAGER;
				
				String body = mapper.writeValueAsString(new UserControllResponse(num, grade));
				postMockMVC(URL, body, status().isForbidden(), MANAGER_TOKEN);
			}
			
			@Test
			@DisplayName("사용자로 변경")
			void changeUser() throws Exception {
				int num = 3;
				Role grade = Role.ROLE_MANAGER;
				
				String body = mapper.writeValueAsString(new UserControllResponse(num, grade));
				postMockMVC(URL, body, status().isForbidden(), MANAGER_TOKEN);
			}
		}
		
		@Nested
		@DisplayName("사용자가 변경")
		class user {
			
			@Test
			@DisplayName("관리자로 변경")
			void changeAdmin() throws Exception {
				int num = 1;
				Role grade = Role.ROLE_ADMIN;
				
				String body = mapper.writeValueAsString(new UserControllResponse(num, grade));
				postMockMVC(URL, body, status().isForbidden(), USER_TOKEN);
			}
			
			@Test
			@DisplayName("매니저로 변경")
			void changeManager() throws Exception {
				int num = 1;
				Role grade = Role.ROLE_MANAGER;
				
				String body = mapper.writeValueAsString(new UserControllResponse(num, grade));
				postMockMVC(URL, body, status().isForbidden(), USER_TOKEN);
			}
			
			@Test
			@DisplayName("사용자로 변경")
			void changeUser() throws Exception {
				int num = 3;
				Role grade = Role.ROLE_MANAGER;
				
				String body = mapper.writeValueAsString(new UserControllResponse(num, grade));
				postMockMVC(URL, body, status().isForbidden(), USER_TOKEN);
			}
		}
		
	}
}