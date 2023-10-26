package com.finale.neulhaerang.domain.member.dto.response;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResDto {
	private long memberId;
	private String nickname;
	private String accessToken;

	public static LoginResDto of(Member member, String accessToken) {
		return LoginResDto.builder()
			.memberId(member.getId())
			.nickname(member.getNickname())
			.accessToken(accessToken).build();
	}
}