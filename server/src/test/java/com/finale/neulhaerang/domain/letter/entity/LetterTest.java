package com.finale.neulhaerang.domain.letter.entity;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.finale.neulhaerang.domain.member.entity.Member;

class LetterTest {
	@DisplayName("Letter Entity Build Test")
	@Test
	void When_EntityBuilder_Expect_isNotNull() {
		// given
		String content = "슬늘생";

		// when
		Letter letter = createLetter(content);

		// then
		assertThat(letter).isNotNull();
	}

	@DisplayName("Letter Entity Bean 주입 Test")
	@Test
	void When_LetterDI_Expect_isEqualTo() {
		// given
		String content = "슬늘생";

		// when
		Letter letter = createLetter(content);

		// then
		assertThat(letter.getContent()).isEqualTo(content);
	}

	@DisplayName("Letter를 생성합니다.")
	@Test
	void When_CreateLetter_Expect_Equal() {
		// given
		Member member = Member.builder()
			.kakaoId(1L)
			.nickname("정은")
			.build();
		String content = "슬늘생";
		LocalDate date = LocalDate.now();
		// when
		Letter letter = Letter.create(member, content, date);
		// then
		assertThat(letter.getLetterDate()).isEqualTo(date);
		assertThat(letter.getContent()).isEqualTo(content);
		assertThat(letter.getMember().getNickname()).isEqualTo("정은");
	}

	Letter createLetter(String content) {
		return Letter.builder()
			.content(content)
			.build();
	}
}