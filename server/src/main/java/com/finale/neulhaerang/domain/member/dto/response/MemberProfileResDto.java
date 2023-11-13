package com.finale.neulhaerang.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberProfileResDto {
	private int level;
	private int nxtExp;
	private int curExp;
	private String nickname;

	public static MemberProfileResDto of(int level, int nxtExp, int curExp, String nickname) {
		return MemberProfileResDto.builder()
			.level(level)
			.nxtExp(nxtExp)
			.curExp(curExp)
			.nickname(nickname).build();
	}
}
