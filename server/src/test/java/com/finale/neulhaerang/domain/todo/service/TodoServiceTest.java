package com.finale.neulhaerang.domain.todo.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;
import com.finale.neulhaerang.domain.todo.entity.Todo;
import com.finale.neulhaerang.domain.todo.repository.TodoRepository;

@SpringBootTest
@ActiveProfiles("test")
class TodoServiceTest {
	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private TodoService todoService;

	@Test
	@DisplayName("Todo 등록 테스트")
	public void When_InsertTodo_Expect_IsOk() {
		// given
		LocalDateTime date = LocalDateTime.of(2023, 12, 1, 13, 30);
		String content = "코딩테스트";
		StatType statType = StatType.생존력;
		TodoCreateReqDto todoCreateReqDto = TodoCreateReqDto.builder()
			.todoDate(date)
			.content(content)
			.statType(statType)
			.build();

		// when
		todoService.createTodo(todoCreateReqDto);

		// then
		List<Todo> todoList = todoRepository.findAll();
		assertThat(todoList).hasSize(1)
			.extracting("content").contains(content);
	}
}