package com.finale.neulhaerang.domain.member.service;

import com.finale.neulhaerang.domain.member.dto.response.MemberStatusResDto;

public interface MemberService {
	MemberStatusResDto findStatusByMemberId(long memberId);


}
