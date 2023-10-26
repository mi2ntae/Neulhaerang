package com.finale.neulhaerang.todo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.todo.entity.Todo;

public class TodoTest {
	@Test
	@DisplayName("Todo Entity Build Test")
	public void builder() {
		Todo todo = createTodo("양치하기", StatType.갓생력);
		assertThat(todo).isNotNull();
	}
	@Test
	@DisplayName("Todo Entity Bean 주입 Test")
	public void javaBean(){
		// GIVEN
		String content = "양치하기";
		StatType statType = StatType.갓생력;

		// WHEN
		Todo todo = createTodo(content, statType);

		// THEN
		assertThat(todo.getContent()).isEqualTo(content);
		assertThat(todo.getStatType()).isEqualTo(statType);
	}

	private Todo createTodo(String content, StatType statType){
		return Todo.builder()
			.content(content)
			.statType(statType)
			.build();
	}
}
