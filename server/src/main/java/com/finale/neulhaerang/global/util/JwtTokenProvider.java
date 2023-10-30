package com.finale.neulhaerang.global.util;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.service.MemberService;
import com.finale.neulhaerang.global.exception.common.NonValidJwtTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
	private final RedisUtil redisUtil;

	@Value("${spring.jwt.secret}")
	private String secretKey;

	@Value("${spring.jwt.token.access-expiration-time}")
	private long accessExpirationTime;

	@Value("${spring.jwt.token.refresh-expiration-time}")
	private long refreshExpirationTime;

	@Autowired
	private MemberService memberService;

	/**
	 * Access 토큰 생성
	 */
	public String createAccessToken(String deviceToken, long memberId){
		Claims claims = Jwts.claims().setSubject(deviceToken);
		claims.put("member", String.valueOf(memberId));
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + accessExpirationTime);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expireDate)
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	/**
	 * Refresh 토큰 생성
	 */
	public String createRefreshToken(String deviceToken){
		Claims claims = Jwts.claims().setSubject(deviceToken);
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + refreshExpirationTime);

		String refreshToken = Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expireDate)
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();

		// redis에 저장
		redisUtil.setData(deviceToken, refreshToken, refreshExpirationTime);
		return refreshToken;
	}

	/**
	 * 토큰으로부터 클레임을 만들고, 이를 통해 User 객체 생성해 Authentication 객체 반환
	 */
	public Authentication getAuthentication(String token) {
		String deviceToken = getSubject(token);
		Member member = memberService.loadMemberByDeviceToken(deviceToken);

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		UserDetails userDetails = User.builder()
			.username(String.valueOf(member.getId()))
			.password(String.valueOf(member.getId()))
			.authorities(grantedAuthorities).build();

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	/**
	 * http 헤더로부터 bearer 토큰을 가져옴.
	 */
	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	/**
	 * Access 토큰을 검증
	 */
	public boolean validateToken(String token){
		try{
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch(ExpiredJwtException e) {
			// 토큰, 리프레쉬 토큰 재발행
			log.error("Expired JWT");
			throw new NonValidJwtTokenException();
		} catch(JwtException e) {
			log.error("JWT Exception");
			throw new NonValidJwtTokenException();
		}
	}

	public String getSubject(String accessToken) {
		return Jwts.parser().
			setSigningKey(secretKey)
			.parseClaimsJws(accessToken)
			.getBody().getSubject();
	}

	public Claims getClaims(String accessToken) {
		return Jwts.parser().
			setSigningKey(secretKey)
			.parseClaimsJws(accessToken).getBody();
	}
}