package com.finale.neulhaerang.global.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finale.neulhaerang.global.notification.dto.request.CreateNotificationReqDto;
import com.finale.neulhaerang.global.notification.dto.request.NotificationReqDto;
import com.finale.neulhaerang.global.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notification")
public class NotificationController {
	private final NotificationService notificationService;

	@PostMapping("/{memberId}")
	public ResponseEntity<String> sendNotificationByMessage(@PathVariable Long memberId,
		@RequestBody CreateNotificationReqDto createNotificationReqDto) {
		notificationService.sendNotificationByToken(memberId, NotificationReqDto.create(createNotificationReqDto));
		return ResponseEntity.status(HttpStatus.OK).body(memberId + "님에게 알림을 보냈습니다.");
	}
}
