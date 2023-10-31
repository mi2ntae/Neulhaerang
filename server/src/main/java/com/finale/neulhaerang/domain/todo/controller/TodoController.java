package com.finale.neulhaerang.domain.todo.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;
import com.finale.neulhaerang.domain.todo.service.TodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
	private final TodoService todoService;
	@PostMapping
	public ResponseEntity<String> createTodo(@RequestBody @Valid TodoCreateReqDto todoCreateReqDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(todoCreateReqDto));
	}
}
