package com.finale.neulhaerang.domain.member.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResDto {
	private String access_token;
	private String refresh_token;
	private LocalDateTime expired_time;

	public static TokenResDto of(String access_token, String refresh_token, LocalDateTime expired_time) {
		return TokenResDto.builder()
			.access_token(access_token)
			.refresh_token(refresh_token)
			.expired_time(expired_time).build();
	}
}
