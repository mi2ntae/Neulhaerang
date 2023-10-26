package com.finale.neulhaerang.domain.member.service;

import com.finale.neulhaerang.domain.member.dto.request.LoginReqDto;
import com.finale.neulhaerang.domain.member.dto.response.LoginResDto;

public interface AuthService {
	LoginResDto login(LoginReqDto loginReqDto);

}
