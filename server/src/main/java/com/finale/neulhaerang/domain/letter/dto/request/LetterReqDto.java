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
	private double temperature;
	private int max_tokens;

	public static LetterReqDto from(List<ChatGptDto> chatGptDto) {
		return LetterReqDto.builder()
			.model("gpt-3.5-turbo")
			.messages(chatGptDto)
			.temperature(0.5)
			.max_tokens(1000)
			.build();
	}
}
