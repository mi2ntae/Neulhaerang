package com.finale.neulhaerang.domain.letter.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptDto {
	private String role;
	private String content;

	public static ChatGptDto from(String content){
		return ChatGptDto.builder()
			.role("user")
			.content(content)
			.build();
	}
}
