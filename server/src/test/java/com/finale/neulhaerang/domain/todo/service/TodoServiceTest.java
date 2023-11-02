package com.finale.neulhaerang.domain.todo.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.global.util.BaseTest;
import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;
import com.finale.neulhaerang.domain.todo.dto.response.TodoListResDto;
import com.finale.neulhaerang.domain.todo.entity.Todo;
import com.finale.neulhaerang.domain.todo.repository.TodoRepository;
import com.finale.neulhaerang.global.exception.todo.InvalidTodoDateException;

class TodoServiceTest extends BaseTest {
	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private TodoService todoService;

	@Autowired
	private ModelMapper modelMapper;

	@Test
	@DisplayName("Todo 등록 테스트")
	public void When_CreateTodo_Expect_IsOk() {
		// given
		TodoCreateReqDto todoCreateReqDto = TodoCreateReqDto.builder()
			.todoDate(LocalDateTime.of(2023, 12, 1, 13, 30))
			.content("코딩테스트")
			.statType(StatType.생존력)
			.build();
		Todo todo = modelMapper.map(todoCreateReqDto, Todo.class);

		// when
		todoService.createTodo(todoCreateReqDto);

		// then
		List<Todo> todoList = todoRepository.findAll();
		assertThat(todoList).hasSize(1);
		assertThat(todoList.get(0)).usingRecursiveComparison()
			.ignoringFields("id", "member")
			.isEqualTo(todo);
	}

	@Test
	@DisplayName("Todo 등록 시 오늘보다 이전 날짜에 등록 요청이 온 경우 exception 발생 테스트")
	public void When_InsertWrongInput_Expect_BadRequest() {
		// given
		TodoCreateReqDto todoCreateReqDto = TodoCreateReqDto.builder()
			.todoDate(LocalDateTime.of(2023,10,1,13,30))
			.content("코딩테스트")
			.statType(StatType.생존력)
			.build();

		// when, then
		assertThatThrownBy(() -> todoService.createTodo(todoCreateReqDto))
			.isInstanceOf(InvalidTodoDateException.class);
	}

	private Todo createTodo(String content, StatType statType, LocalDateTime todoDate){
		return Todo.builder()
			.member(member)
			.todoDate(todoDate)
			.content(content)
			.statType(statType)
			.build();
	}
}