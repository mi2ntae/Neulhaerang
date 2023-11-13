package com.finale.neulhaerang.domain.ar.dto.response;

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

	public static AroundMemberCharacterListResDto from(CharacterInfo character) {
		return AroundMemberCharacterListResDto.builder()
			.memberId(character.getMember().getId())
			.backpack(character.getBackpack())
			.glasses(character.getGlasses())
			.hat(character.getHat())
			.scarf(character.getScarf()).build();
	}
}