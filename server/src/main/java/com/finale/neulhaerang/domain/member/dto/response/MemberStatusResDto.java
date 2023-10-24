package com.finale.neulhaerang.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberStatusResDto {
	private int indolence;
	private int tiredness;
}