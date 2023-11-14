package com.finale.neulhaerang.domain.title.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TitleTest {

	@DisplayName("Title Entity Build Test")
	@Test
	void When_EntityBuilder_Expect_isNotNull() {
		// given // when
		Title title = createTitle();

		// then
		assertThat(title).isNotNull();
	}

	@DisplayName("Routine Entity Bean 주입 Test")
	@Test
	void When_RoutineDI_Expect_isEqualTo() {
		// given // when
		Title title = createTitle();

		// then
		assertThat(title.getContent()).isEqualTo("슬늘생");
	}

	Title createTitle() {
		return Title.builder()
			.content("슬늘생")
			.build();
	}

}