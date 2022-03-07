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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.dto.board.InsertBoardResponse;
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
class InsertBoardTest extends ControllerMockPerform{
	
	@Autowired MockMvc mockMvc;
	@Autowired JwtTokenUtil jwtTokenUtil;
	@Autowired UserRepository userRepository;
	@Autowired BoardRepository boardRepository;
	
	ObjectMapper mapper;
	List<UserEntity> list;
	
	String INSERT_BOARD_URL, ADMIN_TOKEN, MANAGER_TOKEN, USER_TOKEN;
	
    @BeforeAll
    void beforeAll() {
    	INSERT_BOARD_URL = "/board/insert";
    	
    	ADMIN_TOKEN = jwtTokenUtil.createAdmintoken("admin");
    	MANAGER_TOKEN = jwtTokenUtil.createManagertoken("manager");
    	USER_TOKEN = jwtTokenUtil.createUsertoken("user");
    	
    	list = new ArrayList<UserEntity>();
    	list.add(new UserEntity("admin", "1234", "관리자", 1, Role.ROLE_ADMIN));
    	list.add(new UserEntity("manager", "1234", "매니저", 1, Role.ROLE_MANAGER));
    	list.add(new UserEntity("user", "1234", "사용자", 1, Role.ROLE_USER));
    	
    	userRepository.saveAll(list);
    }
    
    @AfterAll
    void afterAll() {
    	for (UserEntity data : list) { userRepository.delete(data); }
    }
 
    @BeforeEach
    void beforeEach() {
    	mapper = new ObjectMapper();
    }
    
	@Nested
	@DisplayName("성공")
	class success {
		
		@Nested
		@DisplayName("내용 없는 글 관리자 작성")
		class AllParam{
			@Test
			@DisplayName("관리자의 게시물 등록")
			void adminInsert() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("admin title", "content", 1));
				postMockMVC(INSERT_BOARD_URL, body, status().isOk(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("관리자의 게시물 등록")
			void manager_Insert() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("manager title", "content", 2));
				postMockMVC(INSERT_BOARD_URL, body, status().isOk(), MANAGER_TOKEN);
			}
			
			@Test
			@DisplayName("관리자의 게시물 등록")
			void userInsert() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("user title", "content", 3));
				postMockMVC(INSERT_BOARD_URL, body, status().isOk(), USER_TOKEN);
			}
		}
		
		@Nested
		@DisplayName("내용 없는 글 관리자 작성")
		class NoTitle{
			
			@Test
			@DisplayName("관리자의 게시물 타이틀 null 등록")
			void nullAdminInsert() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("title", null, 1));
				postMockMVC(INSERT_BOARD_URL, body, status().isOk(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("매니저의 게시물  타이틀 null 등록")
			void nullManager_Insert() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("title", null, 2));
				postMockMVC(INSERT_BOARD_URL, body, status().isOk(), MANAGER_TOKEN);
			}
			
			@Test
			@DisplayName("사용자의 게시물 타이틀 null 등록")
			void nullUserInsert() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("title", null, 3));
				postMockMVC(INSERT_BOARD_URL, body, status().isOk(), USER_TOKEN);
			}
			
			@Test
			@DisplayName("관리자의 게시물 빈 타이틀 등록")
			void adminInsert() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("title", "", 1));
				postMockMVC(INSERT_BOARD_URL, body, status().isOk(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("매니저의 게시물 빈 타이틀 등록")
			void manager_Insert() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("title", "", 2));
				postMockMVC(INSERT_BOARD_URL, body, status().isOk(), MANAGER_TOKEN);
			}
			
			@Test
			@DisplayName("사용자의 게시물 빈 타이틀 등록")
			void userInsert() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("title", "", 3));
				postMockMVC(INSERT_BOARD_URL, body, status().isOk(), USER_TOKEN);
			}
		}
	}
	
	@Nested
	@DisplayName("실패")
	class fail {
		
		@Nested
		@DisplayName("타이틀 없는 글 관리자 작성")
		class NoTitle{
			
			@Test
			@DisplayName("관리자의 게시물 타이틀 null 등록")
			void nullAdminInsert() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse(null, "content", 1));
				postMockMVC(INSERT_BOARD_URL, body, status().isBadRequest(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("매니저의 게시물  타이틀 null 등록")
			void nullManager_Insert() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse(null, "content", 2));
				postMockMVC(INSERT_BOARD_URL, body, status().isBadRequest(), MANAGER_TOKEN);
			}
			
			@Test
			@DisplayName("사용자의 게시물 타이틀 null 등록")
			void nullUserInsert() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse(null, "content", 3));
				postMockMVC(INSERT_BOARD_URL, body, status().isBadRequest(), USER_TOKEN);
			}
			
			@Test
			@DisplayName("관리자의 게시물 빈 타이틀 등록")
			void adminInsert() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("", "content", 1));
				postMockMVC(INSERT_BOARD_URL, body, status().isBadRequest(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("매니저의 게시물 빈 타이틀 등록")
			void manager_Insert() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("", "content", 2));
				postMockMVC(INSERT_BOARD_URL, body, status().isBadRequest(), MANAGER_TOKEN);
			}
			
			@Test
			@DisplayName("사용자의 게시물 빈 타이틀 등록")
			void userInsert() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("", "content", 3));
				postMockMVC(INSERT_BOARD_URL, body, status().isBadRequest(), USER_TOKEN);
			}
		}
		
		@Nested
		@DisplayName("잘못된 아이디")
		class WrongId{
			
			@Test
			@DisplayName("잘못된 아이디")
			void wrongId() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("title", "content", 10));
				postMockMVC(INSERT_BOARD_URL, body, status().isBadRequest(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("사용자 null")
			void nullId() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("title", "content", null));
				postMockMVC(INSERT_BOARD_URL, body, status().isBadRequest(), ADMIN_TOKEN);
			}
		}
		
		@Nested
		@DisplayName("토큰과 유저가 다를경우")
		class idCheck{
			
			@Test
			@DisplayName("관리자 -> 유저")
			void adminToUser() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("title", "content", 3));
				postMockMVC(INSERT_BOARD_URL, body, status().isBadRequest(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("관리자 -> 매니저")
			void adminToManager() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("title", "content", 2));
				postMockMVC(INSERT_BOARD_URL, body, status().isBadRequest(), ADMIN_TOKEN);
			}
			
			@Test
			@DisplayName("매니저 -> 관리자")
			void managerToAdmin() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("title", "content", 1));
				postMockMVC(INSERT_BOARD_URL, body, status().isBadRequest(), MANAGER_TOKEN);
			}
			
			@Test
			@DisplayName("매니저 -> 유저")
			void managerToUser() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("title", "content", 3));
				postMockMVC(INSERT_BOARD_URL, body, status().isBadRequest(), MANAGER_TOKEN);
			}
			
			@Test
			@DisplayName("유저 -> 관리자")
			void userToAdmin() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("title", "content", 1));
				postMockMVC(INSERT_BOARD_URL, body, status().isBadRequest(), USER_TOKEN);
			}
			
			@Test
			@DisplayName("유저 -> 매니저")
			void userToManager() throws Exception{
				String body = mapper.writeValueAsString(new InsertBoardResponse("title", "content", 2));
				postMockMVC(INSERT_BOARD_URL, body, status().isBadRequest(), USER_TOKEN);
			}
		}
	}
}