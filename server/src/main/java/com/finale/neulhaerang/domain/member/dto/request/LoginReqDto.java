package com.finale.neulhaerang.domain.member.dto.request;

import lombok.Getter;

@Getter
public class LoginReqDto {
	private String provider;
	private String accessToken;
	private String deviceToken;
}