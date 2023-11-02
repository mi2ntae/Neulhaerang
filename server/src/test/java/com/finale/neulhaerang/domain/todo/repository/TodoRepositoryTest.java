package com.finale.neulhaerang.domain.todo.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.global.util.BaseTest;
import com.finale.neulhaerang.domain.todo.entity.Todo;

class TodoRepositoryTest extends BaseTest {
	@Autowired
	private TodoRepository todoRepository;

	@Test
	@DisplayName("Todo 조회 시 날짜가 주어졌을 때 해당 날짜의 Todo를 조회하는 JPA 쿼리 테스트")
	public void When_FindTodo_Expect_CollectTodoList(){
		// given
		Todo todo1 = this.createTodo("todo1",StatType.생존력,LocalDateTime.now());
		Todo todo2 = this.createTodo("todo2",StatType.인싸력,LocalDateTime.now());
		Todo todo3 = this.createTodo("todo3",StatType.갓생력,LocalDateTime.now());
		Todo todo4 = this.createTodo("todo4",StatType.창의력,LocalDateTime.of(2023,10,30,13,30));
		Todo todo5 = Todo.builder()
			.member(member)
			.todoDate(LocalDateTime.now())
			.content("todo5")
			.statType(StatType.최애력)
			.status(true)
			.build();
		todoRepository.saveAll(List.of(todo1, todo2, todo3, todo4, todo5));

		// when
		List<Todo> todoList = todoRepository.findTodosByMemberAndStatusIsFalseAndTodoDateIsBetween(
			member,LocalDate.now().atStartOfDay(),LocalDate.now().atTime(LocalTime.MAX));

		// then
		assertThat(todoList).hasSize(3);
		for(Todo todo:todoList){
			assertThat(todo.getTodoDate().toLocalDate()).isEqualTo(LocalDate.now());
			assertThat(todo.isStatus()).isEqualTo(false);
		}
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