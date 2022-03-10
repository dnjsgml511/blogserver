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
			String id = "newUser";
			String password = "1234";
			String nickname = "새로운 가입자";
			
			String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname));
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
				String id = "userActive";
				String password = "1234";
				String nickname = "아이디 빈칸 가입자";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname));
				postMockMVC(URL, body, status().isConflict());
			}
			
			@Test
			@DisplayName("짧은 아이디 길이 체크")
			void shortId() throws Exception{
				String id = "sho";
				String password = "1234";
				String nickname = "아이디 빈칸 가입자";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("긴 아이디 길이 체크")
			void longId() throws Exception{
				String id = "longlonglonglonglongl";
				String password = "1234";
				String nickname = "아이디 빈칸 가입자";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("아이디 빈칸")
			void emptyId() throws Exception{
				String id = null;
				String password = "1234";
				String nickname = "아이디 null 가입자";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname));
				postMockMVC(URL, body, status().isBadRequest());
			}
		}
		

		@Nested
		@DisplayName("닉네임 관련 실패")
		class Nickname{
			
			@Test
			@DisplayName("중복 닉네임")
			void overlapNickname() throws Exception{
				
				String id = "newUser";
				String password = "1234";
				String nickname = "활동사용자";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname));
				postMockMVC(URL, body, status().isConflict());
			}
			

			@Test
			@DisplayName("짧은 닉네임 길이")
			void shortNickname() throws Exception{
				
				String id = "newUser";
				String password = "1234";
				String nickname = "새로운";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("긴 닉네임 길이")
			void longNickname() throws Exception{
				
				String id = "newUser";
				String password = "1234";
				String nickname = "새로운사용자새로운사용자새로운사용자새로운";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("빈 닉네임")
			void emptyNickname() throws Exception{
				
				String id = "newUser";
				String password = "1234";
				String nickname = null;
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
		}
		
		@Nested
		@DisplayName("비밀번호 관련 실패")
		class Password{
			
			@Test
			@DisplayName("짧은 비밀번호")
			void shortPassword() throws Exception{
				String id = "newUser";
				String password = "123";
				String nickname = "새로운사용자";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("긴 비밀번호")
			void longPassword() throws Exception{
				String id = "newUser";
				String password = "123456789123456789123";
				String nickname = "새로운사용자";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("빈 비밀번호")
			void emptyPassword() throws Exception{
				String id = "newUser";
				String password = null;
				String nickname = "새로운사용자";
				
				String body = mapper.writeValueAsString(new SignupResponse(id, password, nickname));
				postMockMVC(URL, body, status().isBadRequest());
			}
			
		}
	}
}