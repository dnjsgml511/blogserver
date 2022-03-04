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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.data.entitiy.BoardEntity;
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
class UpdateTest extends ControllerMockPerform{
	
	@Autowired MockMvc mockMvc;
	@Autowired JwtTokenUtil jwtTokenUtil;
	@Autowired UserRepository userRepository;
	@Autowired BoardRepository boardRepository;

	ObjectMapper mapper;
	
	String UPDATE_URL, ADMIN_TOKEN, MANAGER_TOKEN, USER_TOKEN;
	
    @BeforeAll
    @Transactional
    void beforeAll(){
    	UPDATE_URL = "/board/update"; 
		ADMIN_TOKEN = jwtTokenUtil.createAdmintoken("adminActive");
		MANAGER_TOKEN = jwtTokenUtil.createManagertoken("managerActive");
		USER_TOKEN = jwtTokenUtil.createUsertoken("user");
		
		BeforeAllSetting beforeAllSetting = new BeforeAllSetting();
		userRepository.saveAll(beforeAllSetting.userSetting());
		boardRepository.saveAll( beforeAllSetting.boardSetting());
    }
    
    @AfterAll
    void afterAll() {
    }
 
    @BeforeEach
    void beforeEach() {
    }
    
	@Nested
	@DisplayName("성공")
	class success {
		
		@Nested
		@DisplayName("게시판 수정")
		class update{
			
			@Nested
			@DisplayName("관리자")
			class admin{
				
				@BeforeEach
				void beforeEach() {
					mapper = new ObjectMapper();
				}
				
//				@Test
//				@DisplayName("모든 파라메터 있을 경우")
//				void allparam() throws Exception {
//					String body = mapper.writeValueAsString(new BoardEntity(1, "update title", "update content", "update writer"));
//					patchMockMVC(UPDATE_URL, body, status().isOk(), ADMIN_TOKEN);
//				}
				
//				@Test
//				@DisplayName("타이틀 없을 경우")
//				void noTitle() throws Exception {
//					patchMockMVC(UPDATE_URL, params, status().isOk(), ADMIN_TOKEN);
//				}
//				
//				
//				@Test
//				@DisplayName("타이틀 null일 경우")
//				void nullTitle() throws Exception {
//					params.add("idx", "1");
//					params.add("content", "update content");
//					params.add("writer", "update writer");
//					
//					patchMockMVC(UPDATE_URL, params, status().isOk(), ADMIN_TOKEN);
//				}
//				
//				
//				@Test
//				@DisplayName("내용 없을 경우")
//				void noContent() throws Exception {
//					params.add("idx", "1");
//					params.add("title", "update title");
//					params.add("content", "");
//					params.add("writer", "update writer");
//					
//					patchMockMVC(UPDATE_URL, params, status().isOk(), ADMIN_TOKEN);
//				}
//				
//				@Test
//				@DisplayName("내용 nul일 경우")
//				void nullContent() throws Exception {
//					params.add("idx", "1");
//					params.add("title", "update title");
//					params.add("writer", "update writer");
//					
//					patchMockMVC(UPDATE_URL, params, status().isOk(), ADMIN_TOKEN);
//				}
//				
//				@Test
//				@DisplayName("작성자 없을 경우")
//				void noWriter() throws Exception {
//					params.add("idx", "1");
//					params.add("title", "update title");
//					params.add("content", "update content");
//					params.add("writer", "");
//					
//					patchMockMVC(UPDATE_URL, params, status().isOk(), ADMIN_TOKEN);
//				}
//				
//				@Test
//				@DisplayName("작성자 null일 경우")
//				void nullWriter() throws Exception {
//					params.add("idx", "1");
//					params.add("title", "update title");
//					params.add("content", "update content");
//					
//					patchMockMVC(UPDATE_URL, params, status().isOk(), ADMIN_TOKEN);
//				}
			}
			
			
			
		}
		
	}
	
	@Nested
	@DisplayName("실패")
	class fail {
		
		@Nested
		@DisplayName("게시판 수정")
		class update{
			
		}
	}
}