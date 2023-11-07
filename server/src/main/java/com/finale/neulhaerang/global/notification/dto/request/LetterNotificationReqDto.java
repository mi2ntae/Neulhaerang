package com.finale.neulhaerang.global.notification.dto.request;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LetterNotificationReqDto extends NotificationReqDto {
	String title;
	String content;

	@Builder
	private LetterNotificationReqDto(String title, String content) {
		super(title, content);
		this.title = title;
		this.content = content;
	}

	public static LetterNotificationReqDto create(Member member) {
		return LetterNotificationReqDto.builder()
			.title(member.getNickname() + "님 편지가 도착했어요💌")
			.content(member.getNickname() + "님💗 ' 해랑이에게 편지가 도착했어요! 우체통에서 확인해보세요~ 해랑이가 기다리고 있어요 💘")
			.build();
	}
}
