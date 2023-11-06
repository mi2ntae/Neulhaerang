package com.finale.neulhaerang.global.event.handler;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.finale.neulhaerang.domain.letter.entity.Letter;
import com.finale.neulhaerang.domain.letter.repository.LetterRepository;
import com.finale.neulhaerang.domain.title.entity.EarnedTitle;
import com.finale.neulhaerang.domain.title.entity.Title;
import com.finale.neulhaerang.domain.title.repository.EarnedTitleRepository;
import com.finale.neulhaerang.domain.title.repository.TitleRepository;
import com.finale.neulhaerang.global.exception.title.NotExistTitleException;
import com.finale.neulhaerang.global.util.BaseTest;

class LetterTitleHandlerTest extends BaseTest {
	@Autowired
	private LetterRepository letterRepository;
	@Autowired
	private LetterTitleHandler letterTitleHandler;
	@Autowired
	private EarnedTitleRepository earnedTitleRepository;
	@Autowired
	private TitleRepository titleRepository;

	@DisplayName("편지의 개수가 9개이면 칭호가 발급되지 않습니다.")
	@Test
	void When_Get9Letter_Expect_NothingTitle() {
		// given
		Title title1 = createTitle(22L, "편십", "편십+");
		Title title2 = createTitle(23L, "편오", "편오+");
		Title title3 = createTitle(24L, "편백", "편백+");
		titleRepository.saveAll(List.of(title1, title2, title3));

		for (int i = 0; i < 9; i++) {
			createLetter(LocalDate.now().minusDays(i), "하이" + i);
		}

		// when
		letterTitleHandler.checkNumOfLetter(member);
		// then
		List<EarnedTitle> titles = earnedTitleRepository.findAll();
		assertThat(titles).hasSize(0);
	}

	@DisplayName("편지의 개수가 49개이면 칭호가 1개 발급됩니다.")
	@Test
	void When_Get49Letter_Expect_GetOneTitle() {
		// given
		Title title1 = createTitle(22L, "편십", "편십+");
		Title title2 = createTitle(23L, "편오", "편오+");
		Title title3 = createTitle(24L, "편백", "편백+");
		titleRepository.saveAll(List.of(title1, title2, title3));

		for (int i = 0; i < 49; i++) {
			createLetter(LocalDate.now().minusDays(i), "하이" + i);
		}

		// when
		letterTitleHandler.checkNumOfLetter(member);
		// then
		List<EarnedTitle> titles = earnedTitleRepository.findAll();
		assertThat(titles).hasSize(1);
	}

	@DisplayName("편지의 개수가 99개이면 칭호가 2개 발급됩니다.")
	@Test
	void When_Get99Letter_Expect_GetTwoTitle() {
		// given
		Title title1 = createTitle(22L, "편십", "편십+");
		Title title2 = createTitle(23L, "편오", "편오+");
		Title title3 = createTitle(24L, "편백", "편백+");
		titleRepository.saveAll(List.of(title1, title2, title3));

		for (int i = 0; i < 99; i++) {
			createLetter(LocalDate.now().minusDays(i), "하이" + i);
		}

		// when
		letterTitleHandler.checkNumOfLetter(member);
		// then
		List<EarnedTitle> titles = earnedTitleRepository.findAll();
		assertThat(titles).hasSize(2);
	}

	@DisplayName("편지의 개수가 100개이면 칭호가 3개 발급됩니다.")
	@Test
	void When_Get100Letter_Expect_GetThreeTitle() {
		// given
		Title title1 = createTitle(22L, "편십", "편십+");
		Title title2 = createTitle(23L, "편오", "편오+");
		Title title3 = createTitle(24L, "편백", "편백+");
		titleRepository.saveAll(List.of(title1, title2, title3));

		for (int i = 0; i < 100; i++) {
			createLetter(LocalDate.now().minusDays(i), "하이" + i);
		}

		// when
		letterTitleHandler.checkNumOfLetter(member);
		// then
		List<EarnedTitle> titles = earnedTitleRepository.findAll();
		assertThat(titles).hasSize(3);
	}

	@DisplayName("칭호 발급 시,칭호가 존재하지 않으면 에러가 납니다.")
	@Test
	void When_GetTitleWithoutTitle_Expect_NotExistTitleException() {
		// given

		for (int i = 0; i < 100; i++) {
			createLetter(LocalDate.now().minusDays(i), "하이" + i);
		}

		// when // then
		assertThatThrownBy(() -> letterTitleHandler.checkNumOfLetter(member))
			.isInstanceOf(NotExistTitleException.class);
	}

	private static Title createTitle(Long id, String name, String content) {
		return Title.builder()
			.id(id)
			.name(name)
			.content(content)
			.build();
	}

	private void createLetter(LocalDate date, String content) {
		Letter letter = Letter.builder()
			.letterDate(date)
			.content(content)
			.member(member)
			.read(false)
			.build();
		letterRepository.save(letter);
	}

}