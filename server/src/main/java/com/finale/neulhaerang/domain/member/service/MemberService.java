package com.finale.neulhaerang.domain.member.service;

import com.finale.neulhaerang.domain.member.document.MemberStat;
import com.finale.neulhaerang.domain.member.dto.response.MemberCharacterResDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberStatusResDto;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.global.exception.member.NotExistCharacterInfoException;
import com.finale.neulhaerang.global.exception.member.NotExistDeviceException;
import com.finale.neulhaerang.global.exception.member.NotExistMemberException;

public interface MemberService {
	MemberStatusResDto findStatusByMemberId(long memberId);

	MemberCharacterResDto findCharacterByMemberId(long memberId) throws NotExistCharacterInfoException;

	Member loadMemberByDeviceToken(String deviceToken) throws NotExistDeviceException, NotExistMemberException;

	void removeMember();

	void testInsert(MemberStat memberStat);
}
