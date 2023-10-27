package com.finale.neulhaerang.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finale.neulhaerang.domain.member.dto.request.LoginReqDto;
import com.finale.neulhaerang.domain.member.dto.request.TokenReqDto;
import com.finale.neulhaerang.domain.member.dto.response.LoginResDto;
import com.finale.neulhaerang.domain.member.dto.response.TokenResDto;
import com.finale.neulhaerang.domain.member.service.AuthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(value = "인증 API", tags = {"Auth"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
	private final AuthService authService;

	@ApiOperation(value = "SNS 로그인", notes = "SNS 로그인 (카카오, 구글)")
	@PostMapping("/login")
	public ResponseEntity<LoginResDto> login(@RequestBody LoginReqDto loginReqDto) {
		return ResponseEntity.ok().body(authService.login(loginReqDto));
	}

	@ApiOperation(value = "엑세스 토큰 재발급", notes = "엑세스 토큰 만료로 인한 재발급")
	@PostMapping("/refresh")
	public ResponseEntity<TokenResDto> refreshAccessToken(@RequestBody TokenReqDto tokenReqDto) {
		return ResponseEntity.ok().body(authService.refreshAccessToken(tokenReqDto));
	}

}
