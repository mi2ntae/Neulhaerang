package com.finale.neulhaerang.domain.title.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.title.dto.response.EarnedTitleResDto;
import com.finale.neulhaerang.domain.title.entity.EarnedTitle;
import com.finale.neulhaerang.domain.title.entity.Title;
import com.finale.neulhaerang.domain.title.repository.EarnedTitleRepository;
import com.finale.neulhaerang.domain.title.repository.TitleRepository;
import com.finale.neulhaerang.global.exception.title.NotEarnedTitleException;
import com.finale.neulhaerang.global.exception.title.NotExistTitleException;
import com.finale.neulhaerang.global.handler.AuthenticationHandler;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TitleSerivceImpl implements TitleService {
	private final AuthenticationHandler authenticationHandler;
	private final EarnedTitleRepository earnedTitleRepository;
	private final TitleRepository titleRepository;
	private final MemberRepository memberRepository;

	@Override
	public List<EarnedTitleResDto> findEarnedTitleByMember() {
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findEarnedTitlesByMember_Id(
			authenticationHandler.getLoginMemberId());
		return earnedTitles.stream()
			.map(EarnedTitleResDto::from)
			.collect(Collectors.toList());
	}

	@Override
	public void modifyEquipTitleByTitleId(Long titleId) {
		Member member = memberRepository.findById(authenticationHandler.getLoginMemberId()).get();
		Optional<Title> title = titleRepository.findById(titleId);
		if (title.isEmpty()) {
			throw new NotExistTitleException(member, titleId);
		}
		if (!earnedTitleRepository.existsByTitle_IdAndMember(titleId, member)) {
			throw new NotEarnedTitleException(member, titleId);
		}
		member.updateTitleId(titleId);
	}
}
