package com.finale.neulhaerang.domain.todo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;
import com.finale.neulhaerang.domain.todo.dto.response.TodoListResDto;
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

	@Override
	public List<TodoListResDto> findTodoByDate(LocalDate todoDate) {
		Member member = memberRepository.getReferenceById(authenticationHandler.getLoginMemberId());

		LocalDateTime startDate = todoDate.atStartOfDay();
		LocalDateTime endDate = todoDate.atTime(LocalTime.MAX);

		List<Todo> todoList = todoRepository.findTodosByMemberAndTodoDateIsBetween(member, startDate, endDate);
		List<TodoListResDto> todoResDtoList = new ArrayList<>();
		for (Todo todo : todoList) {
			String alarmTime = todo.getTodoDate().getHour()+":"+todo.getTodoDate().getMinute();
			todoResDtoList.add(TodoListResDto.of(todo, alarmTime));
		}
		if(todoResDtoList.size()==0){
			return null;
		}
		return todoResDtoList;
	}
}
