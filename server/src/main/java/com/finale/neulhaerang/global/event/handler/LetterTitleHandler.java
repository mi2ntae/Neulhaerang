package com.finale.neulhaerang.global.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.finale.neulhaerang.domain.letter.repository.LetterRepository;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.global.event.LetterEvent;

@Component
public class LetterTitleHandler extends TitleEventHandler {
	@Autowired
	private LetterRepository letterRepository;
	private Long[] titleIdOfLetter = new Long[] {22L, 23L, 24L};

	@EventListener
	public void checkLetter(LetterEvent letterEvent) {
		checkNumOfLetter(letterEvent.getMember());
	}

	public void checkNumOfLetter(Member member) {
		int numOfLetter = letterRepository.countAllByMember(member);
		if (numOfLetter >= 10) {
			getTitle(titleIdOfLetter[0], member);
		}
		if (numOfLetter >= 50) {
			getTitle(titleIdOfLetter[1], member);
		}
		if (numOfLetter >= 100) {
			getTitle(titleIdOfLetter[2], member);
		}
	}
}
