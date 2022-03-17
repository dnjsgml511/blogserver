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

@DisplayName("회원가입")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class SignupTest extends ControllerMockPerform{
	
	@Autowired MockMvc mockMvc;
	@Autowired JwtTokenUtil jwtTokenUtil;
	@Autowired UserRepository userRepository;
	@Autowired BoardRepository boardRepository;

	ObjectMapper mapper;
	MultiValueMap<String, String> params;
	
	String URL, ADMIN_TOKEN, MANAGER_TOKEN, USER_TOKEN;
	
	String id = "newUser";
	String password = "1234";
	String nickname = "새로운 가입자";
	String phone = "010-0000-0000";
	String email = "lwh@koreapetroleum.com";
	String companyno = "106-55-12345";
	
    @BeforeAll
    void beforeAll(){
    	URL = "/signup"; 
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
		@DisplayName("회원가입 성공")
		void signUpSuccess() throws Exception{
			String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
			postMockMVC(URL, body, status().isOk());
		}
		
	}
	
	@Nested
	@DisplayName("실패")
	class Fail {

		@Nested
		@DisplayName("아이디 관련 실패")
		class Id{
			
			@Test
			@DisplayName("중복 아이디")
			void overlappingId() throws Exception{
				id = "userActive";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isConflict());
			}
			
			@Test
			@DisplayName("짧은 아이디 길이 체크")
			void shortId() throws Exception{
				id = "sho";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("긴 아이디 길이 체크")
			void longId() throws Exception{
				id = "longlonglonglonglongl";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("아이디 빈칸")
			void emptyId() throws Exception{
				id = null;
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}
		}
		

		@Nested
		@DisplayName("닉네임 관련 실패")
		class Nickname{
			
			@Test
			@DisplayName("중복 닉네임")
			void overlapNickname() throws Exception{
				
				nickname = "활동사용자";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isConflict());
			}
			

			@Test
			@DisplayName("짧은 닉네임 길이")
			void shortNickname() throws Exception{
				
				nickname = "새로운";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("긴 닉네임 길이")
			void longNickname() throws Exception{
				
				nickname = "새로운사용자새로운사용자새로운사용자새로운";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("빈 닉네임")
			void emptyNickname() throws Exception{
				
				nickname = null;
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
		}
		
		@Nested
		@DisplayName("비밀번호 관련 실패")
		class Password{
			
			@Test
			@DisplayName("짧은 비밀번호")
			void shortPassword() throws Exception{
				password = "123";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("긴 비밀번호")
			void longPassword() throws Exception{
				password = "123456789123456789123";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("빈 비밀번호")
			void emptyPassword() throws Exception{
				password = null;
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
		}
		
		@Nested
		@DisplayName("전화번호 관련 실패")
		class phone{
			
			@Test
			@DisplayName("빈 전화번호")
			void emptyPhone() throws Exception{
				phone = "";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("null 전화번호")
			void nullPhone() throws Exception{
				phone = null;
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}

			@Test
			@DisplayName("잘못된 전화번호 1")
			void wrongPhone1() throws Exception{
				phone = "0100-000-0000";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("잘못된 전화번호 2")
			void wrongPhone2() throws Exception{
				phone = "010-00000-0000";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("잘못된 전화번호 3")
			void wrongPhone3() throws Exception{
				phone = "010-000-0000";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("잘못된 전화번호 4")
			void wrongPhone4() throws Exception{
				phone = "010-0000-000";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("잘못된 전화번호 5")
			void wrongPhone5() throws Exception{
				phone = "010-0000-00000";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname, phone, email, companyno));
				postMockMVC(URL, body, status().isBadRequest());
			}
		}
	}
}