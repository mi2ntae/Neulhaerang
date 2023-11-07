package com.finale.neulhaerang.global.notification.dto.request;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.title.entity.Title;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TitleNotificationReqDto extends NotificationReqDto {
	String title;
	String content;

	@Builder
	private TitleNotificationReqDto(String title, String content) {
		super(title, content);
		this.title = title;
		this.content = content;
	}

	public static TitleNotificationReqDto create(Member member, Title earnedTitle) {
		return TitleNotificationReqDto.builder()
			.title(earnedTitle.getName() + " 칭호를 획득하셨습니다!")
			.content(member.getNickname() + "님💗 '" + earnedTitle.getName() + "' 칭호를 획득하셨습니다! 축하드려요🎈🎁🎉")
			.build();
	}
}
