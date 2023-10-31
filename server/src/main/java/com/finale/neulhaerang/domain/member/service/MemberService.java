package com.finale.neulhaerang.domain.member.service;

import com.finale.neulhaerang.domain.member.dto.response.MemberCharacterResDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberStatusResDto;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.global.exception.member.NonExistCharacterInfoException;
import com.finale.neulhaerang.global.exception.member.NonExistDeviceException;
import com.finale.neulhaerang.global.exception.member.NonExistMemberException;

public interface MemberService {
	MemberStatusResDto findStatusByMemberId(long memberId);

	MemberCharacterResDto findCharacterByMemberId(long memberId) throws NonExistCharacterInfoException;

	Member loadMemberByDeviceToken(String deviceToken) throws NonExistDeviceException, NonExistMemberException;

	void removeMember();
}
