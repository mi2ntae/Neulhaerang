package com.finale.neulhaerang.domain.member.service;

import java.util.List;

import com.finale.neulhaerang.domain.member.dto.request.CharacterModifyReqDto;
import com.finale.neulhaerang.domain.member.dto.request.StatRecordReqDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberCharacterResDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberProfileResDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberStatusResDto;
import com.finale.neulhaerang.domain.member.dto.response.StatListResDto;
import com.finale.neulhaerang.domain.member.dto.response.StatRecordListResDto;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.global.exception.member.NotExistCharacterInfoException;
import com.finale.neulhaerang.global.exception.member.NotExistDeviceException;
import com.finale.neulhaerang.global.exception.member.NotExistMemberException;

public interface MemberService {
	MemberStatusResDto findStatusByMemberId(long memberId);

	MemberCharacterResDto findCharacterByMemberId(long memberId) throws NotExistCharacterInfoException;

	Member loadMemberByDeviceToken(String deviceToken) throws NotExistDeviceException, NotExistMemberException;

	void removeMember() throws NotExistMemberException;

	void createStat(StatRecordReqDto statRecord);

	List<StatListResDto> findAllStatsByMemberId(long memberId) throws NotExistMemberException;

	int[] findStatChangeRecordLastDaysByStatType(int statType);

	List<StatRecordListResDto> findStatChangeRecordByStatType(int statType, int page);

	MemberProfileResDto findMemberProfileByMemberId(long memberId);

	void modifyCharacterInfoByMember(CharacterModifyReqDto characterModifyReqDto);

	void recordTiredness (int tiredness) throws NotExistMemberException;
}
