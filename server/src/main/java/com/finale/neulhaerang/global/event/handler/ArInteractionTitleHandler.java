package com.finale.neulhaerang.global.event.handler;

import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.finale.neulhaerang.domain.title.entity.EarnedTitle;
import com.finale.neulhaerang.domain.title.entity.Title;
import com.finale.neulhaerang.domain.title.repository.EarnedTitleRepository;
import com.finale.neulhaerang.domain.title.repository.TitleRepository;
import com.finale.neulhaerang.global.event.RepelIndolenceMonsterEvent;
import com.finale.neulhaerang.global.event.TagOtherMemberEvent;
import com.finale.neulhaerang.global.exception.title.NotExistTitleException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArInteractionTitleHandler {
	private final TitleRepository titleRepository;
	private final EarnedTitleRepository earnedTitleRepository;
	private final long SOCIAL_FIRST_TAG = 31;
	private final long REPEL_INDOLENCE_MONSTER = 20;

	@Async
	@EventListener
	public void tagOtherMember(TagOtherMemberEvent tagOtherMemberEvent) {
		Optional<Title> optionalTitle = titleRepository.findById(SOCIAL_FIRST_TAG);
		if (optionalTitle.isEmpty()) {
			throw new NotExistTitleException(tagOtherMemberEvent.getMember(), SOCIAL_FIRST_TAG);
		}
		if (earnedTitleRepository.existsByTitle_IdAndMember(SOCIAL_FIRST_TAG, tagOtherMemberEvent.getMember())) {
			log.info(
				"첫 소셜 태그 칭호를 받은 사용자 " + tagOtherMemberEvent.getMember().getNickname() + "님이 다시 칭호를 획득 하였습니다. 확인바랍니다.");
		}
		earnedTitleRepository.save(EarnedTitle.create(tagOtherMemberEvent.getMember(), optionalTitle.get()));
	}

	@Async
	@EventListener
	public void repelIndolenceMonster(RepelIndolenceMonsterEvent repelIndolenceMonsterEvent) {
		Optional<Title> optionalTitle = titleRepository.findById(REPEL_INDOLENCE_MONSTER);
		if (optionalTitle.isEmpty()) {
			throw new NotExistTitleException(repelIndolenceMonsterEvent.getMember(), REPEL_INDOLENCE_MONSTER);
		}
		if (earnedTitleRepository.existsByTitle_IdAndMember(REPEL_INDOLENCE_MONSTER, repelIndolenceMonsterEvent.getMember())) {
			log.info(
				"나태 괴물 첫 처치 칭호를 받은 사용자 " + repelIndolenceMonsterEvent.getMember().getNickname() + "님이 다시 칭호를 획득 하였습니다. 확인바랍니다.");
		}
		earnedTitleRepository.save(EarnedTitle.create(repelIndolenceMonsterEvent.getMember(), optionalTitle.get()));
	}
}
