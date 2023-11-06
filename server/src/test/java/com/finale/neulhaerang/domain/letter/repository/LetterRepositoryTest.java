package com.finale.neulhaerang.domain.letter.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
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
		Letter saveLetter = createLetter(date);
		letterRepository.save(saveLetter);

		// when
		Optional<Letter> letter = letterRepository.findLetterByMemberAndLetterDate(member, date);

		// then
		assertThat(letter).isPresent();
		assertThat(letter.get().getContent()).isEqualTo("오늘의 편지입니다.");
		assertThat(letter.get().getLetterDate()).isEqualTo(date);
	}

	@DisplayName("멤버를 받아 해당 멤버의 편지의 개수를 반환합니다.")
	@Test
	void When_GetMember_Expect_CountOfLetterByMember() {
		// given
		Letter letter1 = createLetter(LocalDate.of(2023, 11, 1));
		Letter letter2 = createLetter(LocalDate.of(2023, 11, 2));
		Letter letter3 = createLetter(LocalDate.of(2023, 11, 3));
		letterRepository.saveAll(List.of(letter1, letter2, letter3));

		// when
		int countOfLetter = letterRepository.countAllByMember(member);

		// then
		Assertions.assertThat(countOfLetter).isEqualTo(3);
	}

	private Letter createLetter(LocalDate date) {
		return Letter.builder()
			.member(member)
			.letterDate(date)
			.content("오늘의 편지입니다.")
			.build();
	}
}