package com.finale.neulhaerang.todo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.todo.entity.Todo;

public class TodoTest {
	@Test
	public void builder() {
		Todo todo = Todo.builder()
			.content("양치하기")
			.statType(StatType.갓생력)
			.build();
		assertThat(todo).isNotNull();
	}
	@Test
	public void javaBean(){
		// GIVEN
		String content = "양치하기";
		StatType statType = StatType.갓생력;

		// WHEN
		Todo todo = Todo.builder()
			.content(content)
			.statType(statType)
			.build();

		// THEN
		assertThat(todo.getContent()).isEqualTo(content);
		assertThat(todo.getStatType()).isEqualTo(statType);
	}
}
