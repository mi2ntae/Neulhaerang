package com.finale.neulhaerang.domain.member.dto.response;

import com.finale.neulhaerang.domain.member.entity.CharacterInfo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberCharacterResDto {
	private String type;
	private String face;
	private String backpack;
	private String skin;
	private String glasses;
	private String hat;
	private String hand;
	private String scarf;

	public static MemberCharacterResDto from(CharacterInfo character) {
		return MemberCharacterResDto.builder()
			.type(character.getSkin().substring(3))
			.face(character.getFace())
			.backpack(character.getBackpack())
			.skin(character.getSkin().substring(3, character.getSkin().length()))
			.glasses(character.getGlasses())
			.hat(character.getHat())
			.hand(character.getHand())
			.scarf(character.getScarf()).build();
	}
}