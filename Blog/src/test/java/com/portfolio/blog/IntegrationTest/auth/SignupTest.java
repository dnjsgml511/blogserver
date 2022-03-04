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
import com.portfolio.blog.config.security.Role;
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
    	
//    	list.add(new UserEntity("adminActive", "활동관리자", "1234", Role.ROLE_ADMIN, 1));
//    	list.add(new UserEntity("adminBlock", "비활동관리자", "1234", Role.ROLE_ADMIN));
//    	list.add(new UserEntity("managerActive", "활동매니저", "1234", Role.ROLE_MANAGER, 1));
//    	list.add(new UserEntity("managerBlock", "비활동매니저", "1234", Role.ROLE_MANAGER));
//    	list.add(new UserEntity("userActive", "활동사용자", "1234", Role.ROLE_USER, 1));
//    	list.add(new UserEntity("userBlock", "비활동사용자", "1234", Role.ROLE_USER));
    	
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
	}
	
	@Nested
	@DisplayName("실패")
	class fail {
	}
	
}