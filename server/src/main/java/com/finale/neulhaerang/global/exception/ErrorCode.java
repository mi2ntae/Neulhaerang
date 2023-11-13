package com.finale.neulhaerang.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// 인증
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-001", "잘못된 형식의 토큰입니다."),
	AUTHENTICATION_ENTRY_POINT(HttpStatus.BAD_REQUEST, "A-002", "잘못된 접근입니다."),
	EXPIRED_AUTH(HttpStatus.UNAUTHORIZED, "A-003", "유효하지 않은 인증 정보입니다. 다시 로그인해주세요."), // 리프레쉬 토큰 만료
	ACCESS_FORBIDDEN(HttpStatus.FORBIDDEN, "A-004", "해당 요청에 대한 권한이 존재하지 않습니다."),

	// 멤버
	NOT_EXIST_MEMBER(HttpStatus.INTERNAL_SERVER_ERROR, "M-001", "해당 사용자가 존재하지 않습니다."),

	// 공통
	INVALID_PAGE_INDEX(HttpStatus.BAD_REQUEST, "C-001", "올바르지 않은 페이지 번호입니다."),

	// 캐릭터 정보
	NOT_EXIST_CHARACTERINFO(HttpStatus.INTERNAL_SERVER_ERROR, "C-001", "해당 사용자의 캐릭터 정보가 존재하지 않습니다."),

	// 디바이스
	NOT_EXIST_DEVICE(HttpStatus.INTERNAL_SERVER_ERROR, "D-001", "등록되지 않은 디바이스입니다."),

	// 스탯
	INVALID_STAT_KIND(HttpStatus.INTERNAL_SERVER_ERROR, "S-001", "존재하지 않는 스탯입니다."),
	ALREADY_EXIST_TIREDNESS(HttpStatus.INTERNAL_SERVER_ERROR, "S-002", "오늘의 피로도를 이미 입력하셨습니다."),

	// 투두리스트
	INVALID_TODO_DATE(HttpStatus.BAD_REQUEST, "T-001", "날짜가 유효하지 않습니다."),
	NOT_EXIST_TODO(HttpStatus.BAD_REQUEST, "T-002", "해당 체크리스트가 존재하지 않습니다."),

	// 루틴
	NOT_EXIST_ALARM_TIME(HttpStatus.BAD_REQUEST, "R-001", "알람을 받는 경우 알람 시간을 지정해야합니다."),
	INVALID_REPEATED_DATE(HttpStatus.BAD_REQUEST, "R-002", "반복 날짜를 담은 리스트는 길이가 7이어야 합니다."),
	ALREADY_REMOVE_ROUTINE(HttpStatus.BAD_REQUEST, "R-003", "해당 루틴은 이미 삭제되었습니다."),
	ALREADY_REMOVE_DAILY_ROUTINE(HttpStatus.BAD_REQUEST, "R-004", "해당 데일리 루틴은 이미 삭제되었습니다."),
	NOT_EXIST_ROUTINE(HttpStatus.BAD_REQUEST, "R-005", "해당 id를 가진 루틴은 존재하지 않습니다."),
	NOT_EXIST_DAILY_ROUTINE(HttpStatus.BAD_REQUEST, "R-006", "해당 id를 가진 데일리 루틴은 존재하지 않습니다."),
	NOT_EXIST_RELATION_WITH_ROUTINE(HttpStatus.BAD_REQUEST, "R-007", "해당 id를 가진 루틴과 관련이 없습니다."),
	CAN_NOT_REMOVE_DAILY_ROUTINE_BEFORE_TODAY(HttpStatus.BAD_REQUEST, "R-008", "오늘 날짜 이전의 데일리 루틴은 삭제할 수 없습니다."),
	NON_REPEATED_DATE(HttpStatus.BAD_REQUEST, "R-009", "반복 하지 않는 루틴은 생성할 수 없습니다."),

	// AR
	INVALID_TAG(HttpStatus.INTERNAL_SERVER_ERROR, "K-001", "자기 자신은 태그할 수 없습니다"),

	// 칭호
	NOT_EXIST_TITLE(HttpStatus.BAD_REQUEST, "E-001", "해당 칭호는 존재하지 않습니다."),
	NOT_EARNED_TITLE(HttpStatus.BAD_REQUEST, "E-002", "해당 칭호는 아직 얻지 못했습니다."),
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