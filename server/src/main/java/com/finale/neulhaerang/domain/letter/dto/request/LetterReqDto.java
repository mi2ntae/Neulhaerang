package com.finale.neulhaerang.domain.letter.dto.request;

import java.util.List;

import com.finale.neulhaerang.domain.letter.dto.common.ChatGptDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LetterReqDto {
	private String model;
	private List<ChatGptDto> messages;

	public static LetterReqDto from(List<ChatGptDto> chatGptDto) {
		return LetterReqDto.builder()
			.model("gpt-3.5-turbo")
			.messages(chatGptDto)
			.build();
	}
}
