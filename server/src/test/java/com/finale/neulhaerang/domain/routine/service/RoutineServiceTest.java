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
import com.finale.neulhaerang.global.exception.common.InvalidRepeatedDateException;
import com.finale.neulhaerang.global.exception.common.NotExistAlarmTimeException;

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

		RoutineCreateReqDto routineCreateReqDto = createRoutine("아침밥 챙겨랏 S2", true, LocalTime.of(8, 30, 0),
			List.of(true, true, true, false, false, false, false), StatType.생존력);
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

		RoutineCreateReqDto routineCreateReqDto = createRoutine("아침밥 챙겨랏 S2", false, LocalTime.of(8, 30, 0),
			List.of(true, true, true, false, false, false, false), StatType.생존력);
		// when
		Member save = memberRepository.save(member);
		routineService.createRoutine(save, routineCreateReqDto);

		// then
		List<Routine> routines = routineRepository.findAll();
		assertThat(routines).hasSize(1)
			.extracting("content", "repeated", "alarm", "alarmTime", "deleteDate", "statType", "member")
			.containsExactlyInAnyOrder(
				tuple("아침밥 챙겨랏 S2", "1110000", false, null, null, StatType.생존력, save)
			);
	}

	@DisplayName("알람이 존재하는 루틴을 생성 시, 알람 시간이 없으면 에러가 납니다.")
	@Test
	void When_CreateRoutineWithoutAlarmTime_Expect_isBadRequest() {

		// given
		Member member = createMember();

		RoutineCreateReqDto routineCreateReqDto = createRoutine("아침밥 챙겨랏 S2", true, null,
			List.of(true, true, true, false, false, false, false), StatType.생존력);
		// when
		Member save = memberRepository.save(member);

		// then
		assertThatThrownBy(() -> routineService.createRoutine(save, routineCreateReqDto))
			.isInstanceOf(NotExistAlarmTimeException.class);
	}

	@DisplayName("루틴을 생성 시, 반복 날짜 정보를 담은 리스트의 크기가 7이 아니면 에러가 납니다.")
	@Test
	void When_CreateRoutineWithoutInvalidSizeRepeatedList_Expect_isBadRequest() {

		// given
		Member member = createMember();

		RoutineCreateReqDto routineCreateReqDto = createRoutine("아침밥 챙겨랏 S2", true, LocalTime.of(8, 30, 0),
			List.of(true, true, true, false, false, false), StatType.생존력);
		// when
		Member save = memberRepository.save(member);

		// then
		assertThatThrownBy(() -> routineService.createRoutine(save, routineCreateReqDto))
			.isInstanceOf(InvalidRepeatedDateException.class);
	}

	private static Member createMember() {
		return Member.builder()
			.kakaoId(12345678)
			.nickname("정은")
			.build();
	}

	private static RoutineCreateReqDto createRoutine(String content, boolean alarm, LocalTime alarmTime,
		List<Boolean> repeated, StatType statType) {
		return RoutineCreateReqDto.builder()
			.content(content)
			.alarm(alarm)
			.alarmTime(alarmTime)
			.repeated(repeated)
			.statType(statType)
			.build();
	}
}
