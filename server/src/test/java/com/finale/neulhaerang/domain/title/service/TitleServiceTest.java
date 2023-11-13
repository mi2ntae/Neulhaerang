package com.finale.neulhaerang.domain.title.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.finale.neulhaerang.domain.title.dto.response.EarnedTitleResDto;
import com.finale.neulhaerang.domain.title.entity.EarnedTitle;
import com.finale.neulhaerang.domain.title.entity.Title;
import com.finale.neulhaerang.domain.title.repository.EarnedTitleRepository;
import com.finale.neulhaerang.domain.title.repository.TitleRepository;
import com.finale.neulhaerang.global.util.BaseTest;

class TitleServiceTest extends BaseTest {
	@Autowired
	private TitleService titleService;
	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	private EarnedTitleRepository earnedTitleRepository;

	@DisplayName("얻은 칭호를 조회합니다.")
	@Test
	void When_FindEarnedTitle_Expect_ListOfEarnedTitle() {
		// given
		Title title1 = createTitle(19L, "슬늘생1", "슬늘1");
		Title title2 = createTitle(20L, "슬늘생2", "슬늘2");
		Title title3 = createTitle(21L, "슬늘생3", "슬늘3");
		List<Title> titles = titleRepository.saveAll(List.of(title1, title2, title3));
		EarnedTitle earnedTitle1 = createEarnedTitle(titles.get(0));
		EarnedTitle earnedTitle2 = createEarnedTitle(titles.get(2));
		earnedTitleRepository.saveAll(List.of(earnedTitle1, earnedTitle2));

		// when
		List<EarnedTitleResDto> earnedTitles = titleService.findEarnedTitleByMember();

		// then
		assertThat(earnedTitles).hasSize(2)
			.extracting("titleId", "name", "content")
			.containsExactlyInAnyOrder(
				tuple(19L, "슬늘생1", "슬늘1"),
				tuple(21L, "슬늘생3", "슬늘3")
			);
	}

	private EarnedTitle createEarnedTitle(Title title) {
		return EarnedTitle.builder()
			.member(member)
			.title(title)
			.build();
	}

	private Title createTitle(Long id, String name, String content) {
		return Title.builder()
			.id(id)
			.name(name)
			.content(content)
			.build();
	}
}