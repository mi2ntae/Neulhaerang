package com.finale.neulhaerang.domain.routine.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

	@DisplayName("DailyRoutine의 check가 반대의 boolean 값으로 수정됩니다.")
	@Test
	void When_CheckDailyRoutine_Expect_OppositeStatus() {
		// given
		boolean original = true;
		DailyRoutine dailyRoutine = createDailyRoutine(original);

		// when
		dailyRoutine.updateCheck();

		// then
		assertThat(dailyRoutine.isCheck()).isEqualTo(!original);
	}

	private DailyRoutine createDailyRoutine(boolean check) {
		return DailyRoutine.builder()
			.check(check)
			.build();
	}
}