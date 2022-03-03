package com.portfolio.blog.IntegrationTest.board;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.BeforeAllSetting;
import com.portfolio.blog.util.ControllerMockPerform;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
class Search extends ControllerMockPerform{

	@Autowired MockMvc mockMvc;
	@Autowired BeforeAllSetting beforeAllSetting;
	@Autowired UserRepository userRepository;
	
	@BeforeAll
	public void before() throws Exception {
		userRepository.saveAll(beforeAllSetting.userSetting());
		List<UserEntity> test = userRepository.findAll();
		for (UserEntity data : test) {
			System.out.println(data);
		}
	}

	@AfterAll
	public void after() throws Exception {
	}
	
	@Nested
	@DisplayName("검색 테스트")
	public class SearchTest {
		
		@Test
		@DisplayName("관리자 기본검색")
		void defaultAdminSearch() throws Exception {
			List<UserEntity> test = userRepository.findAll();
			for (UserEntity data : test) {
				System.out.println(data);
			}
		}
		
		@Test
		@DisplayName("유저 기본검색")
		void defaultUserSearch() throws Exception {
			System.out.println("test2");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Test
//	@DisplayName("검색x, 사용자변경")
//	public void noSearchChangeUserController() throws Exception {
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("page", "0");
//		params.add("size", "15");
//		
//		
//		getMockMVC("/board/search/user", params, status().isOk(), ADMIN_TOKEN);
//	}
//	
//	@Test
//	@DisplayName("관리자x, 검색x")
//	public void noAdminNoSearchChangeUserController() throws Exception {
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("page", "0");
//		params.add("size", "15");
//		
//		getMockMVC("/board/search/user", params, status().isOk(), USER_TOKEN);
//	}
//	
//	@Test
//	@DisplayName("검색x, 사용자 미변경")
//	public void noSearchNotChangeUserController() throws Exception {
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("page", "0");
//		params.add("size", "15");
//		
//		getMockMVC("/board/search/admin", params, status().isOk(), ADMIN_TOKEN);
//	}
//	
//	@Test
//	@DisplayName("관리자x, 검색x")
//	public void noAdminNoSearchNotChangeUserController() throws Exception {
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("page", "0");
//		params.add("size", "15");
//		
//		getMockMVC("/board/search/admin", params, status().isOk(), USER_TOKEN);
//	}
//	
//	@Test
//	@DisplayName("검색x, 사용자 미변경, 페이지 없음")
//	public void noSearchNotChangeUserNoPageController() throws Exception {
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("size", "15");
//		
//		getMockMVC("/board/search/admin", params, status().isOk(), ADMIN_TOKEN);
//	}
//	
//	@Test
//	@DisplayName("검색x, 사용자 미변경, 페이지크기 없음")
//	public void noSearchNotChangeUserNoSizeController() throws Exception {
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("page", "0");
//		
//		getMockMVC("/board/search/admin", params, status().isOk(), ADMIN_TOKEN);
//	}
//	
//	
//	@Test
//	@DisplayName("검색x, 사용자 미변경, 페이지크기 없음, 사이즈 없음")
//	public void noSearchNotChangeUserNoPageNoSizeController() throws Exception {
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		
//		getMockMVC("/board/search/admin", params, status().isOk(), ADMIN_TOKEN);
//	}
	
	
	
	
	
//	
//	
//	
//	
//	
//	@Test
//	public void boardNoPageSearchController() throws Exception {
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("size", "15");
//		
//		getMockMVC("/board/search", params, status().isOk(), token);
//	}
//	
//	@Test
//	public void boardNoSizeSearchController() throws Exception {
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("page", "0");
//		
//		getMockMVC("/board/search", params, status().isOk(), token);
//	}
//	
//	@Test
//	public void boardNoSearchAllDataController() throws Exception {
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("page", "0");
//		params.add("size", "15");
//		
//		getMockMVC("/board/search/admin", params, status().isOk(), token);
//	}
//	
//	@Test
//	public void boardNoSearchController() throws Exception {
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("page", "0");
//		params.add("size", "15");
//		
//		getMockMVC("/board/search/user", params, status().isOk(), token);
//	}
//	
//	@Test
//	public void boardSearchController() throws Exception {
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("page", "0");
//		params.add("size", "15");
//		params.add("search", "next");
//		
//		getMockMVC("/board/search/user", params, status().isOk(), token);
//	}
//	
//	@Test
//	public void boardTopSearchController() throws Exception {
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("size", "3");
//		
//		getMockMVC("/board/topsearch", params, status().isOk(), token);
//		
//	}

}
