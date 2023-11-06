package com.finale.neulhaerang.domain.letter.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.finale.neulhaerang.domain.letter.entity.Letter;
import com.finale.neulhaerang.global.util.BaseTest;

class LetterRepositoryTest extends BaseTest {

	@Autowired
	private LetterRepository letterRepository;

	@Test
	@DisplayName("날짜가 주어졌을 때 해당 날짜의 편지가 있으면 편지를 찾아옵니다.")
	public void When_FindLetterByDate_Expect_Letter() {
		// given
		LocalDate date = LocalDate.of(2023, 11, 5);
		Letter saveLetter = Letter.builder()
			.member(member)
			.letterDate(date)
			.content("오늘의 편지입니다.")
			.build();
		letterRepository.save(saveLetter);

		// when
		Optional<Letter> letter = letterRepository.findLetterByMemberAndLetterDate(member, date);

		// then
		assertThat(letter).isPresent();
		assertThat(letter.get().getContent()).isEqualTo("오늘의 편지입니다.");
		assertThat(letter.get().getLetterDate()).isEqualTo(date);
	}
}