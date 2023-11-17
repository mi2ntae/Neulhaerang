package com.finale.neulhaerang.domain.member.entity;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;

import java.time.LocalDateTime;

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

	@DisplayName("멤버가 생성됩니다.")
	@Test
	void When_CreateMember_Expect_IsOk() {
		// given
		long kakaoId = 111111;
		String nickname = "김민태";

		// when
		Member member = Member.create(kakaoId, nickname);

		// then
		assertSoftly(s -> {
			s.assertThat(member.getKakaoId()).isEqualTo(kakaoId);
			s.assertThat(member.getNickname()).isEqualTo(nickname);
		});
	}

	@DisplayName("멤버의 탈퇴 날짜를 변경합니다.")
	@Test
	void When_ModifyWithdrawalDate_Expect_Update() {
		// given
		Member member = Member.create(1234565L, "늘해랑");
		LocalDateTime now = LocalDateTime.now();

		// when
		member.updateWithdrawalDate(now);

		// then
		assertThat(member.getWithdrawalDate()).isEqualTo(now);
	}

	@DisplayName("멤버의 생성 날짜를 변경합니다. For test")
	@Test
	void When_ModifyCreateDate_Expect_Update() {
		// given
		Member member = Member.create(1234565L, "늘해랑");
		LocalDateTime now = LocalDateTime.now();

		// when
		member.updateCreateDate(now);

		// then
		assertThat(member.getCreateDate()).isEqualTo(now);
	}

	private Member createMember(long kakaoId, String nickname) {
		return Member.builder()
			.kakaoId(kakaoId)
			.nickname(nickname).build();
	}
}