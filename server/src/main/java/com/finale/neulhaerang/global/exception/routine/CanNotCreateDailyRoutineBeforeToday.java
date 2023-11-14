package com.finale.neulhaerang.global.exception.routine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CanNotCreateDailyRoutineBeforeToday extends RuntimeException {
	public CanNotCreateDailyRoutineBeforeToday() {
		super();
	}

	public CanNotCreateDailyRoutineBeforeToday(String message) {
		super(message);
	}

	public CanNotCreateDailyRoutineBeforeToday(String message, Throwable th) {
		super(message, th);
	}
}