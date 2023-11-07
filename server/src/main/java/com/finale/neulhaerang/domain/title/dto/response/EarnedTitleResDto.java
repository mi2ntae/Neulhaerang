package com.finale.neulhaerang.domain.title.dto.response;

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
}
