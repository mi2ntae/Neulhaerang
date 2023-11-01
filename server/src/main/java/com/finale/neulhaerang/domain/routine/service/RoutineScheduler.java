package com.finale.neulhaerang.domain.routine.service;

import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoutineScheduler {

	private final RoutineRepository routineRepository;

	@Scheduled(cron = "${schedules.cron.daily-routine}", zone = "Asia/Seoul")
	public void createDailyRoutineTrigger() {
		createDailyRoutine(LocalDate.now());
	}

	void createDailyRoutine(LocalDate date) {
		// 데일리 루틴 생성
	}

}
