package com.finale.neulhaerang.domain.member.dto.response;

import com.finale.neulhaerang.domain.member.entity.CharacterInfo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberCharacterResDto {
	private int backpack;
	private int glasses;
	private int hat;
	private int scarf;
	private int skin;
	private int hand;
	private long title;

	public static MemberCharacterResDto from(CharacterInfo character) {
		return MemberCharacterResDto.builder()
			.backpack(character.getBackpack())
			.glasses(character.getGlasses())
			.hat(character.getHat())
			.skin(character.getSkin())
			.hand(character.getHand())
			.scarf(character.getScarf())
			.title(character.getTitle())
			.build();
	}
}