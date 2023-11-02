package com.finale.neulhaerang.global.exception.routine;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.routine.entity.Routine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlreadyRemoveRoutineException extends RuntimeException {
	public AlreadyRemoveRoutineException() {
		super();
	}

	public AlreadyRemoveRoutineException(Member member, Routine routine) {
		super();
		log.error(
			member.getNickname() + "(member_id=" + member.getId()
				+ ")님이 dailyRoutine(daily_routine_id=" + routine.getId() + ", content=" + routine.getContent()
				+ ")의 check 값을 변경하려 하셨는데 해당 daily routine은 이미 삭제되었습니다.");
	}

	public AlreadyRemoveRoutineException(String message) {
		super(message);
	}

	public AlreadyRemoveRoutineException(String message, Throwable th) {
		super(message, th);
	}
}