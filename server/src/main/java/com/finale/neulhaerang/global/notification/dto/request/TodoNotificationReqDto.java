package com.finale.neulhaerang.global.notification.dto.request;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.todo.entity.Todo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TodoNotificationReqDto extends NotificationReqDto {
	String title;
	String content;

	@Builder
	private TodoNotificationReqDto(String title, String content) {
		super(title, content);
		this.title = title;
		this.content = content;
	}

	public static TodoNotificationReqDto create(Member member, Todo todo) {
		return TodoNotificationReqDto.builder()
			.title("'" + todo.getContent() + "'를 할 시간이에요😎")
			.content(member.getNickname() + "님💗 '" + todo.getContent() + "'을 할 시간이에요! 잊지 않으셨죠? 😀")
			.build();
	}
}
