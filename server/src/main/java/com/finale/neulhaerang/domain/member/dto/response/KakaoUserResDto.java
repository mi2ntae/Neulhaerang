package com.finale.neulhaerang.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUserResDto {
	private long id;
	private Kakao_account kakao_account;

	@Getter
	public static class Kakao_account {
		private Profile profile;
	}

	@Getter
	public static class Profile {
		private String nickname;
	}
}