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
import com.portfolio.blog.data.dto.auth.SigninResponse;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@DisplayName("로그인 테스트")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SigninTest extends ControllerMockPerform{
	
	@Autowired MockMvc mockMvc;
	@Autowired UserRepository userRepository;
	
	MultiValueMap<String, String> params;
	List<UserEntity> list;
	ObjectMapper mapper;
	
	String LOGIN_URL;
	
    @BeforeAll
    void beforeAll() {
    	LOGIN_URL = "/authenticate";
    	
		list = new ArrayList<UserEntity>();
		list.add(new UserEntity("admin", "1234", "관리자", 1, Role.ROLE_ADMIN));
		list.add(new UserEntity("manager", "1234", "매니저", 1, Role.ROLE_MANAGER));
		list.add(new UserEntity("user", "1234", "사용자", 1, Role.ROLE_USER));
		list.add(new UserEntity("blockadmin", "1234", "블록관리자", 0, Role.ROLE_ADMIN));
		list.add(new UserEntity("blockmanager", "1234", "블록매니저", 0, Role.ROLE_MANAGER));
		list.add(new UserEntity("blockuser", "1234", "블록사용자", 0, Role.ROLE_USER));
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
		@DisplayName("관리자 로그인")
		void adminLogin() throws Exception{
			String body = mapper.writeValueAsString(new SigninResponse("admin","1234"));
			postMockMVC(LOGIN_URL, body, status().isOk());
		}
		
		@Test
		@DisplayName("관리자 로그인")
		void managerLogin() throws Exception{
			String body = mapper.writeValueAsString(new SigninResponse("manager","1234"));
			postMockMVC(LOGIN_URL, body, status().isOk());
		}
		
		@Test
		@DisplayName("유저 로그인")
		void userLogin() throws Exception{
			String body = mapper.writeValueAsString(new SigninResponse("user","1234"));
			postMockMVC(LOGIN_URL, body, status().isOk());
		}
		
	}  
	
	@Nested
	@DisplayName("실패")
	class fail {
		
		@Nested
		@DisplayName("블락처리")
		class block{
			
			@Test
			@DisplayName("관리자 블락처리")
			void adminblock() throws Exception{ 
				String body = mapper.writeValueAsString(new SigninResponse("blockadmin","1234"));
				postMockMVC(LOGIN_URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("매니저 블락처리")
			void mamagerblock() throws Exception{ 
				String body = mapper.writeValueAsString(new SigninResponse("blockmanager","1234"));
				postMockMVC(LOGIN_URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("사용자 블락처리")
			void userblock() throws Exception{ 
				String body = mapper.writeValueAsString(new SigninResponse("blockuser","1234"));
				postMockMVC(LOGIN_URL, body, status().isBadRequest());
			}
		}
		
		@Nested
		@DisplayName("입력값 다를 경우")
		class NotEnoughParam{
			
			@Test
			@DisplayName("아이디가 없을 경우")
			void notId() throws Exception{
				String body = mapper.writeValueAsString(new SigninResponse("notid","1234"));
				postMockMVC(LOGIN_URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("관리자 비밀번호가 틀릴 경우")
			void notAdminPassword() throws Exception{
				String body = mapper.writeValueAsString(new SigninResponse("admin","12346"));
				postMockMVC(LOGIN_URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("관리자 비밀번호가 틀릴 경우")
			void notManagerPassword() throws Exception{
				String body = mapper.writeValueAsString(new SigninResponse("manager","12346"));
				postMockMVC(LOGIN_URL, body, status().isBadRequest());
			}
			
			@Test
			@DisplayName("관리자 비밀번호가 틀릴 경우")
			void notUserPassword() throws Exception{
				String body = mapper.writeValueAsString(new SigninResponse("user","12346"));
				postMockMVC(LOGIN_URL, body, status().isBadRequest());
			}
		}
	}
}