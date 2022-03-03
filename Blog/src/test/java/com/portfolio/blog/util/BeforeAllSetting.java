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
		list.add(new UserEntity("adminActive", "활동관리자", "1234", Role.ROLE_ADMIN, 1));
		list.add(new UserEntity("adminBlock", "비활동관리자", "1234", Role.ROLE_ADMIN));
		list.add(new UserEntity("managerActive", "활동매니저", "1234", Role.ROLE_MANAGER, 1));
		list.add(new UserEntity("managerBlock", "비활동매니저", "1234", Role.ROLE_MANAGER));
		list.add(new UserEntity("userActive", "활동사용자", "1234", Role.ROLE_USER, 1));
		list.add(new UserEntity("userBlock", "비활동사용자", "1234", Role.ROLE_USER));

		return list;
	}

	// 테이블 데이터 셋팅
	public List<BoardEntity> boardSetting() {
		List<BoardEntity> list = new ArrayList<BoardEntity>();
		for(int i = 0; i < 5; ++i) {
			BoardEntity set = new BoardEntity("top title" + i, "top content" + i, "user");
			set.setTop(1);
			list.add(set);
		}
		
		for(int i = 0; i < 5; ++i) {
			BoardEntity set = new BoardEntity("title" + i, "content" + i, "other");
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
		return list;
	}
	
}
