package com.portfolio.blog.controllerTest.admin;

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
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Admin extends ControllerMockPerform {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	String token;
	UserEntity user;

	@Before
	public void before() throws Exception {

		List<UserEntity> list = new ArrayList<UserEntity>();
		user = new UserEntity("admin", "관리자", "1234", Role.ROLE_ADMIN);
		list.add(user);
		user = new UserEntity("user", "사용자", "1234");
		list.add(user);
		user = new UserEntity("user2", "두번째", "1234");
		list.add(user);
		user = new UserEntity("asdf", "사용자2", "1234");
		list.add(user);
		userRepository.saveAll(list);
	}

	@After
	public void after() throws Exception {
		userRepository.delete(user);
	}

	
	@Test
	public void SearchGradeController() throws Exception {
		
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", Role.ROLE_ADMIN);
		token = jwtTokenUtil.generateToken("ADMIN", claims);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("grade", "ROLE_USER");
		
		getMockMVC("/admin/userlist", params, status().isOk(), token);
	}
	
	@Test
	public void SearchNicknameController() throws Exception {
		
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", Role.ROLE_ADMIN);
		token = jwtTokenUtil.generateToken("ADMIN", claims);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("nickname", "사용자");
		
		getMockMVC("/admin/userlist", params, status().isOk(), token);
	}
	
	@Test
	public void SearchIdController() throws Exception {
		
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", Role.ROLE_ADMIN);
		token = jwtTokenUtil.generateToken("ADMIN", claims);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("id", "user");
		
		getMockMVC("/admin/userlist", params, status().isOk(), token);
	}

	@Test
	public void userlistAdminController() throws Exception {
		
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", Role.ROLE_ADMIN);
		
		token = jwtTokenUtil.generateToken("ADMIN", claims);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

		getMockMVC("/admin/userlist", params, status().isOk(), token);
	}
	
	@Test
	public void userlistNotAdminController() throws Exception {
		
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", Role.ROLE_USER);
		token = jwtTokenUtil.generateToken("USER", claims);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		
		getMockMVC("/admin/userlist", params, status().isForbidden(), token);
	}
}
