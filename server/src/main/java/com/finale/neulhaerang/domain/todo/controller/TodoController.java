package com.finale.neulhaerang.domain.todo.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;
import com.finale.neulhaerang.domain.todo.dto.response.TodoListResDto;
import com.finale.neulhaerang.domain.todo.service.TodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
	private final TodoService todoService;
	@PostMapping
	public ResponseEntity<Void> createTodo(@RequestBody @Valid TodoCreateReqDto todoCreateReqDto){
		todoService.createTodo(todoCreateReqDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping
	public ResponseEntity<List<TodoListResDto>> findTodo(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")
		LocalDate todoDate){
		return ResponseEntity.status(HttpStatus.OK).body(todoService.findTodo(todoDate));
	}
}
