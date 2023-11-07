package com.finale.neulhaerang.global.notification.dto.request;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindOtherMemberReqDto extends NotificationReqDto {
	String title;
	String content;

	@Builder
	private FindOtherMemberReqDto(String title, String content) {
		super(title, content);
		this.title = title;
		this.content = content;
	}

	public static FindOtherMemberReqDto create(Member member, Member otherMember) {
		return FindOtherMemberReqDto.builder()
			.title(member.getNickname() + " 주위에 다른 사용자가 있어요!")
			.content(member.getNickname() + "님💗 '" + otherMember.getNickname() + "'님이 주위에 계세요!💨💨 태그해 보는건 어때요?")
			.build();
	}
}
