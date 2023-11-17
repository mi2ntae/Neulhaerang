package com.finale.neulhaerang.global.event.handler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberStatRepository;
import com.finale.neulhaerang.domain.title.entity.EarnedTitle;
import com.finale.neulhaerang.domain.title.entity.Title;
import com.finale.neulhaerang.domain.title.repository.EarnedTitleRepository;
import com.finale.neulhaerang.domain.title.repository.TitleRepository;
import com.finale.neulhaerang.global.exception.title.NotExistTitleException;
import com.finale.neulhaerang.global.notification.dto.request.TitleNotificationReqDto;
import com.finale.neulhaerang.global.notification.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Transactional
public class TitleEventHandler {
	@Autowired
	protected MemberStatRepository memberStatRepository;
	@Autowired
	protected TitleRepository titleRepository;
	@Autowired
	protected EarnedTitleRepository earnedTitleRepository;
	@Autowired
	protected NotificationService notificationService;

	protected void getTitle(Long titleId, Member member) {
		Optional<Title> optionalTitle = titleRepository.findById(titleId);
		if (optionalTitle.isEmpty()) {
			throw new NotExistTitleException(member, titleId);
		}
		if (!earnedTitleRepository.existsByTitle_IdAndMember(titleId, member)) {
			earnedTitleRepository.save(EarnedTitle.create(member, optionalTitle.get()));
			try {
				notificationService.sendNotificationByToken(member.getId(),
					TitleNotificationReqDto.create(member, optionalTitle.get()));
			} catch (Exception e) {
				log.warn("알림을 보내는데 문제가 발생했습니다. 확인바랍니다.");
			}
		} else {
			log.info(member.getNickname() + "님이 이미 획득한 칭호(title_id=" + titleId + ")이기 때문에 발급하지 않습니다.");
		}
	}
}
