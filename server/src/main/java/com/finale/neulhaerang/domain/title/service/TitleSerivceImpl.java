package com.finale.neulhaerang.domain.title.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.title.dto.response.EarnedTitleResDto;
import com.finale.neulhaerang.domain.title.entity.EarnedTitle;
import com.finale.neulhaerang.domain.title.repository.EarnedTitleRepository;
import com.finale.neulhaerang.global.util.AuthenticationHandler;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TitleSerivceImpl implements TitleService {
	private final AuthenticationHandler authenticationHandler;
	private final EarnedTitleRepository earnedTitleRepository;

	@Override
	public List<EarnedTitleResDto> findEarnedTitleByMember() {
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findEarnedTitlesByMember_Id(
			authenticationHandler.getLoginMemberId());
		return earnedTitles.stream()
			.map(EarnedTitleResDto::from)
			.collect(Collectors.toList());
	}
}
