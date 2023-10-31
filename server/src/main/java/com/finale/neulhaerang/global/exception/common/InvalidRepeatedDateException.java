package com.finale.neulhaerang.global.exception.common;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidRepeatedDateException extends RuntimeException {
	public InvalidRepeatedDateException() {
		super();
	}

	public InvalidRepeatedDateException(Member member) {
		super();
		log.error(
			member.getNickname() + "(member_id=" + member.getId()
				+ ")님이 루틴 생성 시, 반복 날짜 정보를 잘못 보내셨습니다. 반복 날짜 정보는 모든 요일에 대해 반복 여부를 작성해야합니다.");
	}

	public InvalidRepeatedDateException(String message) {
		super(message);
	}

	public InvalidRepeatedDateException(String message, Throwable th) {
		super(message, th);
	}
}