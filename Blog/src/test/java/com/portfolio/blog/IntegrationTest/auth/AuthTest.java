package com.portfolio.blog.IntegrationTest.auth;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
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
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@DisplayName("권한 테스트")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthTest extends ControllerMockPerform{
	
	@Autowired MockMvc mockMvc;
	@Autowired UserRepository userRepository;
	
	MultiValueMap<String, String> params;
	List<UserEntity> list;
	ObjectMapper mapper;
	
	String url;
	
    @BeforeAll
    void beforeAll() {
    	url = "/authenticate";
    	
    	list = new ArrayList<UserEntity>();
    	
    	list.add(new UserEntity("adminActive", "활동관리자", "1234", Role.ROLE_ADMIN, 1));
    	list.add(new UserEntity("adminBlock", "비활동관리자", "1234", Role.ROLE_ADMIN));
    	list.add(new UserEntity("managerActive", "활동매니저", "1234", Role.ROLE_MANAGER, 1));
    	list.add(new UserEntity("managerBlock", "비활동매니저", "1234", Role.ROLE_MANAGER));
    	list.add(new UserEntity("userActive", "활동사용자", "1234", Role.ROLE_USER, 1));
    	list.add(new UserEntity("userBlock", "비활동사용자", "1234", Role.ROLE_USER));
    	
    	userRepository.saveAll(list);
    }
    
    @AfterAll
    void afterAll() {
    	for (UserEntity data : list) userRepository.delete(data);
    }
 
    @BeforeEach
    void beforeEach() {
    	mapper = new ObjectMapper();
    }
    
	@Nested
	@DisplayName("성공")
	class success {

		@Test
		@DisplayName("관리자 로그인 성공")
		void admin() throws Exception {
			String body = mapper.writeValueAsString(new UserEntity("adminActive", "1234"));
			postMockMVC(url, body, status().isOk());
		}
		
		@Test
		@DisplayName("관리자 로그인 성공")
		void manager() throws Exception {
			String body = mapper.writeValueAsString(new UserEntity("managerActive", "1234"));
			postMockMVC(url, body, status().isOk());
		}
		
		@Test
		@DisplayName("사용자 로그인 성공")
		void user() throws Exception {
			String body = mapper.writeValueAsString(new UserEntity("userActive", "1234"));
			postMockMVC(url, body, status().isOk());
		}
    }
	
	@Nested
	@DisplayName("실패")
	class fail {
		
		@Nested
		@DisplayName("ID 관련 실패")
		class FailID {
			@Test
			@DisplayName("아이디 미입력 로그인 실패")
			void emptyID() throws Exception {
				String body = mapper.writeValueAsString(new UserEntity("", "1234"));
				postMockMVC(url, body, status().isForbidden());
			}
			@Test
			@DisplayName("아이디 NULL 로그인 실패")
			void nullID() throws Exception {
				String body = mapper.writeValueAsString(new UserEntity(null, "1234"));
				postMockMVC(url, body, status().isForbidden());
			}
			@Test
			@DisplayName("가입 안 된 아이디 로그인 실패")
			void wrongID() throws Exception {
				String body = mapper.writeValueAsString(new UserEntity("wrong", "1234"));
				postMockMVC(url, body, status().isForbidden());
			}
		}
		
		@Nested
		@DisplayName("비밀번호 관련 실패")
		class FailPASSWORD {
			@Test
			@DisplayName("비밀번호 미입력 관리자  로그인 실패")
			void emptyAmin() throws Exception {
				String body = mapper.writeValueAsString(new UserEntity("adminActive", ""));
				postMockMVC(url, body, status().isForbidden());
			}
			@Test
			@DisplayName("비밀번호 미입력 매니저  로그인 실패")
			void emptyManager() throws Exception {
				String body = mapper.writeValueAsString(new UserEntity("managerActive", ""));
				postMockMVC(url, body, status().isForbidden());
			}
			@Test
			@DisplayName("비밀번호 미입력 사용자 로그인 실패")
			void emptyUser() throws Exception {
				String body = mapper.writeValueAsString(new UserEntity("userActive", ""));
				postMockMVC(url, body, status().isForbidden());
			}
			@Test
			@DisplayName("Null 비밀번호 관리자 로그인 실패")
			void nullAdmin() throws Exception {
				String body = mapper.writeValueAsString(new UserEntity("adminActive", null));
				postMockMVC(url, body, status().isForbidden());
			}
			@Test
			@DisplayName("Null 비밀번호 매니저 로그인 실패")
			void nullManager() throws Exception {
				String body = mapper.writeValueAsString(new UserEntity("managerActive", null));
				postMockMVC(url, body, status().isForbidden());
			}
			@Test
			@DisplayName("Null 비밀번호 사용자 로그인 실패")
			void nullUser() throws Exception {
				String body = mapper.writeValueAsString(new UserEntity("userActive", null));
				postMockMVC(url, body, status().isForbidden());
			}
			@Test
			@DisplayName("틀린 비밀번호 관리자 로그인 실패")
			void wrongAdmin() throws Exception {
				String body = mapper.writeValueAsString(new UserEntity("adminActive", "wrong"));
				postMockMVC(url, body, status().isForbidden());
			}
			@Test
			@DisplayName("틀린 비밀번호 관리자 로그인 실패")
			void wrongManager() throws Exception {
				String body = mapper.writeValueAsString(new UserEntity("adminActive", "wrong"));
				postMockMVC(url, body, status().isForbidden());
			}
			@Test
			@DisplayName("틀린 비밀번호 사용자 로그인 실패")
			void wrongUser() throws Exception {
				String body = mapper.writeValueAsString(new UserEntity("adminActive", "wrong"));
				postMockMVC(url, body, status().isForbidden());
			}
		}
		
		@Nested
		@DisplayName("계정 비활성화 관련 실패")
		class block {
			@Test
			@DisplayName("비활성 관리자 로그인 실패")
			void admin() throws Exception {
				String body = mapper.writeValueAsString(new UserEntity("adminBlock", "1234"));
				postMockMVC(url, body, status().isForbidden());
			}
			@Test
			@DisplayName("비활성 관리자 로그인 실패")
			void manager() throws Exception {
				String body = mapper.writeValueAsString(new UserEntity("adminBlock", "1234"));
				postMockMVC(url, body, status().isForbidden());
			}
			@Test
			@DisplayName("비활성 사용자 로그인 실패")
			void user() throws Exception {
				String body = mapper.writeValueAsString(new UserEntity("adminBlock", "1234"));
				postMockMVC(url, body, status().isForbidden());
			}
		}
	}
}