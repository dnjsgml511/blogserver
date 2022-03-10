package com.portfolio.blog.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.entitiy.BoardEntity;
import com.portfolio.blog.data.entitiy.UserEntity;

public class BeforeAllSetting {

	@Autowired JwtTokenUtil jwtTokenUtil;

	// 유저 데이터 셋팅
	public List<UserEntity> userSetting() {
		List<UserEntity> list = new ArrayList<UserEntity>();
		list.add(new UserEntity("adminActive", "1234", "활동관리자", Role.ROLE_ADMIN, 1));
		list.add(new UserEntity("adminBlock", "1234", "비활동관리자", Role.ROLE_ADMIN));
		list.add(new UserEntity("managerActive", "1234", "활동매니저", Role.ROLE_MANAGER, 1));
		list.add(new UserEntity("managerBlock", "1234", "비활동매니저", Role.ROLE_MANAGER));
		list.add(new UserEntity("userActive", "1234", "활동사용자", Role.ROLE_USER, 1));
		list.add(new UserEntity("userBlock", "1234", "비활동사용자", Role.ROLE_USER));
		
		return list;
	}

	// 테이블 데이터 셋팅
	public List<BoardEntity> boardSetting() {
		List<BoardEntity> list = new ArrayList<BoardEntity>();

		String admin = "admin", manager = "manager", user = "user";
		UserEntity adminEntity = new UserEntity(1);
		UserEntity managerEntity = new UserEntity(2);
		UserEntity userEntity = new UserEntity(3);

		// 기본 게시물
		for (int i = 0; i < 20; i++) {
			BoardEntity set = new BoardEntity("title" + i, "content" + i, admin, adminEntity);
			list.add(set);
			set = new BoardEntity("title" + i, "content" + i, manager, managerEntity);
			list.add(set);
			set = new BoardEntity("title" + i, "content" + i, user, userEntity);
			list.add(set);
		}

		// 상단 고정 게시물
		for (int i = 0; i < 10; i++) {
			BoardEntity set = new BoardEntity("title" + i, "content" + i, admin, adminEntity);
			set.setTop(1);
			list.add(set);
			set = new BoardEntity("title" + i, "content" + i, manager, managerEntity);
			set.setTop(1);
			list.add(set);
			set = new BoardEntity("title" + i, "content" + i, user, userEntity);
			set.setTop(1);
			list.add(set);
		}

		// 숨긴 게시물
		for (int i = 0; i < 10; i++) {
			BoardEntity set = new BoardEntity("title" + i, "content" + i, admin, adminEntity);
			set.setHide(1);
			list.add(set);
			set = new BoardEntity("title" + i, "content" + i, manager, managerEntity);
			set.setHide(1);
			list.add(set);
			set = new BoardEntity("title" + i, "content" + i, user, userEntity);
			set.setHide(1);
			list.add(set);
		}

		return list;

	}

}
