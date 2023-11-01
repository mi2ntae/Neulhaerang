package com.finale.neulhaerang.domain.member.controller;

import org.springframework.http.HttpStatus;
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
		return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginReqDto));
	}

	@ApiOperation(value = "엑세스 토큰 재발급", notes = "엑세스 토큰 만료로 인한 재발급")
	@PostMapping("/refresh")
	public ResponseEntity<TokenResDto> reissueAccessToken(@RequestBody TokenReqDto tokenReqDto) {
		return ResponseEntity.status(HttpStatus.OK).body(authService.reissueAccessToken(tokenReqDto));
	}

	@ApiOperation(value = "로그 아웃", notes = "로그 아웃")
	@PostMapping("/logout")
	public ResponseEntity<Void> logout() {
		authService.logout();
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@ApiOperation(value = "연결 확인", notes = "연결 확인")
	@PostMapping("/check")
	public ResponseEntity<String> check() {
		return ResponseEntity.status(HttpStatus.OK).body("I'm Healthy");
	}

}
