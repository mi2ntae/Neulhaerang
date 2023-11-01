package com.finale.neulhaerang.domain.member.service;

import com.finale.neulhaerang.domain.member.dto.request.LoginReqDto;
import com.finale.neulhaerang.domain.member.dto.request.TokenReqDto;
import com.finale.neulhaerang.domain.member.dto.response.LoginResDto;
import com.finale.neulhaerang.domain.member.dto.response.TokenResDto;
import com.finale.neulhaerang.global.exception.common.ExpiredAuthException;
import com.finale.neulhaerang.global.exception.common.NotValidJwtTokenException;

public interface AuthService {
	LoginResDto login(LoginReqDto loginReqDto);

	TokenResDto reissueAccessToken(TokenReqDto tokenReqDto) throws NotValidJwtTokenException, ExpiredAuthException;

	void logout();
}
