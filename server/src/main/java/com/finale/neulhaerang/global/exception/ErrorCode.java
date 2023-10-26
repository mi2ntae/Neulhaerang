package com.finale.neulhaerang.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// 인증
	NON_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-001", "잘못된 형식의 토큰입니다."),
	AUTHENTICATION_ENTRY_POINT(HttpStatus.BAD_REQUEST, "A-002", "잘못된 접근입니다."),
	
	// 멤버
	NON_EXIST_MEMBER(HttpStatus.INTERNAL_SERVER_ERROR, "M-001", "해당 사용자가 존재하지 않습니다."),
	
	// 캐릭터 정보
	NON_EXIST_CHARACTERINFO(HttpStatus.INTERNAL_SERVER_ERROR, "C-001", "해당 사용자의 캐릭터 정보가 존재하지 않습니다."),

	// 디바이스
	NON_EXIST_DEVICE(HttpStatus.INTERNAL_SERVER_ERROR, "D-001", "등록되지 않은 디바이스입니다.");

	ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}

	private HttpStatus httpStatus;
	private String errorCode;
	private String message;
}