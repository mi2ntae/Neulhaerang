package com.finale.neulhaerang.domain.member.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.finale.neulhaerang.domain.member.dto.response.KakaoUserResDto;

@FeignClient(name = "kakaoInfoFeignClient", url = "${oauth2.kakao.infoUrl}", configuration = FeignClientProperties.FeignClientConfiguration.class)
@Component
public interface KakaoInfoFeignClient {
	@GetMapping("/v2/user/me")
	KakaoUserResDto getKakaoUserInfo(
		@RequestHeader(name = "Authorization") String Authorization,
		@RequestHeader(name = "Content-type") String cType
	);
}
