package com.finale.neulhaerang.global.event.handler;

import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.title.entity.EarnedTitle;
import com.finale.neulhaerang.domain.title.entity.Title;
import com.finale.neulhaerang.domain.title.repository.EarnedTitleRepository;
import com.finale.neulhaerang.domain.title.repository.TitleRepository;
import com.finale.neulhaerang.global.event.LetterEvent;
import com.finale.neulhaerang.global.exception.title.NotExistTitleException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class LetterTitleHandler {
	private final TitleRepository titleRepository;
	private final EarnedTitleRepository earnedTitleRepository;

	@EventListener
	public void checkLetter(LetterEvent letterEvent) {
		// findByMember_Id()
		int numOfLetter = 0;

		if (numOfLetter >= 10) {

		}
		if (numOfLetter >= 50) {

		}
		if (numOfLetter >= 100) {

		}
	}

	public void getLetterTitle(long titleId, Member member) {
		Optional<Title> optionalTitle = titleRepository.findById(titleId);
		if (optionalTitle.isEmpty()) {
			throw new NotExistTitleException(member, titleId);
		}
		if (!earnedTitleRepository.existsByTitle_IdAndMember(titleId, member)) {
			earnedTitleRepository.save(EarnedTitle.create(member, optionalTitle.get()));
		} else {
			log.info(member.getNickname() + "님이 이미 획득한 칭호(title_id=" + titleId + ")이기 때문에 발급하지 않습니다.");
		}
	}
}
