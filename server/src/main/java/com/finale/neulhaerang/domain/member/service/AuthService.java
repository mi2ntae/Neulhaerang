package com.finale.neulhaerang.domain.member.service;

import com.finale.neulhaerang.domain.member.dto.request.LoginReqDto;
import com.finale.neulhaerang.domain.member.dto.request.TokenReqDto;
import com.finale.neulhaerang.domain.member.dto.response.LoginResDto;
import com.finale.neulhaerang.domain.member.dto.response.TokenResDto;
import com.finale.neulhaerang.global.exception.common.ExpiredAuthException;
import com.finale.neulhaerang.global.exception.common.NonValidJwtTokenException;

public interface AuthService {
	LoginResDto login(LoginReqDto loginReqDto);

	TokenResDto refreshAccessToken(TokenReqDto tokenReqDto) throws NonValidJwtTokenException, ExpiredAuthException;
}
