package com.finale.neulhaerang.global.event.handler;

import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.finale.neulhaerang.domain.title.entity.EarnedTitle;
import com.finale.neulhaerang.domain.title.entity.Title;
import com.finale.neulhaerang.domain.title.repository.EarnedTitleRepository;
import com.finale.neulhaerang.domain.title.repository.TitleRepository;
import com.finale.neulhaerang.global.event.RegisteredEvent;
import com.finale.neulhaerang.global.exception.title.NotExistTitleException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegisteredTitleHandler {
	private final TitleRepository titleRepository;
	private final EarnedTitleRepository earnedTitleRepository;

	@Async
	@EventListener
	public void earnedRegisteredTitle(RegisteredEvent registeredEvent) {
		Optional<Title> optionalTitle = titleRepository.findById(19L);
		if (optionalTitle.isEmpty()) {
			throw new NotExistTitleException(registeredEvent.getMember(), 19L);
		}
		earnedTitleRepository.save(EarnedTitle.create(registeredEvent.getMember(), optionalTitle.get()));
	}
}
