package com.finale.neulhaerang.domain.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finale.neulhaerang.domain.member.dto.request.LoginReqDto;
import com.finale.neulhaerang.domain.member.dto.response.KakaoUserResDto;
import com.finale.neulhaerang.domain.member.dto.response.LoginResDto;
import com.finale.neulhaerang.domain.member.entity.Device;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.DeviceRepository;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.global.util.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	private final MemberRepository memberRepository;
	private final DeviceRepository deviceRepository;
	private final JwtTokenProvider jwtTokenProvider;

	// Feign Client
	private final KakaoInfoFeignClient kakaoInfoFeignClient;

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
		jwtTokenProvider.createRefreshToken(loginReqDto.getDeviceToken(), member.getId());
		return LoginResDto.of(member, accessToken);
	}

	private Member kakaoLogin(LoginReqDto loginReqDto) {
		KakaoUserResDto kakaoUserResDto = kakaoInfoFeignClient.getKakaoUserInfo("Bearer "+loginReqDto.getAccessToken(), "application/x-www-form-urlencoded");
		Optional<Member> optionalMember = memberRepository.findMemberByKakaoId(kakaoUserResDto.getId());
		if(optionalMember.isEmpty()) {
			Member member = memberRepository.save(Member.of(kakaoUserResDto.getId(), kakaoUserResDto.getKakao_account().getProfile().getNickname()));
			deviceRepository.save(Device.of(member, loginReqDto.getDeviceToken()));
			return member;
		} else {
			return optionalMember.get();
		}
	}
}
