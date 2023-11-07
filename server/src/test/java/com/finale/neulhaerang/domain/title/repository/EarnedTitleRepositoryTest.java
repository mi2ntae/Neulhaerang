package com.finale.neulhaerang.domain.title.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.finale.neulhaerang.domain.title.entity.EarnedTitle;
import com.finale.neulhaerang.domain.title.entity.Title;
import com.finale.neulhaerang.global.util.BaseTest;

class EarnedTitleRepositoryTest extends BaseTest {
	@Autowired
	private TitleRepository titleRepository;

	@Autowired
	private EarnedTitleRepository earnedTitleRepository;

	@DisplayName("멤버와 title id를 받아 존재한다면 true를 반환합니다.")
	@Test
	void When_GetTitleIdAndMember_Expect_ReturnTrue() {
		// given
		Title title = createTitle(1L, "좡", "좡");
		titleRepository.save(title);
		EarnedTitle earnedTitle = EarnedTitle.create(member, title);
		earnedTitleRepository.save(earnedTitle);

		// when // then
		assertThat(earnedTitleRepository.existsByTitle_IdAndMember(1L, member)).isTrue();
		assertThat(earnedTitleRepository.existsByTitle_IdAndMember(2L, member)).isFalse();
	}

	@DisplayName("멤버 정보를 받아 해당 멤버가 획득한 칭호 리스트를 전달합니다.")
	@Test
	void When_GetMemberInfo_Expect_ListOfTitleMemberHad() {
		// given
		Title title1 = createTitle(1L, "좡", "좡");
		Title title2 = createTitle(2L, "좡", "좡");
		Title title3 = createTitle(3L, "좡", "좡");
		List<Title> titles = titleRepository.saveAll(List.of(title1, title2, title3));
		EarnedTitle earnedTitle1 = EarnedTitle.create(member, titles.get(0));
		EarnedTitle earnedTitle2 = EarnedTitle.create(member, titles.get(1));
		EarnedTitle earnedTitle3 = EarnedTitle.create(member, titles.get(2));
		earnedTitleRepository.saveAll(List.of(earnedTitle1, earnedTitle2, earnedTitle3));

		// when
		List<EarnedTitle> earnTitles = earnedTitleRepository.findEarnedTitlesByMember_Id(member.getId());
		// then
		assertThat(earnTitles).hasSize(3).extracting("title")
			.containsExactlyInAnyOrder(
				titles.get(0),
				titles.get(1),
				titles.get(2)
			);
	}

	private static Title createTitle(Long id, String name, String content) {
		return Title.builder()
			.id(id)
			.name(name)
			.content(content)
			.build();
	}

}