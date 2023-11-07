package com.finale.neulhaerang.global.notification.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CreateNotificationReqDto {
	private String title;
	private String content;
}
