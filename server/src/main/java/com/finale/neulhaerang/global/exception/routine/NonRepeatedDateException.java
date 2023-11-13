package com.finale.neulhaerang.global.exception.routine;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NonRepeatedDateException extends RuntimeException {
	public NonRepeatedDateException() {
		super();
	}

	public NonRepeatedDateException(Member member) {
		super();
		log.error(
			member.getNickname() + "(member_id=" + member.getId()
				+ ")님이 루틴 생성 시, 반복 날짜 정보를 잘못 보내셨습니다. 현재 모든 요일에 대해 반복 여부가 없습니다.");
	}

	public NonRepeatedDateException(String message) {
		super(message);
	}

	public NonRepeatedDateException(String message, Throwable th) {
		super(message, th);
	}
}