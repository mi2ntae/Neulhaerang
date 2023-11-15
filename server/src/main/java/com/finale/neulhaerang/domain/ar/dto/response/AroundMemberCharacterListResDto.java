package com.finale.neulhaerang.domain.ar.dto.response;

import java.util.Objects;

import com.finale.neulhaerang.domain.member.entity.CharacterInfo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AroundMemberCharacterListResDto {
	private long memberId;
	private int backpack;
	private int glasses;
	private int hat;
	private int scarf;
	private long title;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AroundMemberCharacterListResDto that = (AroundMemberCharacterListResDto)o;
		return memberId == that.memberId && backpack == that.backpack && glasses == that.glasses && hat == that.hat
			&& scarf == that.scarf && title == that.title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(memberId, backpack, glasses, hat, scarf, title);
	}

	public static AroundMemberCharacterListResDto from(CharacterInfo character) {
		return AroundMemberCharacterListResDto.builder()
			.memberId(character.getMember().getId())
			.backpack(character.getBackpack())
			.glasses(character.getGlasses())
			.hat(character.getHat())
			.scarf(character.getScarf())
			.title(character.getTitle()).build();
	}
}