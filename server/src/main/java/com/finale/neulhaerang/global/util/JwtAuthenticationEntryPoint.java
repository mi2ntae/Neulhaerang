package com.finale.neulhaerang.global.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/*
 * 유저 정보가 없음
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws
		IOException, ServletException {
		// 유효한 자격증명을 제공하지 않고 접근하려 할때 401
		log.error("[JwtAuthenticationEntryPoint] commence() -> 유효하지 않은 인증 정보입니다.");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}
}