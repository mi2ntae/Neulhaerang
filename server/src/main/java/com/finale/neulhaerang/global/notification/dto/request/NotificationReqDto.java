package com.finale.neulhaerang.global.notification.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationReqDto {
	String title;
	String content;

	public static NotificationReqDto create(CreateNotificationReqDto createNotificationReqDto) {
		return new NotificationReqDto(createNotificationReqDto.getTitle(), createNotificationReqDto.getContent());
	}
}