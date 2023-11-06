package com.finale.neulhaerang.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatListResDto {
	private int score;
	private String level;

	public static StatListResDto of(int score, String level) {
		return StatListResDto.builder()
			.score(score)
			.level(level).build();
	}
}
