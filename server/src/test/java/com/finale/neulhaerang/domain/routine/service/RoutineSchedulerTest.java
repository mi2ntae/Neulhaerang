package com.finale.neulhaerang.domain.routine.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.routine.repository.DailyRoutineRepository;
import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;
import com.finale.neulhaerang.global.util.BaseTest;

class RoutineSchedulerTest extends BaseTest {

	@Autowired
	private RoutineScheduler routineScheduler;

	@Autowired
	private RoutineRepository routineRepository;

	@Autowired
	private DailyRoutineRepository dailyRoutineRepository;

	@DisplayName("스케줄러가 실행되면 해당 날짜의 daily-routine이 추가됩니다.")
	@Test
	void When_RoutineScheduler_Expect_AddDailyRoutine() throws InterruptedException {
		// given

		Routine routine1 = createRoutine("양치하기", "0010000", false, StatType.생존력);
		Routine routine2 = createRoutine("양치하기2", "0110000", false, StatType.생존력);
		Routine routine3 = createRoutine("양치하기3", "0101000", false, StatType.생존력);

		List<Routine> routines = List.of(routine1, routine2, routine3);
		routineRepository.saveAll(routines);
		LocalDate date = LocalDate.of(2023, 11, 1);

		// when
		routineScheduler.createDailyRoutine(date);

		// then
		List<DailyRoutine> dailyRoutines = dailyRoutineRepository.findDailyRoutinesByRoutineDateAndRoutineIn(
			date, routines);
		assertThat(dailyRoutines).hasSize(2)
			.extracting("routine", "check", "routineDate")
			.containsExactlyInAnyOrder(
				tuple(routine1, false, date),
				tuple(routine2, false, date)
			);

	}

	private Routine createRoutine(String content, String repeated, boolean alarm,
		StatType statType) {
		return Routine.builder()
			.member(member)
			.content(content)
			.repeated(repeated)
			.alarm(alarm)
			.statType(statType)
			.build();
	}

}