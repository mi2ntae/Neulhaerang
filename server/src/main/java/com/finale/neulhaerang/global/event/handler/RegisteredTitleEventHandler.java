package com.finale.neulhaerang.global.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.finale.neulhaerang.global.event.RegisteredEvent;

@Component
public class RegisteredTitleEventHandler extends TitleEventHandler {
	private final long titleIdOfRegisteredTitle = 19L;

	@Async
	@EventListener
	public void checkRegisteredTitle(RegisteredEvent registeredEvent) {
		getTitle(titleIdOfRegisteredTitle, registeredEvent.getMember());
	}
}
