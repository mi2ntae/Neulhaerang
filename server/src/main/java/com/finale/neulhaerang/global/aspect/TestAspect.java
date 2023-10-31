package com.finale.neulhaerang.global.aspect;

import java.util.HashSet;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class TestAspect {
	@Before("execution(* com.finale.neulhaerang.domain.*.service.*.*(..))")
	public void logBefore(JoinPoint joinPoint) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			return;
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		UserDetails userDetails = User.builder()
			.username("1")	// device Id
			.password("1")	// member Id
			.authorities(grantedAuthorities).build();
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

}