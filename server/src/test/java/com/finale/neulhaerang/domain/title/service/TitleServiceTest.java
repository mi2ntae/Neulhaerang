package com.finale.neulhaerang.domain.title.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.title.dto.response.EarnedTitleResDto;
import com.finale.neulhaerang.domain.title.entity.EarnedTitle;
import com.finale.neulhaerang.domain.title.entity.Title;
import com.finale.neulhaerang.domain.title.repository.EarnedTitleRepository;
import com.finale.neulhaerang.domain.title.repository.TitleRepository;
import com.finale.neulhaerang.global.exception.title.NotExistTitleException;
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

	@DisplayName("장착 칭호를 변경합니다.")
	@Test
	void When_ModifyEquipTitle_Expect_ChangeEquipTitle() {
		// given
		Title title1 = createTitle(1L, "슬늘생1", "슬늘1");
		Title title2 = createTitle(2L, "슬늘생2", "슬늘2");
		List<Title> titles = titleRepository.saveAll(List.of(title1, title2));
		EarnedTitle earnedTitle1 = createEarnedTitle(titles.get(0));
		EarnedTitle earnedTitle2 = createEarnedTitle(titles.get(1));
		earnedTitleRepository.saveAll(List.of(earnedTitle1, earnedTitle2));

		// when
		titleService.modifyEquipTitleByTitleId(titles.get(1).getId());

		// then
		Member member1 = memberRepository.findById(member.getId()).get();
		assertThat(member1.getTitleId()).isEqualTo(titles.get(1).getId());
	}

	@DisplayName("장착 칭호 변경 시, 해당 칭호가 없다면 에러가 납니다.")
	@Test
	void When_ModifyEquipTitleWithNotExistTitle_Expect_NotExistTitleException() {
		// given // when // then
		assertThatThrownBy(() -> titleService.modifyEquipTitleByTitleId(1L))
			.isInstanceOf(NotExistTitleException.class);
	}

	@DisplayName("장착 칭호 변경 시, 아직 얻지 못한 칭호라면 에러가 납니다.")
	@Test
	void When_ModifyEquipTitleWithNotEarnedTitle_Expect_ChangeEquipTitle() {
		// given
		Title title1 = createTitle(1L, "슬늘생1", "슬늘1");
		Title title2 = createTitle(2L, "슬늘생2", "슬늘2");
		List<Title> titles = titleRepository.saveAll(List.of(title1, title2));

		// when // then
		assertThatThrownBy(() -> titleService.modifyEquipTitleByTitleId(titles.get(1).getId()))
			.isInstanceOf(NotExistTitleException.class);
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