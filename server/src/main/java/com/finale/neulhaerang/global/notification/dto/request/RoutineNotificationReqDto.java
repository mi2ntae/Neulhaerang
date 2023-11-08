package com.finale.neulhaerang.global.notification.dto.request;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.routine.entity.Routine;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RoutineNotificationReqDto extends NotificationReqDto {
	String title;
	String content;

	@Builder
	private RoutineNotificationReqDto(String title, String content) {
		super(title, content);
		this.title = title;
		this.content = content;
	}

	public static RoutineNotificationReqDto create(Member member, Routine routine) {
		return RoutineNotificationReqDto.builder()
			.title("'" + routine.getContent() + "'를 할 시간이에요😎")
			.content(member.getNickname() + "님💗 '" + routine.getContent() + "'을 할 시간이에요! 잊지 않으셨죠? 😀")
			.build();
	}
}
