package com.finale.neulhaerang.domain.routine.entity;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineCreateReqDto;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineModifyReqDto;
import com.finale.neulhaerang.global.exception.routine.InvalidRepeatedDateException;
import com.finale.neulhaerang.global.exception.routine.NonRepeatedDateException;

class RoutineTest {
	@DisplayName("Routine Entity Build Test")
	@Test
	void When_EntityBuilder_Expect_isNotNull() {
		// given // when
		Routine routine = createRoutine("매일 아침밥을 먹어요.");

		// then
		assertThat(routine).isNotNull();
	}

	@DisplayName("Routine Entity Bean 주입 Test")
	@Test
	void When_RoutineDI_Expect_isEqualTo() {
		// given
		String content = "매일 아침밥을 먹어요.";

		// when
		Routine routine = createRoutine(content);

		// then
		assertThat(routine.getContent()).isEqualTo(content);
	}

	@DisplayName("루틴을 생성합니다.")
	@Test
	void When_CreateRoutine_Expect_Equal() {
		// given
		RoutineCreateReqDto routineCreateReqDto = RoutineCreateReqDto.builder()
			.content("test")
			.alarm(true)
			.alarmTime(LocalTime.MAX)
			.statType(StatType.갓생력)
			.build();
		Member member = Member.builder()
			.kakaoId(1L)
			.build();
		String repeated = "1111111";

		// when
		Routine routine = Routine.create(routineCreateReqDto, member, repeated);

		// then
		assertThat(routine.getContent()).isEqualTo("test");
		assertThat(routine.isAlarm()).isEqualTo(true);
		assertThat(routine.getAlarmTime()).isEqualTo(LocalTime.MAX);
		assertThat(routine.getStatType()).isEqualTo(StatType.갓생력);
		assertThat(routine.getRepeated()).isEqualTo("1111111");
		assertThat(routine.getMember()).isEqualTo(member);
	}

	@DisplayName("루틴 생성 시, 알람이 false인 경우 alarmTime은 null이 됩니다.")
	@Test
	void When_CreateRoutineWithAlarmIsFalse_Expect_AlarmTimeIsNull() {
		// given
		RoutineCreateReqDto routineCreateReqDto = RoutineCreateReqDto.builder()
			.content("test")
			.alarm(false)
			.alarmTime(LocalTime.MAX)
			.statType(StatType.갓생력)
			.build();
		Member member = Member.builder()
			.kakaoId(1L)
			.build();
		// when
		Routine routine = Routine.create(routineCreateReqDto, member, "0100000");

		// then
		assertThat(routine.getAlarmTime()).isNull();
	}

	@DisplayName("루틴 생성 시, repeated가 0000000인 경우 에러가 납니다.")
	@Test
	void When_CreateRoutineWithNonRepeat_Expect_NonRepeatedDateException() {
		// given
		RoutineCreateReqDto routineCreateReqDto = RoutineCreateReqDto.builder()
			.content("test")
			.alarm(false)
			.alarmTime(LocalTime.MAX)
			.statType(StatType.갓생력)
			.build();
		Member member = Member.builder()
			.kakaoId(1L)
			.build();

		// when // then
		assertThatThrownBy(() -> Routine.create(routineCreateReqDto, member, "0000000"))
			.isInstanceOf(NonRepeatedDateException.class);
	}

	@DisplayName("루틴 생성 시, repeated의 길이가 7이 아니면 에러가 납니다.")
	@Test
	void When_CreateRoutineWithNonRepeat_Expect_InvalidRepeatedDateException() {
		// given
		RoutineCreateReqDto routineCreateReqDto = RoutineCreateReqDto.builder()
			.content("test")
			.alarm(false)
			.alarmTime(LocalTime.MAX)
			.statType(StatType.갓생력)
			.build();
		Member member = Member.builder()
			.kakaoId(1L)
			.build();

		// when // then
		assertThatThrownBy(() -> Routine.create(routineCreateReqDto, member, "10000000"))
			.isInstanceOf(InvalidRepeatedDateException.class);
	}

	@DisplayName("탈퇴 날짜를 변경합니다.")
	@Test
	void When_ModifyDeleteDate_Expect_IsOk() {
		// given
		Routine routine = Routine.builder()
			.content("test")
			.alarm(false)
			.alarmTime(LocalTime.MAX)
			.statType(StatType.갓생력)
			.repeated("1111111")
			.deleteDate(null)
			.build();
		LocalDate now = LocalDate.now();
		// when

		routine.updateDeleteDate(now);
		// then
		assertThat(routine.getDeleteDate()).isEqualTo(now);
	}

	@DisplayName("루틴 정보를 변경합니다.")
	@Test
	void When_ModifyContentAndAlarmAndAlarmTimeAndRepeated_Expect_IsOk() {
		// given
		Routine routine = Routine.builder()
			.content("test")
			.alarm(false)
			.alarmTime(LocalTime.MAX)
			.statType(StatType.갓생력)
			.repeated("1111111")
			.deleteDate(null)
			.build();

		RoutineModifyReqDto routineModifyReqDto = RoutineModifyReqDto.builder()
			.content("change")
			.alarm(true)
			.alarmTime(LocalTime.MAX)
			.build();

		String repeated = "1100000";

		// when
		routine.updateContentAndAlarmAndAlarmTimeAndRepeated(routineModifyReqDto, repeated);

		// then
		assertThat(routine.getRepeated()).isEqualTo("1100000");
		assertThat(routine.getContent()).isEqualTo("change");
		assertThat(routine.isAlarm()).isTrue();
		assertThat(routine.getAlarmTime()).isEqualTo(LocalTime.MAX);
	}

	@DisplayName("루틴 수정 시, repeated가 0000000인 경우 에러가 납니다.")
	@Test
	void When_ModifyRoutineWithNonRepeateDay_Expect_NonRepeatedDateException() {
		// given
		Routine routine = Routine.builder()
			.content("test")
			.alarm(false)
			.alarmTime(LocalTime.MAX)
			.statType(StatType.갓생력)
			.repeated("1111111")
			.deleteDate(null)
			.build();

		RoutineModifyReqDto routineModifyReqDto = RoutineModifyReqDto.builder()
			.content("change")
			.alarm(true)
			.alarmTime(LocalTime.MAX)
			.build();

		String repeated = "0000000";

		// when // then
		assertThatThrownBy(() -> routine.updateContentAndAlarmAndAlarmTimeAndRepeated(routineModifyReqDto, repeated))
			.isInstanceOf(NonRepeatedDateException.class);
	}

	@DisplayName("루틴 수정 시, repeated의 길이가 7이 아니면 에러가 납니다.")
	@Test
	void When_ModifyRoutineWithInvalidRepeateDay_Expect_InvalidRepeatedDateException() {
		// given
		Routine routine = Routine.builder()
			.content("test")
			.alarm(false)
			.alarmTime(LocalTime.MAX)
			.statType(StatType.갓생력)
			.repeated("1111111")
			.deleteDate(null)
			.build();

		RoutineModifyReqDto routineModifyReqDto = RoutineModifyReqDto.builder()
			.content("change")
			.alarm(true)
			.alarmTime(LocalTime.MAX)
			.build();

		String repeated = "10000000";

		// when // then
		assertThatThrownBy(() -> routine.updateContentAndAlarmAndAlarmTimeAndRepeated(routineModifyReqDto, repeated))
			.isInstanceOf(InvalidRepeatedDateException.class);
	}

	private Routine createRoutine(String content) {
		return Routine.builder()
			.content(content)
			.build();
	}
}