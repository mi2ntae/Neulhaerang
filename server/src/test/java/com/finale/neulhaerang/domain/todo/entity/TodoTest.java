package com.finale.neulhaerang.domain.todo.entity;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;
import com.finale.neulhaerang.domain.todo.dto.request.TodoModifyReqDto;

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
	public void When_TodoDI_Expect_isEqualTo() {
		// given
		String content = "양치하기";
		StatType statType = StatType.갓생력;

		// when
		Todo todo = createTodo(content, statType);

		// then
		assertThat(todo.getContent()).isEqualTo(content);
		assertThat(todo.getStatType()).isEqualTo(statType);
	}

	@DisplayName("Todo를 생성합니다.")
	@Test
	void When_CreateTodo_Expect_Equal() {
		// given
		TodoCreateReqDto todoCreateReqDto = TodoCreateReqDto.builder()
			.todoDate(LocalDateTime.now())
			.content("슬늘생")
			.alarm(true)
			.statType(StatType.갓생력)
			.build();
		Member member = Member.builder()
			.kakaoId(1L)
			.build();

		// when
		Todo todo = Todo.create(todoCreateReqDto, member);

		// then
		assertThat(todo.getTodoDate()).isEqualTo(todoCreateReqDto.getTodoDate());
		assertThat(todo.getContent()).isEqualTo(todoCreateReqDto.getContent());
		assertThat(todo.getStatType()).isEqualTo(todoCreateReqDto.getStatType());
		assertThat(todo.isAlarm()).isEqualTo(todoCreateReqDto.getAlarm());
		assertThat(todo.getMember().getKakaoId()).isEqualTo(member.getKakaoId());
	}

	@DisplayName("todo의 check 값을 변경합니다. 이때, 현재 check의 반대로 변경합니다.")
	@Test
	void When_ModifyCheck_Expect_Oposite() {
		// given
		boolean original1 = true;
		boolean original2 = false;
		Todo todo1 = Todo.builder()
			.check(original1)
			.build();

		Todo todo2 = Todo.builder()
			.check(original2)
			.build();
		// when
		todo1.updateCheck();
		todo2.updateCheck();

		// then
		assertThat(todo1.isCheck()).isEqualTo(!original1);
		assertThat(todo2.isCheck()).isEqualTo(!original2);
	}

	@DisplayName("todo의 status 값을 true로 변경합니다.")
	@Test
	void When_ModifyStatus_Expect_True() {
		// given
		Todo todo = Todo.builder()
			.status(false)
			.build();

		// when
		todo.updateStatus();

		// then
		assertThat(todo.isStatus()).isTrue();
	}

	@DisplayName("todo를 수정합니다.")
	@Test
	void When_ModifyTodo_Expect_Equal() {
		// given
		Todo todo = Todo.builder()
			.content("슬늘생")
			.alarm(true)
			.todoDate(LocalDateTime.of(1, 1, 1, 1, 1, 1))
			.statType(StatType.갓생력)
			.build();

		TodoModifyReqDto todoModifyReqDto = TodoModifyReqDto.builder()
			.content("슬늘생2")
			.alarm(false)
			.todoDate(LocalDateTime.of(1, 2, 1, 1, 1, 1))
			.statType(StatType.창의력)
			.build();

		// when
		todo.updateTodo(todoModifyReqDto);
		// then
		assertThat(todo.getContent()).isEqualTo(todoModifyReqDto.getContent());
		assertThat(todo.isAlarm()).isEqualTo(todoModifyReqDto.getAlarm());
		assertThat(todo.getTodoDate()).isEqualTo(todoModifyReqDto.getTodoDate());
		assertThat(todo.getStatType()).isEqualTo(todoModifyReqDto.getStatType());
	}

	private Todo createTodo(String content, StatType statType) {
		return Todo.builder()
			.content(content)
			.statType(statType)
			.build();
	}
}