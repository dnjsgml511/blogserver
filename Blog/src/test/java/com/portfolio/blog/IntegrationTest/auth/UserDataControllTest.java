package com.portfolio.blog.IntegrationTest.auth;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.dto.auth.UserControllResponse;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@DisplayName("사용자 속성 변경 테스트")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDataControllTest extends ControllerMockPerform{
	
	@Autowired MockMvc mockMvc;
	@Autowired UserRepository userRepository;
	@Autowired JwtTokenUtil jwtTokenUtil;
	
	MultiValueMap<String, String> params;
	List<UserEntity> list;
	ObjectMapper mapper;
	
	String USERCONTROLL_URL, BLOCK_URL, ADMIN_TOKEN, MANAGER_TOKEN, USER_TOKEN;
	
    @BeforeAll
    void beforeAll() {
    	USERCONTROLL_URL = "/user/controll";
    	
    	ADMIN_TOKEN = jwtTokenUtil.createAdmintoken("admin");
    	MANAGER_TOKEN = jwtTokenUtil.createManagertoken("manager");
    	USER_TOKEN = jwtTokenUtil.createUsertoken("user");
    }
    
    @BeforeEach
    void beforeEach() {
    	mapper = new ObjectMapper();
    	list = new ArrayList<UserEntity>();
    	list.add(new UserEntity("user","1234","사용자"));
    	list.add(new UserEntity("active","1234","사용자", 1));
    	userRepository.saveAll(list);
    	
    }
 
    @AfterEach
    void afterEach() {
    	for (UserEntity data : list) userRepository.delete(data);
    }
    
	@Nested
	@DisplayName("성공")
	class success {
		
		@Nested
		@DisplayName("사용자 활성화")
		class active {
			@Test
			@DisplayName("관리자의 사용자 수락 성공")
			void adminAccess() throws Exception {
				String body = mapper.writeValueAsString(new UserControllResponse("user", 1));
				postMockMVC(USERCONTROLL_URL, body, status().isOk(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("관리자의 사용자 블락 성공")
			void adminBlock() throws Exception{
				String body = mapper.writeValueAsString(new UserControllResponse("active", 0));
				postMockMVC(USERCONTROLL_URL, body, status().isOk(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("매니저의 사용자 수락 성공")
			void mamagerAccess() throws Exception {
				String body = mapper.writeValueAsString(new UserControllResponse("user", 1));
				postMockMVC(USERCONTROLL_URL, body, status().isOk(), MANAGER_TOKEN);
			}
			
			@Test
			@DisplayName("매니저의 사용자 블락 성공")
			void managerBlock() throws Exception{
				String body = mapper.writeValueAsString(new UserControllResponse("active", 0));
				postMockMVC(USERCONTROLL_URL, body, status().isOk(), MANAGER_TOKEN);
			}
		}
		
		@Nested
		@DisplayName("권한 부여")
		class auth{
			@Test
			@DisplayName("관리자의 사용자 관리자 권한 부여")
			void adminAuth() throws Exception {
				String body = mapper.writeValueAsString(new UserControllResponse("user", Role.ROLE_ADMIN));
				postMockMVC(USERCONTROLL_URL, body, status().isOk(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("관리자의 사용자 매니저 권한 부여")
			void managerAuth() throws Exception {
				String body = mapper.writeValueAsString(new UserControllResponse("user", Role.ROLE_MANAGER));
				postMockMVC(USERCONTROLL_URL, body, status().isOk(), ADMIN_TOKEN);
			}
		}
	}
	
	@Nested
	@DisplayName("실패")
	class fail {
		
		@Nested
		@DisplayName("권한이 없이 시도 할 경우")
		class notAuth{
			@Test
			@DisplayName("관리자의 사용자 관리자 권한 부여")
			void adminAuth() throws Exception {
				String body = mapper.writeValueAsString(new UserControllResponse("user", Role.ROLE_ADMIN));
				postMockMVC(USERCONTROLL_URL, body, status().isForbidden(), MANAGER_TOKEN);
			}
			
			@Test
			@DisplayName("관리자의 사용자 매니저 권한 부여")
			void managerAuth() throws Exception {
				String body = mapper.writeValueAsString(new UserControllResponse("user", Role.ROLE_MANAGER));
				postMockMVC(USERCONTROLL_URL, body, status().isForbidden(), MANAGER_TOKEN);
			}
			
			@Test
			@DisplayName("토큰이 없을 경우")
			void notToken() throws Exception {
				String body = mapper.writeValueAsString(new UserControllResponse("user", Role.ROLE_MANAGER));
				postMockMVC(USERCONTROLL_URL, body, status().isUnauthorized());
			}
		}
		
		@Nested
		@DisplayName("입력값이 부족 할  경우")
		class NotEnoughParam{
			@Test
			@DisplayName("아이디 값이 비었을 경우")
			void emptyId() throws Exception {
				String body = mapper.writeValueAsString(new UserControllResponse("", Role.ROLE_ADMIN));
				postMockMVC(USERCONTROLL_URL, body, status().isBadRequest(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("아이디 값이 null일 경우")
			void nullId() throws Exception {
				String body = mapper.writeValueAsString(new UserControllResponse(null, Role.ROLE_ADMIN));
				postMockMVC(USERCONTROLL_URL, body, status().isBadRequest(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("해당 아이디가 없을 경우")
			void notId() throws Exception {
				String body = mapper.writeValueAsString(new UserControllResponse("notid", Role.ROLE_ADMIN));
				postMockMVC(USERCONTROLL_URL, body, status().isNotFound(), ADMIN_TOKEN);
			}
		}
		
	}
}