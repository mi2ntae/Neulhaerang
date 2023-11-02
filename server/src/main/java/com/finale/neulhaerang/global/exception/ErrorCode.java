package com.finale.neulhaerang.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// 인증
	NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-001", "잘못된 형식의 토큰입니다."),
	AUTHENTICATION_ENTRY_POINT(HttpStatus.BAD_REQUEST, "A-002", "잘못된 접근입니다."),
	EXPIRED_AUTH(HttpStatus.UNAUTHORIZED, "A-003", "유효하지 않은 인증 정보입니다. 다시 로그인해주세요."),
	ACCESS_FORBIDDEN(HttpStatus.FORBIDDEN, "A-004", "해당 요청에 대한 권한이 존재하지 않습니다."),

	// 멤버
	NOT_EXIST_MEMBER(HttpStatus.INTERNAL_SERVER_ERROR, "M-001", "해당 사용자가 존재하지 않습니다."),

	// 캐릭터 정보
	NOT_EXIST_CHARACTERINFO(HttpStatus.INTERNAL_SERVER_ERROR, "C-001", "해당 사용자의 캐릭터 정보가 존재하지 않습니다."),

	// 디바이스
	NOT_EXIST_DEVICE(HttpStatus.INTERNAL_SERVER_ERROR, "D-001", "등록되지 않은 디바이스입니다."),

	// 투두리스트
	INVALID_TODO_DATE(HttpStatus.BAD_REQUEST, "T-001", "날짜가 유효하지 않습니다."),
	NOT_EXIST_TODO(HttpStatus.BAD_REQUEST, "T-002", "해당 체크리스트가 존재하지 않습니다."),

	// 루틴
	NOT_EXIST_ALARM_TIME(HttpStatus.BAD_REQUEST, "R-001", "알람을 받는 경우 알람 시간을 지정해야합니다."),
	INVALID_REPEATED_DATE(HttpStatus.BAD_REQUEST, "R-002", "반복 날짜를 담은 리스트는 길이가 7이어야 합니다."),
	ALREADY_REMOVE_DAILY_ROUTINE(HttpStatus.BAD_REQUEST, "R-003", "해당 데일리 루틴은 이미 삭제되었습니다."),
	NOT_EXIST_DAILY_ROUTINE(HttpStatus.BAD_REQUEST, "R-004", "해당 id를 가진 데일리 루틴은 존재하지 않습니다."),
	;

	ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}

	private HttpStatus httpStatus;
	private String errorCode;
	private String message;
}