package com.finale.neulhaerang.domain.letter.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.finale.neulhaerang.domain.letter.dto.request.LetterReqDto;
import com.finale.neulhaerang.domain.letter.dto.response.LetterResDto;

@FeignClient(url = "https://api.openai.com", name="letterFeignClient")
public interface LetterFeignClient {
	@PostMapping(value = "/v1/chat/completions", consumes = "application/json")
	LetterResDto getGPTResponse(@RequestHeader("Content-type") String contentType,
		@RequestHeader("Authorization") String gptKey, @RequestBody LetterReqDto letterReqDto);
}
