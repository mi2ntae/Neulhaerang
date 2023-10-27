package com.finale.neulhaerang.domain.member.dto.request;

import lombok.Getter;

@Getter
public class TokenReqDto {
	private String accessToken;
	private String refreshToken;
}