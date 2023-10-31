package com.finale.neulhaerang.domain.routine.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.finale.neulhaerang.domain.routine.dto.request.RoutineCreateReqDto;
import com.finale.neulhaerang.domain.routine.service.RoutineService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RoutineController {
	private final RoutineService routineService;

	@PostMapping("/routine")
	public ResponseEntity<String> createRoutine(@RequestBody @Valid RoutineCreateReqDto routineCreateReqDto) {
		routineService.createRoutine(routineCreateReqDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
