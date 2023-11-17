package com.finale.neulhaerang.domain.title.dto.response;

import com.finale.neulhaerang.domain.title.entity.EarnedTitle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EarnedTitleResDto {
	private Long titleId;
	private String name;
	private String content;

	public static EarnedTitleResDto from(EarnedTitle earnedTitle) {
		return EarnedTitleResDto.builder()
			.titleId(earnedTitle.getTitle().getId())
			.content(earnedTitle.getTitle().getContent())
			.name(earnedTitle.getTitle().getName())
			.build();
	}
}
