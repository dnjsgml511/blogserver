package com.portfolio.blog.IntegrationTest.auth;

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
public class Active extends ControllerMockPerform {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	String admin_token;
	String user_token;
	UserEntity admin;
	UserEntity user;

	@Before
	public void before() throws Exception {
		
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", Role.ROLE_ADMIN);
		admin_token = jwtTokenUtil.generateToken("ADMIN", claims);
		
		
		claims = new HashMap<String, Object>();
		claims.put("role", Role.ROLE_USER);
		user_token = jwtTokenUtil.generateToken("USER", claims);
		
		List<UserEntity> list = new ArrayList<UserEntity>();
		admin = new UserEntity("admin", "새로운관리자", "1234", Role.ROLE_ADMIN);
		list.add(admin);
		user = new UserEntity("user", "사용자", "1234", Role.ROLE_USER);
		list.add(user);
		userRepository.saveAll(list);
		
	}

	@After
	public void after() throws Exception {
		
		UserEntity admin = userRepository.findById("admin");
		UserEntity user=  userRepository.findById("user");
		
		System.out.println(admin);
		System.out.println(user);
		
		int idx = admin.getNum();
		userRepository.deleteById(idx);
		idx = user.getNum();
		userRepository.deleteById(idx);
	}

	// 관리자 승인
	@Test
	public void userActiveController() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity("admin"));

		postMockMVC("/useractive", body, status().isOk(), admin_token);
	}
	
	// 비관리자의 승인
	@Test
	public void notAdminUserActiveController() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity("USER"));

		postMockMVC("/useractive", body, status().isForbidden(), user_token);
	}
	
	// 관리자 블락
	@Test
	public void userBlockController() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity("admin"));
		
		postMockMVC("/userblock", body, status().isOk(), admin_token);
	}
	
	// 비관리자의 블락
	@Test
	public void notAdminUserBlockController() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity("USER"));
		
		postMockMVC("/userblock", body, status().isForbidden(), user_token);
	}
}
