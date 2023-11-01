package com.finale.neulhaerang.domain.member.entity;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {
	@DisplayName("Member Entity Build Test")
	@Test
	void When_EntityBuilder_Expect_isNotNull() {
		// given // when
		Member member = createMember(111111, "김민태");

		// then
		assertThat(member).isNotNull();
	}

	@DisplayName("Member Entity Bean 주입 Test")
	@Test
	void When_MemberDI_Expect_isEqualTo() {
		// given
		long kakaoId = 111111;
		String nickname = "김민태";

		// when
		Member member = createMember(kakaoId, nickname);

		// then
		assertSoftly(s -> {
			s.assertThat(member.getKakaoId()).isEqualTo(kakaoId);
			s.assertThat(member.getNickname()).isEqualTo(nickname);
		});
	}

	private Member createMember(long kakaoId, String nickname) {
		return Member.builder()
			.kakaoId(kakaoId)
			.nickname(nickname).build();
	}
}