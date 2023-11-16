package com.finale.neulhaerang.domain.member.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterModifyReqDto {
	@Max(8)
	@Min(0)
	private int backpack;

	@Max(4)
	@Min(0)
	private int glasses;

	@Max(4)
	@Min(0)
	private int hat;

	@Max(3)
	@Min(0)
	private int scarf;

	@Max(8)
	@Min(0)
	private int hand;

	@Max(15)
	@Min(0)
	private int skin;

	@Max(31)
	@Min(0)
	private long title;
}
