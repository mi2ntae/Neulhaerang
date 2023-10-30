package com.finale.neulhaerang.domain.todo.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;
import com.finale.neulhaerang.domain.todo.entity.Todo;
import com.finale.neulhaerang.domain.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
	private final ModelMapper modelMapper;
	private final TodoRepository todoRepository;

	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody @Valid TodoCreateReqDto todoCreateReqDto){
		if(todoCreateReqDto.getTodoDate().isBefore(LocalDateTime.now())){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Input Date Is Before Today");
		}
		Todo todo = modelMapper.map(todoCreateReqDto, Todo.class);
		todoRepository.save(todo);
		return ResponseEntity.status(HttpStatus.CREATED).body(todo);
	}
}
