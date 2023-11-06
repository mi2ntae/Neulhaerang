package com.finale.neulhaerang.domain.letter.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finale.neulhaerang.domain.letter.service.LetterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/letter")
public class LetterController {

	private final LetterService letterService;

	@GetMapping
	public ResponseEntity<String> findLetterByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate todoDate) {
		String content = letterService.findLetterByDate(todoDate);
		return ResponseEntity.status(HttpStatus.OK).body(content);
	}
}
