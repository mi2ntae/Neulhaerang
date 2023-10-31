package com.finale.neulhaerang.domain.member.dto.response;

import java.time.LocalDateTime;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResDto {
	private long memberId;
	private String nickname;
	private String accessToken;
	private String refreshToken;
	private LocalDateTime expiredTime;

	public static LoginResDto of(Member member, TokenResDto tokenResDto) {
		return LoginResDto.builder()
			.memberId(member.getId())
			.nickname(member.getNickname())
			.accessToken(tokenResDto.getAccessToken())
			.refreshToken(tokenResDto.getRefreshToken())
			.expiredTime(tokenResDto.getExpiredTime()).build();
	}
}