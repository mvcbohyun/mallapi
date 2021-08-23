package com.kinsight.exception;

public enum BaseResponseCode {
	SUCCESS, // 성공
	ERROR, // 에러
	DATA_IS_NULL,  // NULL
	VALIDATE_REQUIRED, // 필수 체크
	USERCHK,  //중복 유저 있음
	MAILCHKERROR,// 메일 키 없음
	NOUSER, // 사용자 없음
	MAILTIME, // 인증메일 시간 제한
	ROLENOTADMIN,// 사용자 권한 없음
	SEND_MESSAGE_ERROR,// 문자 안감
	CHK_MESSAGE_ERROR
	;
}
