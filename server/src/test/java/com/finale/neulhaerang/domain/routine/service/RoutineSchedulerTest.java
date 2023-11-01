package com.finale.neulhaerang.domain.routine.service;

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
import com.finale.neulhaerang.domain.routine.repository.DailyRoutineRepository;
import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class RoutineSchedulerTest {

	@Autowired
	private RoutineScheduler routineScheduler;

	@Autowired
	private RoutineRepository routineRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private DailyRoutineRepository dailyRoutineRepository;

	@DisplayName("스케줄러가 실행되면 해당 날짜의 daily-routine이 추가됩니다.")
	@Test
	void When_RoutineScheduler_Expect_AddDailyRoutine() throws InterruptedException {
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
		LocalDate date = LocalDate.of(2023, 11, 1);

		// when
		routineScheduler.createDailyRoutine(date);

		// then
		List<DailyRoutine> dailyRoutines = dailyRoutineRepository.findDailyRoutinesByRoutineDateAndRoutineIn(
			LocalDate.now(), routines);
		assertThat(dailyRoutines).hasSize(2)
			.extracting("routine", "check", "routineDate")
			.containsExactlyInAnyOrder(
				tuple(routine1, false, date),
				tuple(routine2, false, date)
			);

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