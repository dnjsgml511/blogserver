package com.portfolio.blog.data.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.dto.auth.SigninResponse;
import com.portfolio.blog.data.dto.auth.SignupResponse;
import com.portfolio.blog.data.dto.auth.UserMapper;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.data.service.AuthService;
import com.portfolio.blog.util.ReturnText;
import com.portfolio.blog.util.SMTP;
import com.portfolio.blog.util.ValidCheck;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	@Autowired
	ValidCheck validCheck;

	@Override
	public String signup(SignupResponse response) throws Exception {

		// 아이디 값 체크
		if (response.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_ID.getValue());
		}
		// 비밀번호 값 체크
		if (response.getPassword() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_PASSWORD.getValue());
		}
		// 닉네임 값 체크
		if (response.getNickname() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_NICKNAME.getValue());
		}
		// 전화번호 값 체크
		if (response.getPhone() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_PHONE.getValue());
		}
		// 이메일 값 체크
		if (response.getEmail() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_EMAIL.getValue());
		}
		// 사업자번호 값 체크
		if (response.getCompanyno() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_COMPANYNO.getValue());
		}

		// 아이디 길이 체크
		if (response.getId().length() < 4 || response.getId().length() > 20) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_ID_LENGTH.getValue());
		}
		// 비밀번호 길이 체크
		if (response.getPassword().length() < 4 || response.getPassword().length() > 20) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_PASSWORD_LENGTH.getValue());
		}
		// 닉네임 길이 체크
		if (response.getNickname().length() < 4 || response.getNickname().length() > 20) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_NICKNAME_LENGTH.getValue());
		}
		// 전화번호 유효성 체크
		if (!validCheck.isPhone(response.getPhone())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_PHONE.getValue());
		}
		// 이메일 유효성 체크
		if (!validCheck.isEmail(response.getEmail())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_EMAIL.getValue());
		}

		List<UserEntity> check = userRepository.findByIdOrNicknameOrEmailOrCompanyno(response.getId(), response.getNickname(), response.getEmail(), response.getCompanyno());
		
		if(check.size() != 0) {
			for (UserEntity data : check) {
				System.out.println(data);
				// 아이디 중복 체크
				if(data.getId().equals(response.getId())) {
					throw new ResponseStatusException(HttpStatus.CONFLICT, ReturnText.ALREADY_ID.getValue());
				}
				// 닉네임 중복 체크
				if(data.getNickname().equals(response.getNickname())) {
					throw new ResponseStatusException(HttpStatus.CONFLICT, ReturnText.ALREADY_NICKNAME.getValue());
				}
				// 이메일 중복 체크
				if(data.getEmail().equals(response.getEmail())) {
					throw new ResponseStatusException(HttpStatus.CONFLICT, ReturnText.ALREADY_EMAIL.getValue());
				}
				// 사업자번호 중복 체크
				if(data.getEmail().equals(response.getEmail())) {
					throw new ResponseStatusException(HttpStatus.CONFLICT, ReturnText.ALREADY_COMPANYNO.getValue());
				}
			}
		}

		// 회원가입
		userRepository.save(new UserEntity(response));
		return ReturnText.SIGN_SUCCESS.getValue();
	}

	@Override
	public UserMapper signin(SigninResponse response, HttpServletRequest request) throws Exception {

		response = new SigninResponse(response);

		UserEntity user = userRepository.findById(response.getId());

		// 아이디 체크
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_ID.getValue());
		}

		// 비밀번호 체크
		if (!user.getPassword().equals(response.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_PASSWORD.getValue());
		}

		// 활성화 체크
		if (user.getActive() == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_ACTIVE.getValue());
		}

		String token = null;
		if (user.getGrade().equals(Role.ROLE_ADMIN)) {
			token = jwtTokenUtil.createAdmintoken(user.getNum());
		} else if (user.getGrade().equals(Role.ROLE_MANAGER)) {
			token = jwtTokenUtil.createManagertoken(user.getNum());
		} else if (user.getGrade().equals(Role.ROLE_USER)) {
			token = jwtTokenUtil.createUsertoken(user.getNum());
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.NOT_HAVE_GRADE.getValue());
		}

		return new UserMapper(user, token);
	}

	@Override
	public String findid(SignupResponse response) throws Exception {

		String email = response.getEmail();

		// 이메일이 공백일 경우
		if (email == null || email.equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_EMAIL.getValue());
		}

		UserEntity user = userRepository.findByEmail(email.toLowerCase());
		// 이메일이 없을 경우
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_EMAIL.getValue());
		}

		SMTP.sendIdMail(response.getEmail(), user.getId());

		return ReturnText.FIND_SUCCESS.getValue();
	}
}
