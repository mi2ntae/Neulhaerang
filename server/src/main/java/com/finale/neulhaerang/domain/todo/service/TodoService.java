package com.finale.neulhaerang.domain.todo.service;

import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;

public interface TodoService {
	String createTodo(TodoCreateReqDto todoCreateReqDto);
}
