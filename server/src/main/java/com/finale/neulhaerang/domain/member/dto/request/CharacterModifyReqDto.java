package com.finale.neulhaerang.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterModifyReqDto {
	private int backpack;
	private int glasses;
	private int hat;
	private int scarf;
}
