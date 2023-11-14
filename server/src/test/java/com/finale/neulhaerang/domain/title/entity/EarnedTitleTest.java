package com.finale.neulhaerang.domain.title.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EarnedTitleTest {

	@DisplayName("Earned Title Entity Build Test")
	@Test
	void When_EntityBuilder_Expect_isNotNull() {
		// given
		Title title = Title.builder()
			.content("슬늘생")
			.build();
		// when
		EarnedTitle earnedTitle = createEarnedTitle(title);

		// then
		assertThat(earnedTitle).isNotNull();
	}

	@DisplayName("Earned Title Entity Bean 주입 Test")
	@Test
	void When_EarnedTitleDI_Expect_isEqualTo() {
		// given
		Title title = Title.builder()
			.content("슬늘생")
			.build();

		// when
		EarnedTitle earnedTitle = createEarnedTitle(title);

		// then
		assertThat(earnedTitle.getTitle().getContent()).isEqualTo("슬늘생");
	}

	EarnedTitle createEarnedTitle(Title title) {
		return EarnedTitle.builder()
			.title(title)
			.build();
	}

}