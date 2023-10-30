package com.finale.neulhaerang.domain.todo.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.finale.neulhaerang.domain.routine.entity.StatType;

class TodoTest {
	@Test
	@DisplayName("Todo Entity Build Test")
	public void When_EntityBuilder_Expect_isNotNull() {
		// given, when
		Todo todo = createTodo("양치하기", StatType.갓생력);

		// then
		assertThat(todo).isNotNull();
	}

	@Test
	@DisplayName("Todo Entity Bean 주입 Test")
	public void When_TodoDI_Expect_isEqualTo(){
		// given
		String content = "양치하기";
		StatType statType = StatType.갓생력;

		// when
		Todo todo = createTodo(content, statType);

		// then
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