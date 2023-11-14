package com.finale.neulhaerang.domain.ar.service;

import java.util.List;

import com.finale.neulhaerang.domain.ar.dto.request.AroundMemberCharacterReqDto;
import com.finale.neulhaerang.domain.ar.dto.response.AroundMemberCharacterListResDto;

public interface ArService {
	boolean tagOtherMember(long memberId);

	boolean repelIndolenceMonster();

	List<AroundMemberCharacterListResDto> updateLocation(AroundMemberCharacterReqDto aroundMemberCharacterReqDto);

	List<AroundMemberCharacterListResDto> findAroundMemberByLocation();
}
