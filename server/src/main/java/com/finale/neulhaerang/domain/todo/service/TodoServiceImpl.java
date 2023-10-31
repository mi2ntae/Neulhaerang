package com.finale.neulhaerang.domain.todo.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;
import com.finale.neulhaerang.domain.todo.entity.Todo;
import com.finale.neulhaerang.domain.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
	private final TodoRepository todoRepository;

	private final ModelMapper modelMapper;

	@Override
	public String createTodo(TodoCreateReqDto todoCreateReqDto) {
		Todo todo = modelMapper.map(todoCreateReqDto, Todo.class);
		todoRepository.save(todo);
		return "Create Success";
	}
}
