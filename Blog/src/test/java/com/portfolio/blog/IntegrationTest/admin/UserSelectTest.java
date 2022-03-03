package com.portfolio.blog.IntegrationTest.admin;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@DisplayName("사용자 전환 테스트")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserSelectTest extends ControllerMockPerform{
	
	@Autowired MockMvc mockMvc;
	@Autowired UserRepository userRepository;
	@Autowired JwtTokenUtil jwtTokenUtil;
	
	ObjectMapper mapper;
	List<UserEntity> list;
	
	String USERSELECT_URL, ADMIN_TOKEN, MANAGER_TOKEN, USER_TOKEN;
	
    @BeforeAll
    void beforeAll() {
    	USERSELECT_URL = "/admin/userselect";
    	ADMIN_TOKEN = jwtTokenUtil.createAdmintoken("adminActive");
    	MANAGER_TOKEN = jwtTokenUtil.createManagertoken("managerActive");
    	USER_TOKEN = jwtTokenUtil.createUsertoken("userActive");
    	
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
		@DisplayName("관리자의 계정 변경 성공")
		void adminChange() throws Exception{
			String body = mapper.writeValueAsString(new UserEntity("userActive"));
			postMockMVC(USERSELECT_URL, body, status().isOk(), ADMIN_TOKEN);
		}
		
		@Test
		@DisplayName("매니저의 계정 변경 성공")
		void managerChange() throws Exception{
			String body = mapper.writeValueAsString(new UserEntity("userActive"));
			postMockMVC(USERSELECT_URL, body, status().isOk(), MANAGER_TOKEN);
		}
		
	}
	
	@Nested
	@DisplayName("실패")
	class fail {
		
		@Nested
		@DisplayName("권한 없을 경우")
		class authOut{

			@Test
			@DisplayName("사용자의 계정 변경 실패")
			void userChange() throws Exception{
				String body = mapper.writeValueAsString(new UserEntity("userActive"));
				postMockMVC(USERSELECT_URL, body, status().isForbidden(), USER_TOKEN);
			}
			
		}
		
		@Nested
		@DisplayName("틀린 아이디 시도")
		class wrongID{
			
			@Test
			@DisplayName("관리자계정 - 실패")
			void wrongIDAdmin() throws Exception{
				String body = mapper.writeValueAsString(new UserEntity(""));
				postMockMVC(USERSELECT_URL, body, status().isUnprocessableEntity(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("매니저계정 - 실패")
			void wrongIDAManager() throws Exception{
				String body = mapper.writeValueAsString(new UserEntity(""));
				postMockMVC(USERSELECT_URL, body, status().isUnprocessableEntity(), MANAGER_TOKEN);
			}
			
		}
		
		@Nested
		@DisplayName("빈 아이디 시도")
		class emptyID{
			
			@Test
			@DisplayName("관리자계정 - 실패")
			void emptyIDAdmin() throws Exception{
				String body = mapper.writeValueAsString(new UserEntity(""));
				postMockMVC(USERSELECT_URL, body, status().isUnprocessableEntity(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("매니저계정 - 실패")
			void emptyIDAManager() throws Exception{
				String body = mapper.writeValueAsString(new UserEntity(""));
				postMockMVC(USERSELECT_URL, body, status().isUnprocessableEntity(), MANAGER_TOKEN);
			}
			
		}
		
		@Nested
		@DisplayName("null 아이디 시도")
		class nullID{
			
			@Test
			@DisplayName("관리자계정 - 실패")
			void nullIDAdmin() throws Exception{
				String body = mapper.writeValueAsString(new UserEntity());
				postMockMVC(USERSELECT_URL, body, status().isUnprocessableEntity(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("매니저계정 - 실패")
			void nullIDManager() throws Exception{
				String body = mapper.writeValueAsString(new UserEntity());
				postMockMVC(USERSELECT_URL, body, status().isUnprocessableEntity(), MANAGER_TOKEN);
			}
			
		}
		
	}
}