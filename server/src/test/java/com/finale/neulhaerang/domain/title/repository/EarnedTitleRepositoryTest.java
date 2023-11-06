package com.finale.neulhaerang.domain.title.repository;

import org.assertj.core.api.Assertions;
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
		Title title = Title.builder()
			.id(1L)
			.name("쫭")
			.content("짱")
			.build();
		titleRepository.save(title);
		EarnedTitle earnedTitle = EarnedTitle.create(member, title);
		earnedTitleRepository.save(earnedTitle);

		// when // then
		Assertions.assertThat(earnedTitleRepository.existsByTitle_IdAndMember(1L, member)).isTrue();
		Assertions.assertThat(earnedTitleRepository.existsByTitle_IdAndMember(2L, member)).isFalse();
	}

}