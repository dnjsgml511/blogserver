package com.portfolio.blog.IntegrationTest.admin;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserSelect extends ControllerMockPerform {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	String token;
	UserEntity user;
	List<UserEntity> list;

	@Before
	public void before() throws Exception {

		list = new ArrayList<UserEntity>();
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
		for (UserEntity data : list) {
			userRepository.delete(data);
		}
	}

	@Test
	public void changeUser() throws Exception {

		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", Role.ROLE_ADMIN);
		token = jwtTokenUtil.generateToken("ADMIN", claims);

		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity("asdf"));

		postMockMVC("/admin/userselect", body, status().isOk(), token);
	}

	@Test
	public void changeIdEmptyUser() throws Exception {

		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", Role.ROLE_ADMIN);
		token = jwtTokenUtil.generateToken("ADMIN", claims);

		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity(""));

		postMockMVC("/admin/userselect", body, status().isUnprocessableEntity(), token);
	}
	
	@Test
	public void changeIdNullUser() throws Exception {
		
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", Role.ROLE_ADMIN);
		token = jwtTokenUtil.generateToken("ADMIN", claims);
		
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity());
		
		postMockMVC("/admin/userselect", body, status().isUnprocessableEntity(), token);
	}

}
