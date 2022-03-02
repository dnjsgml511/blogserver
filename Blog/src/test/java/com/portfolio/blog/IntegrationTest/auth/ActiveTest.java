package com.portfolio.blog.IntegrationTest.auth;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@DisplayName("권한 테스트")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActiveTest extends ControllerMockPerform{
	
	@Autowired MockMvc mockMvc;
	@Autowired UserRepository userRepository;
	@Autowired JwtTokenUtil jwtTokenUtil;
	
	MultiValueMap<String, String> params;
	List<UserEntity> list;
	ObjectMapper mapper;
	
	String ACCESS_URL, BLOCK_URL, ADMIN_TOKEN, MANAGER_TOKEN, USER_TOKEN;
	
    @BeforeAll
    void beforeAll() {
    	ACCESS_URL = "/useractive";
    	BLOCK_URL = "/userblock";
    	
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
 
    @AfterEach
    void afterEach() {}
    
	@Nested
	@DisplayName("성공")
	class success {
		
	    @BeforeEach
	    void beforeEach() {
	    	mapper = new ObjectMapper();
	    }
		
		@Test
		@DisplayName("승인 성공")
		void access() throws Exception {
			String body = mapper.writeValueAsString(new UserEntity("adminBlock"));
			postMockMVC(ACCESS_URL, body, status().isOk(), ADMIN_TOKEN);
		}
		
		@Test
		@DisplayName("블락 성공")
		void block() throws Exception {
			String body = mapper.writeValueAsString(new UserEntity("adminBlock"));
			postMockMVC(BLOCK_URL, body, status().isOk(), ADMIN_TOKEN);
		}
		
	}
	
	@Nested
	@DisplayName("실패")
	class fail {
	    @BeforeEach
	    void beforeEach() {
	    	mapper = new ObjectMapper();
	    }
		
		@Test
		@DisplayName("매니저 권한 부족으로 인한 승인 실패")
		void managerAccess() throws Exception {
			String body = mapper.writeValueAsString(new UserEntity("managerActive"));
			postMockMVC(ACCESS_URL, body, status().isForbidden(), MANAGER_TOKEN);
		}
		
		@Test
		@DisplayName("매니저 권한 부족으로 인한 블락 실패")
		void managerBlock() throws Exception {
			String body = mapper.writeValueAsString(new UserEntity("managerActive"));
			postMockMVC(BLOCK_URL, body, status().isForbidden(), MANAGER_TOKEN);
		}
		
		@Test
		@DisplayName("사용자 권한 부족으로 인한 승인 실패")
		void userAccess() throws Exception {
			String body = mapper.writeValueAsString(new UserEntity("userActive"));
			postMockMVC(ACCESS_URL, body, status().isForbidden(), USER_TOKEN);
		}
		@Test
		@DisplayName("사용자 권한 부족으로 인한 블락 실패")
		void userBlock() throws Exception {
			String body = mapper.writeValueAsString(new UserEntity("userActive"));
			postMockMVC(BLOCK_URL, body, status().isForbidden(), USER_TOKEN);
		}
	}
}