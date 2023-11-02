package com.finale.neulhaerang.domain.routine.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.entity.StatType;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class RoutineRepositoryTest {
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private RoutineRepository routineRepository;

	@DisplayName("해당 날짜 요일에 실행되는 루틴들을 가져옵니다. 이때 삭제 예정 날짜가 해당 날짜 이후여야 합니다.")
	@Test
	void When_GetDateAndDayOfValue_Expect_GetListOfRoutine() {
		// given
		Member member = Member.builder()
			.nickname("박정은")
			.kakaoId(12345678L).build();
		Member save = memberRepository.save(member);

		Routine routine1 = createRoutine(save, "양치하기1", "0010000", false, StatType.생존력, null);
		Routine routine2 = createRoutine(save, "양치하기2", "0110000", false, StatType.갓생력, LocalDate.of(2023, 8, 20));
		Routine routine3 = createRoutine(save, "양치하기3", "0111000", false, StatType.최애력, LocalDate.of(2023, 8, 19));
		Routine routine4 = createRoutine(save, "양치하기4", "0101000", false, StatType.인싸력, null);

		routineRepository.saveAll(List.of(routine1, routine2, routine3, routine4));

		// when
		String dayOfValue = "__1____";
		LocalDate date = LocalDate.of(2023, 8, 19);
		List<Routine> routines = routineRepository.findRoutinesByDayOfValue(dayOfValue, date);

		// then
		Assertions.assertThat(routines).hasSize(2)
			.extracting("content", "repeated", "statType")
			.containsExactlyInAnyOrder(
				tuple("양치하기1", "0010000", StatType.생존력),
				tuple("양치하기2", "0110000", StatType.갓생력)
			);
	}

	private static Routine createRoutine(Member save, String content, String repeated, boolean alarm,
		StatType statType, LocalDate deleteDate) {
		return Routine.builder()
			.member(save)
			.content(content)
			.repeated(repeated)
			.alarm(alarm)
			.statType(statType)
			.deleteDate(deleteDate)
			.build();
	}
}