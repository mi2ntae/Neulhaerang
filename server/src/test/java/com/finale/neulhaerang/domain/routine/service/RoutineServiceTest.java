package com.finale.neulhaerang.domain.routine.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineCreateReqDto;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RoutineServiceTest {

	@Autowired
	private RoutineService routineService;

	@Autowired
	private RoutineRepository routineRepository;

	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("알람이 존재하는 루틴을 생성합니다.")
	@Test
	void When_CreateRoutineWithAlarm_Expect_isCreated() {

		// given
		Member member = createMember();

		RoutineCreateReqDto routineCreateReqDto = RoutineCreateReqDto.builder()
			.content("아침밥 챙겨랏 S2")
			.alarm(true)
			.alarmTime(LocalTime.of(8, 30, 0))
			.repeated(List.of("월", "화", "수"))
			.statType(StatType.생존력)
			.build();
		// when
		Member save = memberRepository.save(member);
		routineService.createRoutine(save, routineCreateReqDto);

		// then
		List<Routine> routines = routineRepository.findAll();
		assertThat(routines).hasSize(1)
			.extracting("content", "repeated", "alarm", "alarmTime", "deleteDate", "statType", "member")
			.containsExactlyInAnyOrder(
				tuple("아침밥 챙겨랏 S2", "1110000", true, LocalTime.of(8, 30, 0), null, StatType.생존력, save)
			);
	}

	@DisplayName("알람이 없는 루틴을 생성합니다.")
	@Test
	void When_CreateRoutineWithoutAlarm_Expect_isCreated() {

		// given
		Member member = createMember();

		RoutineCreateReqDto routineCreateReqDto = RoutineCreateReqDto.builder()
			.content("아침밥 챙겨랏 S2")
			.alarm(false)
			.alarmTime(LocalTime.of(8, 30, 0))
			.repeated(List.of("월", "화", "수"))
			.statType(StatType.생존력)
			.build();
		// when
		memberRepository.save(member);
		routineService.createRoutine(member, routineCreateReqDto);

		// then
		List<Routine> routines = routineRepository.findAll();
		assertThat(routines).hasSize(1)
			.extracting("content", "repeated", "alarm", "alarmTime", "deleteDate", "statType", "member")
			.containsExactlyInAnyOrder(
				tuple("아침밥 챙겨랏 S2", "1110000", false, null, null, StatType.생존력, member)
			);
	}

	@DisplayName("알람이 존재하는 루틴을 생성 시, 알람 시간이 없으면 에러가 납니다.")
	@Test
	void When_CreateRoutineWithoutAlarmTime_Expect_isBadRequest() {

		// given
		Member member = createMember();

		RoutineCreateReqDto routineCreateReqDto = RoutineCreateReqDto.builder()
			.content("아침밥 챙겨랏 S2")
			.alarm(true)
			.repeated(List.of("월", "화", "수"))
			.statType(StatType.생존력)
			.build();
		// when
		Member save = memberRepository.save(member);
		routineService.createRoutine(save, routineCreateReqDto);

		// then
		assertThatThrownBy(() -> {
		});
	}

	private static Member createMember() {
		return Member.builder()
			.id(1L)
			.kakaoId(12345678)
			.nickname("정은")
			.build();
	}

}
