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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@DisplayName("사용자 검색 테스트")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserSearchTest extends ControllerMockPerform{
	
	@Autowired MockMvc mockMvc;
	@Autowired UserRepository userRepository;
	@Autowired JwtTokenUtil jwtTokenUtil;
	
	MultiValueMap<String, String> params;
	List<UserEntity> list;
	
	String SEARCH_URL, ADMIN_TOKEN, MANAGER_TOKEN, USER_TOKEN;
	
    @BeforeAll
    void beforeAll() {
    }
    
    @AfterAll
    void afterAll() {
    	for (UserEntity data : list) userRepository.delete(data);
    }
 
    @BeforeEach
    void beforeEach() {
    	params = new LinkedMultiValueMap<String, String>();
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