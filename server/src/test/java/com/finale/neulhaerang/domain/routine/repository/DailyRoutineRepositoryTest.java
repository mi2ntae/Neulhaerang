package com.finale.neulhaerang.domain.routine.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.entity.StatType;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class DailyRoutineRepositoryTest {
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private RoutineRepository routineRepository;

	@Autowired
	private DailyRoutineRepository dailyRoutineRepository;

	@DisplayName("날짜와 루틴 묶음을 받아서 해당 날짜의 루틴에 대한 정보를 반환합니다.")
	@Test
	void When_GetDateAndRoutines_Expect_GetDailyRoutines() {
		// given
		Member member = Member.builder()
			.nickname("박정은")
			.kakaoId(12345678L).build();
		Member save = memberRepository.save(member);

		Routine routine1 = createRoutine(save, "양치하기", "0010000", false, StatType.생존력);
		Routine routine2 = createRoutine(save, "양치하기2", "0110000", false, StatType.생존력);
		Routine routine3 = createRoutine(save, "양치하기3", "0101000", false, StatType.생존력);

		List<Routine> routines = List.of(routine1, routine2, routine3);
		routineRepository.saveAll(routines);
		LocalDate date = LocalDate.of(2023, 8, 19);
		LocalDate otherDate = LocalDate.of(2023, 8, 8);

		DailyRoutine dailyRoutine1 = createDailyRoutine(routine1, true, date);
		DailyRoutine dailyRoutine2 = createDailyRoutine(routine2, false, date);
		DailyRoutine dailyRoutine3 = createDailyRoutine(routine2, true, otherDate);
		DailyRoutine dailyRoutine4 = createDailyRoutine(routine3, false, date);
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
		Member member = Member.builder()
			.nickname("박정은")
			.kakaoId(12345678L).build();
		Member save = memberRepository.save(member);

		Routine routine1 = createRoutine(save, "양치하기", "0010000", false, StatType.생존력);
		Routine routine2 = createRoutine(save, "양치하기2", "0110000", false, StatType.생존력);
		Routine routine3 = createRoutine(save, "양치하기3", "0101000", false, StatType.생존력);

		List<Routine> routines = List.of(routine1, routine2, routine3);
		routineRepository.saveAll(routines);
		LocalDate date = LocalDate.of(2023, 8, 19);
		LocalDate otherDate = LocalDate.of(2023, 8, 8);

		DailyRoutine dailyRoutine1 = createDailyRoutine(routine1, true, date);
		DailyRoutine dailyRoutine2 = createDailyRoutine(routine2, false, date);
		DailyRoutine dailyRoutine3 = createDailyRoutine(routine2, true, otherDate);
		DailyRoutine dailyRoutine4 = createDailyRoutine(routine3, false, date);
		dailyRoutineRepository.saveAll(List.of(dailyRoutine1, dailyRoutine2, dailyRoutine3, dailyRoutine4));

		// when
		List<DailyRoutine> dailyRoutines = dailyRoutineRepository.findDailyRoutinesByRoutineDateAndRoutine_Member(
			date, save);

		// then
		assertThat(dailyRoutines).hasSize(3)
			.extracting("routine", "check", "routineDate")
			.containsExactlyInAnyOrder(
				tuple(routine1, true, date),
				tuple(routine2, false, date),
				tuple(routine3, false, date)
			);
	}

	private static DailyRoutine createDailyRoutine(Routine routine, boolean check, LocalDate date) {
		return DailyRoutine.builder()
			.routine(routine)
			.check(check)
			.routineDate(date)
			.build();
	}

	private static Routine createRoutine(Member save, String content, String repeated, boolean alarm,
		StatType statType) {
		return Routine.builder()
			.member(save)
			.content(content)
			.repeated(repeated)
			.alarm(alarm)
			.statType(statType)
			.build();
	}
}