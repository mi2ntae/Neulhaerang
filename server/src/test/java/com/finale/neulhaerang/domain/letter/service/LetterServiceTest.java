package com.finale.neulhaerang.domain.letter.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.finale.neulhaerang.domain.letter.entity.Letter;
import com.finale.neulhaerang.domain.letter.repository.LetterRepository;
import com.finale.neulhaerang.global.util.BaseTest;

class LetterServiceTest extends BaseTest {

	@Autowired
	private LetterService letterService;

	@Autowired
	private LetterRepository letterRepository;

	@Test
	@DisplayName("해당 날짜에 편지가 있는 경우 불러오는 테스트")
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
		String letter = letterService.findDailyLetter(date);

		// then
		assertThat(letter).isEqualTo("오늘의 편지입니다.");

		Optional<Letter> readLetter = letterRepository.findById(1L);
		assertThat(readLetter.get().isRead()).isEqualTo(true);
	}
}