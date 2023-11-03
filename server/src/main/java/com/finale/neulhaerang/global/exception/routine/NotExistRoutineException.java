package com.finale.neulhaerang.global.exception.routine;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotExistRoutineException extends RuntimeException {
	public NotExistRoutineException() {
		super();
	}

	public NotExistRoutineException(Member member, Long id) {
		super();
		log.error(
			member.getNickname() + "(member_id=" + member.getId()
				+ ")님이 daily routine의 check를 변경하려고 하셨는데, 해당 id(daily_routine_id=" + id
				+ ")를 가진 daily routine은 존재하지 않습니다.");
	}

	public NotExistRoutineException(String message) {
		super(message);
	}

	public NotExistRoutineException(String message, Throwable th) {
		super(message, th);
	}
}