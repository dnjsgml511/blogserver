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
import com.portfolio.blog.config.security.Role;
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
    	SEARCH_URL = "/admin/userlist";
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
    	params = new LinkedMultiValueMap<String, String>();
    }
    
	@Nested
	@DisplayName("성공")
	class success {
		
		@Nested
		@DisplayName("기본 검색")
		class defaultSearch{
			
			@Test
			@DisplayName("관리자 기본 검색")
			void adminSearch() throws Exception {
				getMockMVC(SEARCH_URL, params, status().isOk(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("매니저 기본 검색")
			void managerSearch() throws Exception {
				getMockMVC(SEARCH_URL, params, status().isOk(), MANAGER_TOKEN);
			}
			
		}
		
		@Nested
		@DisplayName("아이디 검색")
		class IDSearch{
			
			@Test
			@DisplayName("관리자의 아이디 검색")
			void adminIDSearch() throws Exception{
				params.add("id", "admin");
				getMockMVC(SEARCH_URL, params, status().isOk(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("매니저의 아이디 검색")
			void managerIDSearch() throws Exception{
				params.add("id", "admin");
				getMockMVC(SEARCH_URL, params, status().isOk(), MANAGER_TOKEN);
			}
			
		}
		
		@Nested
		@DisplayName("회사명 검색")
		class NicknameSearch{
			
			@Test
			@DisplayName("관리자의 회사명 검색")
			void adminNicknameSearch() throws Exception{
				params.add("nickname", "관리자");
				getMockMVC(SEARCH_URL, params, status().isOk(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("매니저의 회사명 검색")
			void managerNicknameSearch() throws Exception{
				params.add("nickname", "관리자");
				getMockMVC(SEARCH_URL, params, status().isOk(), MANAGER_TOKEN);
			}
			
		}
		
		@Nested
		@DisplayName("등급 검색")
		class GradeSearch{
			
			@Test
			@DisplayName("관리자의 등급 검색")
			void adminGradeSearch() throws Exception{
				params.add("grade", "ROLE_ADMIN");
				getMockMVC(SEARCH_URL, params, status().isOk(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("매니저의 등급 검색")
			void managerGradeSearch() throws Exception{
				params.add("grade", "ROLE_MANAGER");
				getMockMVC(SEARCH_URL, params, status().isOk(), MANAGER_TOKEN);
			}
			
		}
		
	}
	
	@Nested
	@DisplayName("실패")
	class fail {
		
		@Test
		@DisplayName("사용자의 기본검색")
		void userSearch() throws Exception {
			getMockMVC(SEARCH_URL, params, status().isForbidden(), USER_TOKEN);
		}
		
		@Test
		@DisplayName("사용자의 아이디 검색")
		void userIDSearch() throws Exception{
			params.add("id", "admin");
			getMockMVC(SEARCH_URL, params, status().isForbidden(), USER_TOKEN);
		}
		
		@Test
		@DisplayName("사용자의 회사명 검색")
		void userNicknameSearch() throws Exception{
			params.add("nickname", "관리자");
			getMockMVC(SEARCH_URL, params, status().isForbidden(), USER_TOKEN);
		}
		
		@Test
		@DisplayName("사용자의 등급 검색")
		void userGradeSearch() throws Exception{
			params.add("grade", "ROLE_ADMIN");
			getMockMVC(SEARCH_URL, params, status().isForbidden(), USER_TOKEN);
		}
		
		
	}
}