package com.finale.neulhaerang.domain.todo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;
import com.finale.neulhaerang.domain.todo.dto.request.TodoModifyReqDto;
import com.finale.neulhaerang.domain.todo.dto.response.TodoListResDto;
import com.finale.neulhaerang.domain.todo.entity.Todo;
import com.finale.neulhaerang.domain.todo.repository.TodoRepository;
import com.finale.neulhaerang.global.exception.todo.InvalidTodoDateException;
import com.finale.neulhaerang.global.exception.todo.NotExistTodoException;
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

	@Override
	public List<TodoListResDto> findTodoByDate(LocalDate todoDate) {
		Member member = memberRepository.getReferenceById(authenticationHandler.getLoginMemberId());

		LocalDateTime startDate = todoDate.atStartOfDay();
		LocalDateTime endDate = todoDate.atTime(LocalTime.MAX);

		List<Todo> todoList = todoRepository.findTodosByMemberAndStatusIsFalseAndTodoDateIsBetween(member, startDate, endDate);

		return todoList.stream()
			.map(TodoListResDto::from)
			.collect(Collectors.toList());
	}

	@Override
	public void modifyTodoCheckByTodoId(Long todoId) {
		Todo todo = todoRepository.findTodoByIdAndStatusIsFalse(todoId)
			.orElseThrow(NotExistTodoException::new);
		if(todo.getTodoDate().toLocalDate().isBefore(LocalDate.now())){
			throw new InvalidTodoDateException();
		}
		todo.updateCheck();
	}

	@Override
	public void removeTodoByTodoId(Long todoId) {
		Todo todo = todoRepository.findTodoByIdAndStatusIsFalse(todoId)
			.orElseThrow(NotExistTodoException::new);
		if(todo.getTodoDate().toLocalDate().isBefore(LocalDate.now())){
			throw new InvalidTodoDateException();
		}
		todo.updateStatus();
	}

	@Override
	public void modifyTodoByTodoId(Long todoId, TodoModifyReqDto todoModifyReqDto) {
		Todo todo = todoRepository.findTodoByIdAndStatusIsFalse(todoId)
			.orElseThrow(NotExistTodoException::new);
		if(
			todo.getTodoDate().toLocalDate().isBefore(LocalDate.now()) || todoModifyReqDto.getTodoDate().toLocalDate().isBefore(LocalDate.now())){
			throw new InvalidTodoDateException();
		}
		todo.updateTodo(todoModifyReqDto);
	}
}
