package com.finale.neulhaerang.domain.member.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResDto {
	private String accessToken;
	private String refreshToken;
	private LocalDateTime expiredTime;

	public static TokenResDto of(String access_token, String refreshToken, LocalDateTime expired_time) {
		return TokenResDto.builder()
			.accessToken(access_token)
			.refreshToken(refreshToken)
			.expiredTime(expired_time).build();
	}
}
