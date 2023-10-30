package com.finale.neulhaerang.domain.routine.entity;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

	private Routine createRoutine(String content) {
		return Routine.builder()
			.content(content)
			.build();
	}
}