package com.finale.neulhaerang.domain.routine.entity;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.global.exception.routine.CanNotCreateDailyRoutineBeforeToday;

class DailyRoutineTest {
	@DisplayName("DailyRoutine Entity Build Test")
	@Test
	void When_DailyEntityBuilder_Expect_isNotNull() {
		// given // when
		DailyRoutine dailyRoutine = createDailyRoutine(true);

		// then
		assertThat(dailyRoutine).isNotNull();
	}

	@DisplayName("DailyRoutine Entity Bean 주입 Test")
	@Test
	void When_DailyRoutineDI_Expect_isEqualTo() {
		// given // when
		DailyRoutine dailyRoutine = createDailyRoutine(true);

		// then
		assertThat(dailyRoutine.isCheck()).isEqualTo(true);
	}

	@DisplayName("DailyRoutine이 생성됩니다.")
	@Test
	void When_CreateDailyRoutine_Expect_IsOk() {
		// given
		Routine routine = Routine.builder()
			.member(Member.builder().build())
			.content("test")
			.alarm(false)
			.build();

		LocalDate date = LocalDate.now();

		// when
		DailyRoutine dailyRoutine = DailyRoutine.create(routine, date);

		// then
		assertThat(dailyRoutine.isCheck()).isFalse();
		assertThat(dailyRoutine.getRoutineDate()).isEqualTo(date);
		assertThat(dailyRoutine.isStatus()).isFalse();
	}

	@DisplayName("DailyRoutine 생성 시, 오늘 이전 날짜로는 생성할 수 없습니다.")
	@Test
	void When_CreateDailyRoutine_Expect_CanNotCreateDailyRoutineBeforeToday() {
		// given
		Routine routine = Routine.builder()
			.member(Member.builder().build())
			.content("test")
			.alarm(false)
			.build();

		LocalDate date = LocalDate.now().minusDays(1);

		// when // then
		assertThatThrownBy(() -> DailyRoutine.create(routine, date))
			.isInstanceOf(CanNotCreateDailyRoutineBeforeToday.class);

	}

	@DisplayName("DailyRoutine의 check가 반대의 boolean 값으로 수정됩니다.")
	@Test
	void When_CheckDailyRoutine_Expect_OppositeStatus() {
		// given
		boolean original1 = true;
		DailyRoutine dailyRoutine1 = createDailyRoutine(original1);
		boolean original2 = false;
		DailyRoutine dailyRoutine2 = createDailyRoutine(original2);
		// when
		dailyRoutine1.updateCheck();
		dailyRoutine2.updateCheck();

		// then
		assertThat(dailyRoutine1.isCheck()).isEqualTo(!original1);
		assertThat(dailyRoutine2.isCheck()).isEqualTo(!original2);
	}

	@DisplayName("DailyRoutine의 status가 true로 수정됩니다.")
	@Test
	void When__Expect_() {
		// given
		DailyRoutine dailyRoutine = DailyRoutine.builder()
			.status(false)
			.build();

		// when
		dailyRoutine.updateStatus();
		// then
		assertThat(dailyRoutine.isStatus()).isEqualTo(true);
	}

	private DailyRoutine createDailyRoutine(boolean check) {
		return DailyRoutine.builder()
			.check(check)
			.build();
	}
}