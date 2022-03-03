package com.portfolio.blog.IntegrationTest.board;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.data.repository.BoardRepository;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.BeforeAllSetting;
import com.portfolio.blog.util.ControllerMockPerform;

@DisplayName("사용자 전환 테스트")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class SearchTest extends ControllerMockPerform{
	
	@Autowired MockMvc mockMvc;
	@Autowired JwtTokenUtil jwtTokenUtil;
	@Autowired UserRepository userRepository;
	@Autowired BoardRepository boardRepository;

	MultiValueMap<String, String> params;
	
	String SEARCH_URL, ADMIN_TOKEN, MANAGER_TOKEN, USER_TOKEN;
	
    @BeforeAll
    void beforeAll(){
    	SEARCH_URL = "/board/search"; 
		ADMIN_TOKEN = jwtTokenUtil.createAdmintoken("adminActive");
		MANAGER_TOKEN = jwtTokenUtil.createManagertoken("managerActive");
		USER_TOKEN = jwtTokenUtil.createUsertoken("userActive");
		
		BeforeAllSetting beforeAllSetting = new BeforeAllSetting();
		userRepository.saveAll(beforeAllSetting.userSetting());
		boardRepository.saveAll(beforeAllSetting.boardSetting());
    }
    
    @AfterAll
    void afterAll() {
    	userRepository.deleteAll();
		boardRepository.deleteAll();
    }
 
    @BeforeEach
    void beforeEach() {
    }
    
	@Nested
	@DisplayName("성공")
	class success {
		
		@Nested
		@DisplayName("관리자가 권한 바꾸지 않았을 경우")
		class Admin{
			
			String ADMIN_URL = SEARCH_URL + "/admin";
			
		    @BeforeEach
		    void beforeEach() {
		    	params = new LinkedMultiValueMap<>();
		    }
			
			@Test
			@DisplayName("모든 파라메터가 있을 경우")
			void search() throws Exception {
				params.add("page", "0");
				params.add("size", "15");
				params.add("search", "next");
				
				getMockMVC(ADMIN_URL, params, status().isOk(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("검색이 없을 경우")
			void noSearch() throws Exception {
				params.add("page", "0");
				params.add("size", "15");
				
				getMockMVC(ADMIN_URL, params, status().isOk(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("페이지가 없을경우 경우")
			void noPage() throws Exception {
				params.add("size", "15");
				params.add("search", "next");
				
				getMockMVC(ADMIN_URL, params, status().isOk(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("사이즈가 없을경우 경우")
			void noSize() throws Exception {
				params.add("page", "0");
				params.add("search", "next");
				
				getMockMVC(ADMIN_URL, params, status().isOk(), ADMIN_TOKEN);
			}
			
		}
		
	}
	
	@Nested
	@DisplayName("실패")
	class fail {
	}
}