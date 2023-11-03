package com.finale.neulhaerang.domain.routine.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.global.util.BaseTest;

class DailyRoutineRepositoryTest extends BaseTest {

	@Autowired
	private RoutineRepository routineRepository;

	@Autowired
	private DailyRoutineRepository dailyRoutineRepository;

	@DisplayName("날짜와 루틴 묶음을 받아서 해당 날짜의 루틴에 대한 정보를 반환합니다.")
	@Test
	void When_GetDateAndRoutines_Expect_GetDailyRoutines() {
		// given

		Routine routine1 = createRoutine("양치하기", "0010000", false, StatType.생존력);
		Routine routine2 = createRoutine("양치하기2", "0110000", false, StatType.생존력);
		Routine routine3 = createRoutine("양치하기3", "0101000", false, StatType.생존력);

		List<Routine> routines = List.of(routine1, routine2, routine3);
		routineRepository.saveAll(routines);
		LocalDate date = LocalDate.of(2023, 8, 19);
		LocalDate otherDate = LocalDate.of(2023, 8, 8);

		DailyRoutine dailyRoutine1 = createDailyRoutine(routine1, true, date, false);
		DailyRoutine dailyRoutine2 = createDailyRoutine(routine2, false, date, false);
		DailyRoutine dailyRoutine3 = createDailyRoutine(routine2, true, otherDate, false);
		DailyRoutine dailyRoutine4 = createDailyRoutine(routine3, false, date, false);
		dailyRoutineRepository.saveAll(List.of(dailyRoutine1, dailyRoutine2, dailyRoutine3, dailyRoutine4));

		// when
		List<DailyRoutine> dailyRoutines = dailyRoutineRepository.findDailyRoutinesByRoutineDateAndRoutineIn(
			date, List.of(routine1, routine2));

		// then
		assertThat(dailyRoutines).hasSize(2)
			.extracting("routine", "check", "routineDate")
			.containsExactlyInAnyOrder(
				tuple(routine1, true, date),
				tuple(routine2, false, date)
			);
	}

	@DisplayName("로그인 한 사용자의 date 날짜에 해당하는 dailyRoutine 리스트를 반환합니다.")
	@Test
	void When_FindDailyRoutineByDate_Expect_FindDailyRoutineByDate() {
		// given

		Routine routine1 = createRoutine("양치하기", "0010000", false, StatType.생존력);
		Routine routine2 = createRoutine("양치하기2", "0110000", false, StatType.생존력);
		Routine routine3 = createRoutine("양치하기3", "0101000", false, StatType.생존력);

		List<Routine> routines = List.of(routine1, routine2, routine3);
		routineRepository.saveAll(routines);
		LocalDate date = LocalDate.of(2023, 8, 19);
		LocalDate otherDate = LocalDate.of(2023, 8, 8);

		DailyRoutine dailyRoutine1 = createDailyRoutine(routine1, true, date, false);
		DailyRoutine dailyRoutine2 = createDailyRoutine(routine2, false, date, false);
		DailyRoutine dailyRoutine3 = createDailyRoutine(routine2, true, otherDate, false);
		DailyRoutine dailyRoutine4 = createDailyRoutine(routine3, false, date, true);
		dailyRoutineRepository.saveAll(List.of(dailyRoutine1, dailyRoutine2, dailyRoutine3, dailyRoutine4));

		// when
		List<DailyRoutine> dailyRoutines = dailyRoutineRepository.findDailyRoutinesByRoutineDateAndRoutine_MemberAndStatusIsFalse(
			date, member);

		// then
		assertThat(dailyRoutines).hasSize(2)
			.extracting("routine", "check", "routineDate")
			.containsExactlyInAnyOrder(
				tuple(routine1, true, date),
				tuple(routine2, false, date)
			);
	}

	@DisplayName("로그인 한 사용자가 요청한 날짜에 완료한 dailyRoutine 리스트를 반환합니다.")
	@Test
	void When_FindDailyRoutineByDate_Expect_DoneDailyRoutineList() {
		// given
		Routine routine1 = createRoutine("양치하기", "0010000", false, StatType.생존력);
		Routine routine2 = createRoutine("양치하기2", "0110000", false, StatType.생존력);
		Routine routine3 = createRoutine("양치하기3", "0101000", false, StatType.생존력);
		routineRepository.saveAll(List.of(routine1, routine2, routine3));

		LocalDate date = LocalDate.of(2023, 8, 19);
		DailyRoutine dailyRoutine1 = createDailyRoutine(routine1, true, date, false);
		DailyRoutine dailyRoutine2 = createDailyRoutine(routine2, false, date, false); // 완료하지 않은 루틴
		DailyRoutine dailyRoutine3 = createDailyRoutine(routine2, true, LocalDate.now(), false);  // 주어진 날짜와 다른 날의 루틴
		DailyRoutine dailyRoutine4 = createDailyRoutine(routine3, false, date, true); // 삭제된 루틴
		DailyRoutine dailyRoutine5 = createDailyRoutine(routine3, true, date, false);
		dailyRoutineRepository.saveAll(List.of(dailyRoutine1, dailyRoutine2, dailyRoutine3, dailyRoutine4, dailyRoutine5));

		// when
		List<DailyRoutine> dailyRoutines = dailyRoutineRepository.findDailyRoutinesByRoutineDateAndRoutine_MemberAndStatusIsFalseAndCheckIsTrue(
			date, member);

		// then
		assertThat(dailyRoutines).hasSize(2)
			.extracting("routine", "check", "routineDate")
			.containsExactlyInAnyOrder(
				tuple(routine1, true, date),
				tuple(routine3, true, date)
			);
	}

	private DailyRoutine createDailyRoutine(Routine routine, boolean check, LocalDate date, boolean status) {
		return DailyRoutine.builder()
			.routine(routine)
			.check(check)
			.routineDate(date)
			.status(status)
			.build();
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