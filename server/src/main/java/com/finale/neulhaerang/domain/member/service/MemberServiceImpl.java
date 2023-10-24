package com.finale.neulhaerang.domain.member.service;

import org.springframework.stereotype.Service;

import com.finale.neulhaerang.domain.member.dto.response.MemberStatusResDto;

@Service
public class MemberServiceImpl implements MemberService{

	// 멤버 상태 정보 조회 (나태도, 피로도) -> MongoDB에서 조회
	@Override
	public MemberStatusResDto findStatusByMemberId(long memberId) {
		return null;
	}


}
