package com.finale.neulhaerang.domain.title.service;

import java.util.List;

import com.finale.neulhaerang.domain.title.dto.response.EarnedTitleResDto;

public interface TitleService {
	List<EarnedTitleResDto> findEarnedTitleByMember();
}
