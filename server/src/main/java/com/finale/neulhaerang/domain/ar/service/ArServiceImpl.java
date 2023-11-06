package com.finale.neulhaerang.domain.ar.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.finale.neulhaerang.domain.member.document.StatRecord;
import com.finale.neulhaerang.domain.member.dto.request.StatRecordReqDto;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.member.repository.MemberStatRepository;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.title.entity.EarnedTitle;
import com.finale.neulhaerang.domain.title.repository.EarnedTitleRepository;
import com.finale.neulhaerang.domain.title.repository.TitleRepository;
import com.finale.neulhaerang.global.event.TagOtherMemberEvent;
import com.finale.neulhaerang.global.exception.ar.InvalidTagException;
import com.finale.neulhaerang.global.exception.member.NotExistMemberException;
import com.finale.neulhaerang.global.util.AuthenticationHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArServiceImpl implements ArService {
	private final EarnedTitleRepository earnedTitleRepository;
	private final MemberStatRepository memberStatRepository;
	private final MemberRepository memberRepository;
	private final TitleRepository titleRepository;

	private final AuthenticationHandler authenticationHandler;
	private final ApplicationEventPublisher publisher;
	private final long SOCIAL_FIRST_TAG = 31;

	@Override
	public boolean tagOtherMember(long memberId) throws NotExistMemberException, InvalidTagException {
		Optional<Member> optionalMember = memberRepository.findById(memberId);
		if(optionalMember.isEmpty()) {
			throw new NotExistMemberException();
		}
		long loginMemberId = authenticationHandler.getLoginMemberId();
		if(optionalMember.get().getId() == loginMemberId) {
			throw new InvalidTagException();
		}

		StatRecordReqDto statRecordReqDto = StatRecordReqDto.of("소셜에서 다른 사용자를 태그", LocalDateTime.now(), StatType.인싸력, 3);
		memberStatRepository.save(StatRecord.of(statRecordReqDto, memberId));

		Optional<EarnedTitle> optionalEarnedTitle = earnedTitleRepository.findEarnedTitleByMember_IdAndTitle_Id(loginMemberId, SOCIAL_FIRST_TAG);
		if(optionalEarnedTitle.isEmpty()) {
			publisher.publishEvent(new TagOtherMemberEvent(memberRepository.getReferenceById(loginMemberId)));
			return true;
		}
		return false;
	}
}
