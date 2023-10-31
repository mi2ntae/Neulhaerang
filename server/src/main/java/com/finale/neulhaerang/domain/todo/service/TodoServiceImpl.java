package com.finale.neulhaerang.domain.todo.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;
import com.finale.neulhaerang.domain.todo.entity.Todo;
import com.finale.neulhaerang.domain.todo.repository.TodoRepository;
import com.finale.neulhaerang.global.exception.todo.InvalidTodoDateException;
import com.finale.neulhaerang.global.util.AuthenticationHandler;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
	private final TodoRepository todoRepository;
	private final AuthenticationHandler authenticationHandler;
	private final MemberRepository memberRepository;

	@Override
	public void createTodo(TodoCreateReqDto todoCreateReqDto) {
		if(todoCreateReqDto.getTodoDate().isBefore(LocalDateTime.now())){
			throw new InvalidTodoDateException();
		}
		Member member = memberRepository.getReferenceById(authenticationHandler.getLoginMemberId());
		todoRepository.save(Todo.create(todoCreateReqDto, member));
	}
}
