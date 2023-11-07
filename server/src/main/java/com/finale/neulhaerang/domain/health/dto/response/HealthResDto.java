package com.finale.neulhaerang.domain.health.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HealthResDto {
	private String health;
	private List<String> activeProfiles;

	public static HealthResDto from(List<String> activeProfiles) {
		return HealthResDto.builder()
			.health("connect")
			.activeProfiles(activeProfiles)
			.build();
	}
}
