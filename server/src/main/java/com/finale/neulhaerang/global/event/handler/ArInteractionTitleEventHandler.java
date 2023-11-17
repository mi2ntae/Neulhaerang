package com.finale.neulhaerang.global.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.finale.neulhaerang.global.event.RepelIndolenceMonsterEvent;
import com.finale.neulhaerang.global.event.TagOtherMemberEvent;

@Component
public class ArInteractionTitleEventHandler extends TitleEventHandler {
	private final long SOCIAL_FIRST_TAG = 31;
	private final long REPEL_INDOLENCE_MONSTER = 20;

	@Async
	@EventListener
	public void tagOtherMember(TagOtherMemberEvent tagOtherMemberEvent) {
		getTitle(SOCIAL_FIRST_TAG, tagOtherMemberEvent.getMember());
	}

	@Async
	@EventListener
	public void repelIndolenceMonster(RepelIndolenceMonsterEvent repelIndolenceMonsterEvent) {
		getTitle(REPEL_INDOLENCE_MONSTER, repelIndolenceMonsterEvent.getMember());
	}
}
