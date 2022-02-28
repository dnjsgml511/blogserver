package com.portfolio.blog.util;

public enum ReturnText {

	CHECK_ID("아이디를 다시 확인해주세요"),
	CHECK_PASSWORD("비밀번호를 다시 확인해주세요"),
	CHECK_NICKNAME("닉네임을 다시 확인해주세요"),
	CHECK_ACTIVE("관리자의 승인 후 사용하실 수 있습니다"),
	
	ALREADY("이미 가입 된 아이디입니다"),
	
	USER_ACTIVE("님을 승인하였습니다"),
	USER_BLOCK("님은 블락처리 하였습니다"),
	SIGN_SUCCESS("회원가입이 완료되었습니다"),
	SAVE_SUCCESS("저장 되었습니다");
	
	private final String value;
	
	ReturnText(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
