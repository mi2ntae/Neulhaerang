package com.finale.neulhaerang.domain.letter.dto.response;

import java.util.List;

import com.finale.neulhaerang.domain.letter.dto.common.ChatGptDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LetterResDto {
	private List<Choice> choices;

	@Getter
	public static class Choice{
		private ChatGptDto message;
	}
}
