package com.finale.neulhaerang.domain.title.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finale.neulhaerang.domain.title.dto.response.EarnedTitleResDto;
import com.finale.neulhaerang.domain.title.service.TitleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/title")
@RequiredArgsConstructor
public class TitleController {
	private final TitleService titleService;

	@GetMapping
	public ResponseEntity<List<EarnedTitleResDto>> findEarnedTitleByMember() {
		List<EarnedTitleResDto> earnedTitle = titleService.findEarnedTitleByMember();
		return ResponseEntity.status(HttpStatus.OK).body(earnedTitle);
	}

	@PatchMapping("/{titleId}")
	public ResponseEntity<List<EarnedTitleResDto>> modifyEquipTitleByMember(@PathVariable Long titleId) {
		titleService.modifyEquipTitleByTitleId(titleId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
