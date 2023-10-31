package com.finale.neulhaerang.domain.member.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.finale.neulhaerang.domain.member.dto.request.LoginReqDto;
import com.finale.neulhaerang.domain.member.dto.request.TokenReqDto;
import com.finale.neulhaerang.domain.member.dto.response.KakaoUserResDto;
import com.finale.neulhaerang.domain.member.dto.response.LoginResDto;
import com.finale.neulhaerang.domain.member.dto.response.TokenResDto;
import com.finale.neulhaerang.domain.member.entity.Device;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.feignclient.KakaoInfoFeignClient;
import com.finale.neulhaerang.domain.member.repository.DeviceRepository;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.global.exception.common.ExpiredAuthException;
import com.finale.neulhaerang.global.exception.common.NonValidJwtTokenException;
import com.finale.neulhaerang.global.util.AuthenticationHandler;
import com.finale.neulhaerang.global.util.JwtTokenProvider;
import com.finale.neulhaerang.global.util.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	private final MemberRepository memberRepository;
	private final DeviceRepository deviceRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationHandler authenticationHandler;
	private final RedisUtil redisUtil;

	// Feign Client
	private final KakaoInfoFeignClient kakaoInfoFeignClient;

	@Value("${spring.jwt.token.access-expiration-time}")
	private long accessExpirationTime;

	@Value("${spring.jwt.token.refresh-expiration-time}")
	private long refreshExpirationTime;

	@Override
	public LoginResDto login(LoginReqDto loginReqDto) {
		Member member = null;
		switch (loginReqDto.getProvider()) {
			case "kakao":
				member = kakaoLogin(loginReqDto);
				break;
			case "google":
				break;
			default:
				break;
		}
		String accessToken = jwtTokenProvider.createAccessToken(loginReqDto.getDeviceToken(), member.getId());
		String refreshToken = jwtTokenProvider.createRefreshToken(loginReqDto.getDeviceToken());
		LocalDateTime expire_time = LocalDateTime.now().plus(accessExpirationTime, ChronoUnit.MILLIS);
		log.info(expire_time.toString());
		return LoginResDto.of(member, TokenResDto.of(accessToken, refreshToken, expire_time));
	}

	@Override
	public TokenResDto reissueAccessToken(TokenReqDto tokenReqDto) throws NonValidJwtTokenException, ExpiredAuthException {
		String deviceToken = tokenReqDto.getDeviceToken();
		Optional<Device> optionalDevice = deviceRepository.findDeviceByDeviceToken(deviceToken);
		if(optionalDevice.isEmpty()) {
			throw new ExpiredAuthException();
		}

		String refreshToken = tokenReqDto.getRefreshToken();
		String savedRefreshToken = redisUtil.getData(deviceToken);
		if(savedRefreshToken != null)  {
			if(!savedRefreshToken.equals(refreshToken)) {
				// 해당 디바이스로 저장된 리프레쉬 토큰이 아닐 때 -> 변조 가능성
				throw new NonValidJwtTokenException();
			}
		} else {
			// 리프레쉬 토큰이 만료되어 로그인 다시 해야하는 경우
			throw new ExpiredAuthException();
		}
		String accessToken = jwtTokenProvider.createAccessToken(deviceToken, optionalDevice.get().getMember().getId());
		return TokenResDto.of(accessToken, tokenReqDto.getRefreshToken(), LocalDateTime.now().plus(accessExpirationTime, ChronoUnit.MILLIS));
	}

	@Override
	public void logout() {
		long deviceId = authenticationHandler.getLoginDeviceId();
		redisUtil.deleteData(String.valueOf(deviceId));
		log.info("로그아웃 완료");
	}

	private Member kakaoLogin(LoginReqDto loginReqDto) {
		KakaoUserResDto kakaoUserResDto = kakaoInfoFeignClient.getKakaoUserInfo("Bearer "+loginReqDto.getAccessToken(), "application/x-www-form-urlencoded");
		Optional<Member> optionalMember = memberRepository.findMemberByKakaoId(kakaoUserResDto.getId());
		Optional<Device> optionalDevice = deviceRepository.findDeviceByDeviceToken(loginReqDto.getDeviceToken());
		if(optionalMember.isEmpty()) {
			Member member = memberRepository.save(Member.of(kakaoUserResDto.getId(), kakaoUserResDto.getKakao_account().getProfile().getNickname()));
			if(optionalDevice.isEmpty()) {
				deviceRepository.save(Device.of(member, loginReqDto.getDeviceToken()));
			}
			return member;
		} else {
			if(optionalDevice.isEmpty()) {
				deviceRepository.save(Device.of(optionalMember.get(), loginReqDto.getDeviceToken()));
			}
			return optionalMember.get();
		}
	}
}
