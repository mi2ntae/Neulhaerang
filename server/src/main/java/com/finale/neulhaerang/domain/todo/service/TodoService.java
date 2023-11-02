package com.finale.neulhaerang.domain.todo.service;

import java.time.LocalDate;
import java.util.List;

import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;
import com.finale.neulhaerang.domain.todo.dto.response.TodoListResDto;

public interface TodoService {
	void createTodo(TodoCreateReqDto todoCreateReqDto);
	List<TodoListResDto> findTodoByDate(LocalDate todoDate);
}
