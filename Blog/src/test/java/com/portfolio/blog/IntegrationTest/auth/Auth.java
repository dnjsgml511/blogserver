package com.portfolio.blog.IntegrationTest.auth;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.util.ControllerMockPerform;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Auth extends ControllerMockPerform {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	UserRepository userRepository;

	String url;
	UserEntity adminActive;
	UserEntity userActive;
	UserEntity adminBlock;
	UserEntity userBlock;

	@Before
	public void before() throws Exception {
		url = "/authenticate";
		List<UserEntity> list = new ArrayList<UserEntity>();
		adminActive = new UserEntity("adminActive", "활동관리자", "1234", Role.ROLE_ADMIN, 1);
		list.add(adminActive);
		adminBlock = new UserEntity("adminBlock", "비활동관리자", "1234", Role.ROLE_ADMIN);
		list.add(adminBlock);
		userActive = new UserEntity("userActive", "활동사용자", "1234", Role.ROLE_USER, 1);
		list.add(userActive);
		userBlock = new UserEntity("userBlock", "비활동사용자", "1234", Role.ROLE_USER);
		list.add(userBlock);
		userRepository.saveAll(list);
	}

	@After
	public void after() throws Exception {
		userRepository.delete(adminActive);
		userRepository.delete(userActive);
		userRepository.delete(adminBlock);
		userRepository.delete(userBlock);
	}

	@Test
	public void authBlockAdminController() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity("adminBlock", "1234"));
		
		postMockMVC(url, body, status().isForbidden());
	}
	@Test
	public void authAdminController() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity("adminActive", "1234"));

		postMockMVC(url, body, status().isOk());
	}
	
	@Test
	public void authUserController() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity("userActive", "1234"));
		
		postMockMVC(url, body, status().isOk());
	}
	
	@Test
	public void authFailActiveController() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity("userBlock", "1234"));
		
		postMockMVC(url, body, status().isForbidden());
	}

	@Test
	public void authFailIDController() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity("admin1", "1234"));

		postMockMVC(url, body, status().isForbidden());
	}

	@Test
	public void authFailPasswordController() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(new UserEntity("userActive", "12345"));

		postMockMVC(url, body, status().isForbidden());
	}
}
