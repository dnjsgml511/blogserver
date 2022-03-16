package com.portfolio.blog.util;

public enum ReturnText {

	CHECK_IDX("번호를 다시 확인 해주세요"),
	CHECK_ID("아이디를 다시 확인 해주세요"),
	CHECK_PASSWORD("비밀번호를 다시 확인 해주세요"),
	CHECK_NICKNAME("닉네임을 다시 확인 해주세요"),
	CHECK_PHONE("전화번호를 다시 확인 해주세요"),
	CHECK_EMAIL("이메일을 다시 확인 해주세요"),
	CHECK_ID_LENGTH("아이디의 길이를 확인 해주세요"),
	CHECK_PASSWORD_LENGTH("비밀번호의 길이를 다시 확인 해주세요"),
	CHECK_NICKNAME_LENGTH("닉네임의 길이를 다시 확인 해주세요"),
	CHECK_ACTIVE("관리자의 승인 후 사용하실 수 있습니다"),
	CHECK_USER("회원을 확인해주세요"),
	CHECK_DATA("입력값을 확인해주세요"),
	
	ALREADY_ID("이미 가입 된 아이디입니다"),
	ALREADY_NICKNAME("이미 가입 된 회사명입니다"),
	
	USER_UPDATE("님의 정보를 수정하였습니다"),
	SIGN_SUCCESS("회원가입이 완료되었습니다.\n관리자의 승인 후 사용 가능합니다"),
	SAVE_SUCCESS("저장 되었습니다"),
	DELETE_SUCCESS("삭제 되었습니다"),
	FIND_SUCCESS("이메일로 발송되었습니다"),
	
	SELECT_FAIL("선택한 값을 확인하세요"),
	
	NOT_NORMAL_ROUTE("정상적인 경로로 들어온 사용자가 아닙니다"),
	NOT_HAVE_GRADE("권한이 없습니다");
	
	private final String value;
	
	ReturnText(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
