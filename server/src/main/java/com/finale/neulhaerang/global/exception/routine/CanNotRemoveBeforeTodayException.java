package com.finale.neulhaerang.global.exception.routine;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CanNotRemoveBeforeTodayException extends RuntimeException {
	public CanNotRemoveBeforeTodayException() {
		super();
	}

	public CanNotRemoveBeforeTodayException(Member member, DailyRoutine dailyRoutine) {
		super();
		log.error(
			member.getNickname() + "(member_id=" + member.getId()
				+ ")님이 dailyRoutine(daily_routine_id=" + dailyRoutine.getId() + ", content=" + dailyRoutine.getRoutine()
				.getContent() + ")를 삭제하려 하셨는데, 오늘 이전 데일리 루틴은 삭제할 수 없습니다.");
	}

	public CanNotRemoveBeforeTodayException(String message) {
		super(message);
	}

	public CanNotRemoveBeforeTodayException(String message, Throwable th) {
		super(message, th);
	}
}