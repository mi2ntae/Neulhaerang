package com.finale.neulhaerang.global.exception.common;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotExistAlarmTimeException extends RuntimeException {
	public NotExistAlarmTimeException() {
		super();
	}

	public NotExistAlarmTimeException(Member member) {
		super();
		log.error(
			member.getNickname() + "(member_id=" + member.getId() + ")님이 루틴 생성 시, 알람 요청을 하였는데 알람 시간을 설정하지 않았습니다.");
	}

	public NotExistAlarmTimeException(String message) {
		super(message);
	}

	public NotExistAlarmTimeException(String message, Throwable th) {
		super(message, th);
	}
}