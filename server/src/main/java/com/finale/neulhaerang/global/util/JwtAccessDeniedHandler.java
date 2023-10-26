package com.finale.neulhaerang.global.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/*
 * 유저 정보는 있으나, 자원에 접근할 수 있는 권한이 없음
 */
@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws
		IOException, ServletException {
		// 필요한 권한이 없이 접근하려 할때 403
		log.error("[JwtAccessDeniedHandler] handle() -> 접근 권한이 없습니다.");
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
}