package com.finale.neulhaerang.global.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.finale.neulhaerang.global.event.TirednessEvent;

@Component
public class TirednessTitleEventHandler extends TitleEventHandler {
	@EventListener
	public void checkLifeStat(TirednessEvent tirednessEvent) {
		getTitle(21L, tirednessEvent.getMember());
	}
}
