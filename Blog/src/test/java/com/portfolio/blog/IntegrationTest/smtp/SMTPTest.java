package com.portfolio.blog.IntegrationTest.smtp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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

import com.portfolio.blog.util.SMTP;

@DisplayName("메일링 테스트")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class SMTPTest{

	@Autowired
	MockMvc mockMvc;

	@BeforeAll
	@Transactional
	void beforeAll() {
	}

	@AfterAll
	void afterAll() {
	}

	@BeforeEach
	void beforeEach() {
	}

	@AfterEach
	void afterEach() {
	}

	@Nested
	@DisplayName("성공")
	class Success {
		
		@Test
		void Test() throws Exception {
			SMTP.sendIdMail("lwh@koreapetroleum.com", "koreapetroleum");
		}
		
	}

	@Nested
	@DisplayName("실패")
	class Fail {
		
		@Test
		void Test() throws Exception {
			SMTP.sendIdMail("lwh2@koreapetroleum.com", "koreapetroleum");
		}
		
	}
}