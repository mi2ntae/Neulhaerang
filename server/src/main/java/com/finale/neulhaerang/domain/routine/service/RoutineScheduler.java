package com.finale.neulhaerang.domain.routine.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.repository.DailyRoutineRepository;
import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoutineScheduler {

	private final RoutineRepository routineRepository;
	private final DailyRoutineRepository dailyRoutineRepository;

	@Scheduled(cron = "${schedules.cron.daily-routine}", zone = "Asia/Seoul")
	public void createDailyRoutineTrigger() {
		createDailyRoutine(LocalDate.now());
	}

	void createDailyRoutine(LocalDate date) {
		// date에 해야하는 루틴들을 가져옵니다.
		StringBuilder dayOfVaule = new StringBuilder("_______");
		int dayOfWeekValue = date.getDayOfWeek().getValue() - 1;
		dayOfVaule.setCharAt(dayOfWeekValue, '1');
		List<Routine> routinesOfDay = routineRepository.findRoutinesByDayOfValue(dayOfVaule.toString(), date);

		// dailyRoutine에 저장합니다.
		routinesOfDay.forEach(r -> {
			dailyRoutineRepository.save(DailyRoutine.create(r, date));
		});
	}

}
