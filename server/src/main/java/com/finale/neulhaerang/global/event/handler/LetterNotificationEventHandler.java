package com.finale.neulhaerang.global.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.global.event.LetterEvent;
import com.finale.neulhaerang.global.notification.dto.request.LetterNotificationReqDto;
import com.finale.neulhaerang.global.notification.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Transactional
public class LetterNotificationEventHandler {
	@Autowired
	protected NotificationService notificationService;

	@EventListener
	public void sendLetterNotification(LetterEvent letterEvent) {
		notificationService.sendNotificationByToken(letterEvent.getMember().getId(),
			LetterNotificationReqDto.create(letterEvent.getMember()));
	}
}
