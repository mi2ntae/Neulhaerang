package com.finale.neulhaerang.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberStatusResDto {
	private int indolence;
	private int tiredness;

	public static MemberStatusResDto create(int indolence, int tiredness) {
		return MemberStatusResDto.builder()
			.indolence(indolence)
			.tiredness(tiredness)
			.build();
	}
}