package com.finale.neulhaerang.domain.todo.dto.response;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.todo.entity.Todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TodoListResDto {
	private Long todoId;
	private String content;
	private boolean alarm;
	private boolean check;
	private StatType statType;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
	private LocalTime alarmTime;

	public static TodoListResDto from(Todo todo) {
		return TodoListResDto.builder()
			.todoId(todo.getId())
			.content(todo.getContent())
			.alarm(todo.isAlarm())
			.check(todo.isCheck())
			.statType(todo.getStatType())
			.alarmTime(todo.getTodoDate().toLocalTime())
			.build();
	}
}
