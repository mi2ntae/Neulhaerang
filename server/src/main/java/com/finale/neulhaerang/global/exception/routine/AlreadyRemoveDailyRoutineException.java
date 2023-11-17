package com.finale.neulhaerang.global.exception.routine;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlreadyRemoveDailyRoutineException extends RuntimeException {
	public AlreadyRemoveDailyRoutineException() {
		super();
	}

	public AlreadyRemoveDailyRoutineException(Member member, DailyRoutine dailyRoutine) {
		super();
		log.error(
			member.getNickname() + "(member_id=" + member.getId()
				+ ")님이 dailyRoutine(daily_routine_id=" + dailyRoutine.getId() + ", content=" + dailyRoutine.getRoutine()
				.getContent() + ")의 check 값을 변경하려 하셨는데 해당 daily routine은 이미 삭제되었습니다.");
	}

	public AlreadyRemoveDailyRoutineException(String message) {
		super(message);
	}

	public AlreadyRemoveDailyRoutineException(String message, Throwable th) {
		super(message, th);
	}
}