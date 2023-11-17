package com.finale.neulhaerang.domain.letter.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.letter.entity.Letter;
import com.finale.neulhaerang.domain.letter.repository.LetterRepository;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.global.exception.member.NotExistMemberException;
import com.finale.neulhaerang.global.handler.AuthenticationHandler;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LetterServiceImpl implements LetterService {
	private final LetterRepository letterRepository;
	private final AuthenticationHandler authenticationHandler;
	private final MemberRepository memberRepository;

	@Override
	public String findLetterByDate(LocalDate date) {
		Member member = memberRepository.findById(authenticationHandler.getLoginMemberId())
			.orElseThrow(NotExistMemberException::new);
		Optional<Letter> letter = letterRepository.findLetterByMemberAndLetterDate(member, date);
		letter.ifPresent(Letter::updateRead);
		return letter.map(Letter::getContent).orElse(null);
	}
}
