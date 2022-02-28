package com.portfolio.blog.IntegrationTest.board;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
import com.portfolio.blog.data.controller.BoardController;
import com.portfolio.blog.data.entitiy.BoardEntity;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.BoardRepository;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Search extends ControllerMockPerform{

	@Autowired
    MockMvc mockMvc;
	
	@Autowired
	BoardController boardController;
	
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	String token;
	UserEntity user;
	List<UserEntity> userlist;
	
	@Before
	public void before() throws Exception {
		
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", Role.ROLE_ADMIN);
		token = jwtTokenUtil.generateToken("ADMIN", claims);
		
		userlist = new ArrayList<UserEntity>();
		user = new UserEntity("admin", "관리자", "1234", Role.ROLE_ADMIN);
		userlist.add(user);
		user = new UserEntity("user", "사용자", "1234");
		userlist.add(user);
		user = new UserEntity("user2", "두번째", "1234");
		userlist.add(user);
		user = new UserEntity("asdf", "사용자2", "1234");
		userlist.add(user);
		userRepository.saveAll(userlist);
		
		List<BoardEntity> list = new ArrayList<BoardEntity>();
		for(int i = 0; i < 5; ++i) {
			BoardEntity set = new BoardEntity("top title" + i, "top content" + i, "user");
			set.setTop(1);
			list.add(set);
		}
		
		for(int i = 0; i < 5; ++i) {
			BoardEntity set = new BoardEntity("title" + i, "content" + i, "user");
			list.add(set);
		}
		for(int i = 0; i < 5; ++i) {
			BoardEntity set = new BoardEntity("title" + i, "content" + i, "user");
			set.setHide(1);
			list.add(set);
		}
		
		for(int i = 0; i < 5; ++i) {
			BoardEntity set = new BoardEntity("title" + i, "content" + i, "user");
			list.add(set);
		}
		for(int i = 0; i < 5; ++i) {
			BoardEntity set = new BoardEntity("nexttitle" + i, "content" + i, "user");
			list.add(set);
		}
		boardRepository.saveAll(list);
	}

	@After
	public void after() throws Exception {
		boardRepository.deleteAll();
	}
	
	@Test
	public void boardNoPageSearchController() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("size", "15");
		
		getMockMVC("/board/search/user", params, status().isOk(), token);
	}
	
	@Test
	public void boardNoSizeSearchController() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", "0");
		
		getMockMVC("/board/search/user", params, status().isOk(), token);
	}
	
	@Test
	public void boardNoSearchController() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", "0");
		params.add("size", "15");
		
		getMockMVC("/board/search/user", params, status().isOk(), token);
	}
	
	@Test
	public void boardSearchController() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", "0");
		params.add("size", "15");
		params.add("search", "next");
		
		getMockMVC("/board/search/user", params, status().isOk(), token);
	}
	
	@Test
	public void boardTopSearchController() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("size", "3");
		
		getMockMVC("/board/topsearch/user", params, status().isOk(), token);
		
	}

}
