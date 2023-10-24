package com.finale.neulhaerang.domain.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finale.neulhaerang.domain.member.dto.response.MemberCharacterResDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberStatusResDto;
import com.finale.neulhaerang.domain.member.entity.CharacterInfo;
import com.finale.neulhaerang.domain.member.repository.CharacterInfoRepository;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.global.exception.characterInfo.NonExistCharacterInfoException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	private final MemberRepository memberRepository;
	private final CharacterInfoRepository characterInfoRepository;

	// 멤버 상태 정보 조회 (나태도, 피로도) -> MongoDB에서 조회
	@Override
	public MemberStatusResDto findStatusByMemberId(long memberId) {
		log.info("MongoDB로부터 Member의 나태도, 피로도 조회.");
		return null;
	}

	// 멤버 캐릭터 조회
	@Override
	public MemberCharacterResDto findCharacterByMemberId(long memberId) throws NonExistCharacterInfoException {
		log.info("캐릭터 타입, 스킨, 의상 정보를 조회.");
		Optional<CharacterInfo> optionalCharacterInfo = characterInfoRepository.findCharacterInfoByMember_Id(memberId);
		if(optionalCharacterInfo.isEmpty()) {
			throw new NonExistCharacterInfoException();
		}
		return MemberCharacterResDto.from(optionalCharacterInfo.get());
	}

}
