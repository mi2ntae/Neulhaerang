package com.finale.neulhaerang.domain.health.controller;

import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finale.neulhaerang.domain.health.dto.response.HealthResDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {
	private final Environment environment;

	@GetMapping
	public ResponseEntity<HealthResDto> healthCheck() {
		HealthResDto healthResDto = HealthResDto.from(Arrays.asList(environment.getActiveProfiles()));
		return ResponseEntity.status(HttpStatus.OK).body(healthResDto);
	}
}
