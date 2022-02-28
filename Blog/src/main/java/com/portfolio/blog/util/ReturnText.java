package com.portfolio.blog.util;

public enum RetrunText {

	CHECK_ID("아이디를 다시 확인해주세요"),
	CHECK_PASSWORD("비밀번호를 다시 확인해주세요"),
	CHECK_NICKNAME("닉네임을 다시 확인해주세요"),
	
	ALREADY("이미 가입 된 아이디입니다"),
	
	SIGN_SUCCESS("회원가입이 완료되었습니다"),
	SAVE_SUCCESS("저장 되었습니다");
	
	private final String value;
	
	RetrunText(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
