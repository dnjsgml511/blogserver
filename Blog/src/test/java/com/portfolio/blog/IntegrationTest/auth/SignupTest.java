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
import com.portfolio.blog.data.dto.SignupResponse;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@DisplayName("회원가입 테스트")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SignupTest extends ControllerMockPerform{
	
	@Autowired MockMvc mockMvc;
	@Autowired UserRepository userRepository;
	
	MultiValueMap<String, String> params;
	List<UserEntity> list;
	ObjectMapper mapper;
	
	String SIGNUP_URL;
	
    @BeforeAll
    void beforeAll() {
    	SIGNUP_URL = "/signup";
    	list = new ArrayList<UserEntity>();
    	list.add(new UserEntity("overlap","1234","중복계정"));
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
		@DisplayName("회원가입 성공")
		void signup() throws Exception {
			String body = mapper.writeValueAsString(new SignupResponse("user", "usernick", "1234"));
			postMockMVC(SIGNUP_URL, body, status().isOk());
		}
		
	}
	
	@Nested
	@DisplayName("실패")
	class fail {
		
		@Nested
		@DisplayName("입력값 부족")
		class NotEnoughParam{
			@Test
			@DisplayName("아이디가 빈칸이라 회원가입 실패")
			void emptyId() throws Exception {
				String body = mapper.writeValueAsString(new SignupResponse("", "1234", "nickname"));
				postMockMVC(SIGNUP_URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("아이디가 null이라 회원가입 실패")
			void nullId() throws Exception {
				String body = mapper.writeValueAsString(new SignupResponse(null, "1234", "nickname"));
				postMockMVC(SIGNUP_URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("비밀번호가 빈칸이라 회원가입 실패")
			void emptyPassword() throws Exception {
				String body = mapper.writeValueAsString(new SignupResponse("user", "", "nickname"));
				postMockMVC(SIGNUP_URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("비밀번호가 null이라 회원가입 실패")
			void nullPassword() throws Exception {
				String body = mapper.writeValueAsString(new SignupResponse("user", null, "nickname"));
				postMockMVC(SIGNUP_URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("닉네임이 빈칸이라 회원가입 실패")
			void emptyNickname() throws Exception {
				String body = mapper.writeValueAsString(new SignupResponse("user", "1234", ""));
				postMockMVC(SIGNUP_URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("닉네임이 null이라 회원가입 실패")
			void nullNickname() throws Exception {
				String body = mapper.writeValueAsString(new SignupResponse("user", "1234", null));
				postMockMVC(SIGNUP_URL, body, status().isBadRequest());
			}
		}
		
		@Nested
		@DisplayName("가입 거부")
		class Refusal{
			
			@Test
			@DisplayName("아이디 중복되어 회원가입 실패")
			void overlapping() throws Exception {
				String body = mapper.writeValueAsString(new SignupResponse("overlap", "1234", "중복계정"));
				postMockMVC(SIGNUP_URL, body, status().isConflict());
			}

			@Test
			@DisplayName("아이디가 길이가 짧아 회원가입 실패")
			void idLengthShort() throws Exception {
				String body = mapper.writeValueAsString(new SignupResponse("sho", "1234", "중복계정"));
				postMockMVC(SIGNUP_URL, body, status().isForbidden());
			}
			
			@Test
			@DisplayName("아이디가 길이가 짧아 회원가입 실패")
			void idLengthLong() throws Exception {
				String body = mapper.writeValueAsString(new SignupResponse("longlonglonglonglongl", "1234", "중복계정"));
				postMockMVC(SIGNUP_URL, body, status().isForbidden());
			}
			
			@Test
			@DisplayName("비밀번호가 길이가 짧아 회원가입 실패")
			void passwordLengthShort() throws Exception {
				String body = mapper.writeValueAsString(new SignupResponse("user", "123", "중복계정"));
				postMockMVC(SIGNUP_URL, body, status().isForbidden());
			}
			
			@Test
			@DisplayName("비밀번호가 길이가 짧아 회원가입 실패")
			void passwordLengthLong() throws Exception {
				String body = mapper.writeValueAsString(new SignupResponse("user", "longlonglonglonglongl", "중복계정"));
				postMockMVC(SIGNUP_URL, body, status().isForbidden());
			}
			
			@Test
			@DisplayName("닉네임의 길이가 짧아 회원가입 실패")
			void nicknameLengthShort() throws Exception {
				String body = mapper.writeValueAsString(new SignupResponse("user", "123", "중복계정"));
				postMockMVC(SIGNUP_URL, body, status().isForbidden());
			}
			
			@Test
			@DisplayName("닉네임의 길이가 짧아 회원가입 실패")
			void nicknameLengthLong() throws Exception {
				String body = mapper.writeValueAsString(new SignupResponse("user", "longlonglonglonglongl", "중복계정"));
				postMockMVC(SIGNUP_URL, body, status().isForbidden());
			}
		}
	}
	
}