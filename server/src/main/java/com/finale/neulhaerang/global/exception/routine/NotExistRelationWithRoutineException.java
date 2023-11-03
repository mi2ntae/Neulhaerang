package com.finale.neulhaerang.global.exception.routine;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;
import com.finale.neulhaerang.domain.routine.entity.Routine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotExistRelationWithRoutineException extends RuntimeException {
	public NotExistRelationWithRoutineException() {
		super();
	}

	public NotExistRelationWithRoutineException(Member member, DailyRoutine dailyRoutine, Routine routine) {
		super();
		log.error(
			member.getNickname() + "(member_id=" + member.getId()
				+ ")님이 루틴 삭제 요청을 하셨는데, 해당 daily routine(daily_routine_id=" + dailyRoutine.getId()
				+ ")는 해당 routine(routine_id=" + routine.getId() + ")와 관계가 없습니다.");
	}

	public NotExistRelationWithRoutineException(String message) {
		super(message);
	}

	public NotExistRelationWithRoutineException(String message, Throwable th) {
		super(message, th);
	}
}