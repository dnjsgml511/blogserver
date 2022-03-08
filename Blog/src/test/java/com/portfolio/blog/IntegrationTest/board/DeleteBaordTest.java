package com.portfolio.blog.IntegrationTest.board;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.entitiy.BoardEntity;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.BoardRepository;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@DisplayName("게시판 등록 테스트")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class DeleteBaordTest extends ControllerMockPerform{
	
	@Autowired MockMvc mockMvc;
	@Autowired JwtTokenUtil jwtTokenUtil;
	@Autowired UserRepository userRepository;
	@Autowired BoardRepository boardRepository;
	
	MultiValueMap<String, String> params;
	List<UserEntity> userlist;
	List<BoardEntity> boardlist;
	
	String DELETE_BOARD_URL, ADMIN_TOKEN, MANAGER_TOKEN, USER_TOKEN;

	@BeforeAll
    void beforeAll() {
    	DELETE_BOARD_URL = "/board/delete";
    	
    	ADMIN_TOKEN = jwtTokenUtil.createAdmintoken("admin");
    	MANAGER_TOKEN = jwtTokenUtil.createManagertoken("manager");
    	USER_TOKEN = jwtTokenUtil.createUsertoken("user");
    	
    	userlist = new ArrayList<UserEntity>();
    	userlist.add(new UserEntity("admin", "1234", "관리자", 1, Role.ROLE_ADMIN));
    	userlist.add(new UserEntity("manager", "1234", "매니저", 1, Role.ROLE_MANAGER));
    	userlist.add(new UserEntity("user", "1234", "사용자", 1, Role.ROLE_USER));
    	userlist.add(new UserEntity("user2", "1234", "사용자2", 1, Role.ROLE_USER));
    	userRepository.saveAll(userlist);
    	
    	boardlist = new ArrayList<BoardEntity>();
    	boardlist.add(new BoardEntity( "title1", "content1", "admin", new UserEntity(1)));
    	boardlist.add(new BoardEntity( "title2", "content2", "manager", new UserEntity(2)));
    	boardlist.add(new BoardEntity( "title3", "content3", "user", new UserEntity(3)));
    	boardlist.add(new BoardEntity( "title4", "content4", "user2", new UserEntity(4)));
    	boardRepository.saveAll(boardlist);
    }
    
    @AfterAll
    void afterAll() throws Exception {
    }
 
    @BeforeEach
    void beforeEach() {
    	params = new LinkedMultiValueMap<String, String>();
    }
    
	@Nested
	@DisplayName("성공")
	class success {
		
		@Test
		@DisplayName("관리자 자신 글 삭제")
		void adminMyboardDel() throws Exception{
			params.add("num", "1");
			deleteMockMVC(DELETE_BOARD_URL, params, status().isOk(), ADMIN_TOKEN);
		}
		
		@Test
		@DisplayName("관리자 매니저 글 삭제")
		void adminManagerboardDel() throws Exception{
			params.add("num", "2");
			deleteMockMVC(DELETE_BOARD_URL, params, status().isOk(), ADMIN_TOKEN);
		}
		
		@Test
		@DisplayName("관리자 매니저 글 삭제")
		void adminUserboardDel() throws Exception{
			params.add("num", "3");
			deleteMockMVC(DELETE_BOARD_URL, params, status().isOk(), ADMIN_TOKEN);
		}
		
	}
	
	@Nested
	@DisplayName("실패")
	class fail {
		
		@Test
		@DisplayName("없는 글 삭제")
		void adminDel() throws Exception{
			params.add("num", "100");
			deleteMockMVC(DELETE_BOARD_URL, params, status().isBadRequest(), ADMIN_TOKEN);
		}
		
		@Nested
		@DisplayName("권한 제한")
		class auth {
			
			@Test
			@DisplayName("매니저의 관리자 글 삭제")
			void managerAdminboardDel() throws Exception{
				params.add("num", "1");
				deleteMockMVC(DELETE_BOARD_URL, params, status().isBadRequest(), MANAGER_TOKEN);
			}
			
			@Test
			@DisplayName("사용자의 관리자 글 삭제")
			void userAdminboardDel() throws Exception{
				params.add("num", "1");
				deleteMockMVC(DELETE_BOARD_URL, params, status().isBadRequest(), MANAGER_TOKEN);
			}
			
		}
		
	}
}