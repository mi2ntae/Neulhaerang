package com.finale.neulhaerang.domain.member.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CharacterInfoTest {
	@DisplayName("CharacterInfo Entity Build Test")
	@Test
	void When_EntityBuilder_Expect_isNotNull() {
		// given
		Member member = Member.builder()
			.kakaoId(1L)
			.build();
		// when
		CharacterInfo characterInfo = createCharacterInfo(member);

		// then
		assertThat(characterInfo).isNotNull();
	}

	@DisplayName("CharacterInfo Entity Bean 주입 Test")
	@Test
	void When_CharacterInfoDI_Expect_isEqualTo() {
		// given
		Member member = Member.builder()
			.kakaoId(1L)
			.build();

		// when
		CharacterInfo characterInfo = createCharacterInfo(member);

		// then
		assertThat(characterInfo.getMember().getKakaoId()).isEqualTo(member.getKakaoId());
	}

	@DisplayName("CharacterInfo를 생성합니다.")
	@Test
	void When_CreateCharacterInfo_Expect_isEqualTo() {
		// given
		Member member = Member.builder()
			.kakaoId(1L)
			.build();

		// when
		CharacterInfo characterInfo = CharacterInfo.create(member);

		// then
		assertThat(characterInfo.getMember().getKakaoId()).isEqualTo(member.getKakaoId());
		assertThat(characterInfo.getHat()).isZero();
		assertThat(characterInfo.getTitle()).isZero();
		assertThat(characterInfo.getScarf()).isZero();
		assertThat(characterInfo.getBackpack()).isZero();
		assertThat(characterInfo.getGlasses()).isZero();
	}

	CharacterInfo createCharacterInfo(Member member) {
		return CharacterInfo.builder()
			.member(member)
			.build();
	}
}