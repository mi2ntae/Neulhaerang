package com.finale.neulhaerang.domain.todo.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;
import com.finale.neulhaerang.domain.todo.dto.request.TodoModifyReqDto;
import com.finale.neulhaerang.domain.todo.dto.response.CheckRatioListResDto;
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
	public ResponseEntity<List<TodoListResDto>> findTodoByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")
		LocalDate todoDate){
		return ResponseEntity.status(HttpStatus.OK).body(todoService.findTodoByDate(todoDate));
	}

	@PatchMapping("/check/{todoId}")
	public ResponseEntity<Void> modifyTodoCheckByTodoId(@PathVariable Long todoId){
		todoService.modifyTodoCheckByTodoId(todoId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PatchMapping("/status/{todoId}")
	public ResponseEntity<Void> removeTodoByTodoId(@PathVariable Long todoId){
		todoService.removeTodoByTodoId(todoId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PatchMapping("/{todoId}")
	public ResponseEntity<Void> modifyTodoByTodoId(@PathVariable Long todoId, @RequestBody @Valid TodoModifyReqDto todoModifyReqDto){
		todoService.modifyTodoByTodoId(todoId, todoModifyReqDto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/done")
	public ResponseEntity<List<CheckRatioListResDto>> findCheckRatioByMonth(@RequestParam("yearMonth") YearMonth yearMonth){
		return ResponseEntity.status(HttpStatus.OK).body(todoService.findCheckRatioByMonth(yearMonth));
	}
}
